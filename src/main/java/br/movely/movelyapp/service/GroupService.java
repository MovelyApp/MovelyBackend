package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.AddUserGroupDTO;
import br.movely.movelyapp.DTO.CreateGroupDTO;
import br.movely.movelyapp.DTO.EditGroupDTO;
import br.movely.movelyapp.DTO.ResponseGroupDTO;
import br.movely.movelyapp.GroupNotFoundException;
import br.movely.movelyapp.exceptions.ForbiddenException;
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
        return groupRepository.findVisibleToUser(currentUser.getId(), pageable).map(ResponseGroupDTO::toDTO);
    }

    public Group findGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public boolean isUserInGroup(Long userId, UUID groupId) {
        return canViewGroup(userId, groupId);
    }

    @Transactional(readOnly = true)
    public boolean canViewGroup(Long userId, UUID groupId) {
        Group group = findGroup(groupId);
        boolean isOwner = group.getOwner() != null && userId.equals(group.getOwner().getId());
        boolean isMember = group.getUsers().stream().anyMatch(member -> userId.equals(member.getId()));
        return isOwner || isMember;
    }

    @Transactional(readOnly = true)
    public List<UUID> listUserGroupIds(String username) {
        User currentUser = findUserByUsername(username);
        return groupRepository.findVisibleGroupIdsToUser(currentUser.getId());
    }

    public ResponseGroupDTO save(CreateGroupDTO request, String username) {
        User currentUser = findUserByUsername(username);
        Group group = new Group();

        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setUrlImagem(request.getUrlImagem());
        group.setOwner(currentUser);
        group.addUser(currentUser);
        groupRepository.save(group);
        return ResponseGroupDTO.toDTO(group);
    }

    public ResponseGroupDTO addUser(AddUserGroupDTO request) {
        if (request == null || request.getGroupId() == null) {
            throw new IllegalArgumentException("Group id is required");
        }

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

    public ResponseGroupDTO removeUser(Long userId, UUID groupId, String username) {
        if (groupId == null) {
            throw new IllegalArgumentException("Group id is required");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }

        User actor = findUserByUsername(username);
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group Not Found"));
        validateCanManageGroup(actor, group);

        boolean userInGroup = group.getUsers().stream()
                .anyMatch(member -> userId.equals(member.getId()));
        if (!userInGroup) {
            throw new RuntimeException("User is not in the group");
        }

        group.getUsers().removeIf(member -> userId.equals(member.getId()));
        return ResponseGroupDTO.toDTO(groupRepository.save(group));
    }

    public ResponseGroupDTO editOwnGroup(UUID id, EditGroupDTO editGroupDTO, String username) {
        User actor = findUserByUsername(username);
        Group groupDB = findGroup(id);
        validateCanManageGroup(actor, groupDB);

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

    private void validateCanManageGroup(User actor, Group group) {
        if (group.getOwner() != null) {
            if (!actor.getId().equals(group.getOwner().getId())) {
                throw new ForbiddenException("Only the group creator can manage members");
            }
            return;
        }

        boolean actorInGroup = group.getUsers().stream()
                .anyMatch(member -> actor.getId().equals(member.getId()));
        if (!actorInGroup) {
            throw new ForbiddenException("Only group members can manage this group");
        }
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
