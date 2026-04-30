package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.EditGroupDTO;
import br.movely.movelyapp.DTO.ResponseGroupDTO;
import br.movely.movelyapp.model.Group;
import br.movely.movelyapp.repository.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private GroupRepository groupRepository;

    public Page<Group> listGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public Group findGroup(long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public ResponseGroupDTO editOwnGroup(long id, EditGroupDTO editGroupDTO) {
        Group groupDB = findGroup(id);

        groupDB.setName(editGroupDTO.getName());
        groupDB.setDescription(editGroupDTO.getDescription());


        groupDB = groupRepository.save(groupDB);
        return ResponseGroupDTO.toDTO(groupDB);
    }
}
