package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.AddUserGroupDTO;
import br.movely.movelyapp.DTO.CreateGroupDTO;
import br.movely.movelyapp.DTO.EditGroupDTO;
import br.movely.movelyapp.DTO.ResponseGroupDTO;
import br.movely.movelyapp.GroupNotFoundException;
import br.movely.movelyapp.exceptions.NotFoundException;
import br.movely.movelyapp.model.Group;
import br.movely.movelyapp.model.User;
import br.movely.movelyapp.repository.GroupRepository;
import br.movely.movelyapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional(readOnly = true)
    public Page<ResponseGroupDTO> listGroups(Pageable pageable, String username) {
        User currentUser = findUserByUsername(username);
        return groupRepository.findByUsers_Id(currentUser.getId(), pageable).map(ResponseGroupDTO::toDTO);
    }

    public Group findGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public boolean isUserInGroup(Long userId, UUID groupId) {
        Group group = findGroup(groupId);
        return group.getUsers().stream().anyMatch(member -> userId.equals(member.getId()));
    }

    @Transactional(readOnly = true)
    public List<UUID> listUserGroupIds(String username) {
        User currentUser = findUserByUsername(username);
        return groupRepository.findByUsers_Id(currentUser.getId(), Pageable.unpaged())
                .getContent()
                .stream()
                .map(Group::getId)
                .collect(Collectors.toList());
    }

    public ResponseGroupDTO save(CreateGroupDTO request, String username) {
        User currentUser = findUserByUsername(username);
        Group group = new Group();

        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setUrlImagem(request.getUrlImagem());
        group.addUser(currentUser);
        groupRepository.save(group);
        return ResponseGroupDTO.toDTO(group);
    }

    public ResponseGroupDTO addUser(AddUserGroupDTO request) {
        User user = findUserForGroupAdd(request);
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundException("Group Not Found"));
        Long userId = user.getId();
        boolean userAlreadyInGroup = group.getUsers().stream()
                .anyMatch(member -> userId.equals(member.getId()));
        if (userAlreadyInGroup) {
            throw new RuntimeException("User already in the group");
        }
        group.addUser(user);
        return ResponseGroupDTO.toDTO(groupRepository.save(group));
    }

    public ResponseGroupDTO removeUser(Long userId, UUID groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group Not Found"));

        boolean userInGroup = group.getUsers().stream()
                .anyMatch(member -> userId.equals(member.getId()));
        if (!userInGroup) {
            throw new RuntimeException("User is not in the group");
        }

        group.getUsers().removeIf(member -> userId.equals(member.getId()));
        return ResponseGroupDTO.toDTO(groupRepository.save(group));
    }

    public ResponseGroupDTO editOwnGroup(UUID id, EditGroupDTO editGroupDTO) {
        Group groupDB = findGroup(id);

        groupDB.atualizarInfo(
                editGroupDTO.getName(),
                editGroupDTO.getDescription(),
                editGroupDTO.getUrlImagem()
        );

        groupDB = groupRepository.save(groupDB);
        return ResponseGroupDTO.toDTO(groupDB);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    private User findUserForGroupAdd(AddUserGroupDTO request) {
        String email = request.getEmail();

        if (email != null && !email.trim().isEmpty()) {
            String normalizedEmail = email.trim().toLowerCase();
            Optional<User> user = userRepository.findByEmailIgnoreCase(normalizedEmail);

            if (!user.isPresent()) {
                user = userRepository.findByUsernameIgnoreCase(normalizedEmail);
            }

            return user.orElseThrow(() -> new NotFoundException("User Not Found"));
        }

        if (request.getUserId() != null) {
            return userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("User Not Found"));
        }

        throw new IllegalArgumentException("Email is required");
    }
}
