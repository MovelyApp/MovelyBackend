package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.EditGroupDTO;
import br.movely.movelyapp.DTO.ResponseGroupDTO;
import br.movely.movelyapp.GroupNotFoundException;
import br.movely.movelyapp.model.Group;
import br.movely.movelyapp.repository.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Page<Group> listGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public Group findGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);
    }

    public Group save(Group group) {
        return groupRepository.save(group);
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