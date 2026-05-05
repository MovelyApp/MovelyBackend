package br.movely.movelyapp.service;

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

import java.util.UUID;

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
    public Page<ResponseGroupDTO> listGroups(Pageable pageable) {
        return groupRepository.findAll(pageable).map(ResponseGroupDTO::toDTO);
    }

    public Group findGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);
    }

    public ResponseGroupDTO save(CreateGroupDTO request) {
        Group group = new Group();

        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setUrlImagem(request.getUrlImagem());
        groupRepository.save(group);
        return ResponseGroupDTO.toDTO(group);
    }

    public ResponseGroupDTO addUser(Long userId, UUID groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group Not Found"));
        if (group.getUsers().contains(user)) {
            throw new RuntimeException("User already in the group");
        }
        group.addUser(user);
        return ResponseGroupDTO.toDTO(groupRepository.save(group));
    }

    public ResponseGroupDTO removeUser(Long userId, UUID groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group Not Found"));

        if (!group.getUsers().contains(user)) {
            throw new RuntimeException("User is not in the group");
        }

        group.removeUser(user);
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
}
