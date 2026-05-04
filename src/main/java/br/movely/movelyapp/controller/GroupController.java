package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.EditGroupDTO;
import br.movely.movelyapp.DTO.ResponseGroupDTO;
import br.movely.movelyapp.model.Group;
import br.movely.movelyapp.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping
    public Page<Group> getGroups(Pageable pageable) {
        return groupService.listGroups(pageable);
    }

    @PutMapping("/{id}")
    public ResponseGroupDTO editGroup(@PathVariable UUID id,
                                      @RequestBody EditGroupDTO group) {
        return groupService.editOwnGroup(id, group);
    }
}