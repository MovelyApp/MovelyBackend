package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.EditGroupDTO;
import br.movely.movelyapp.DTO.ResponseGroupDTO;
import br.movely.movelyapp.model.Group;
import br.movely.movelyapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/groups")
    public Page<Group> getGroups(
    Pageable pageable){
        return groupService.listGroups(pageable);
    }

    @PutMapping("/{id}")
    public ResponseGroupDTO editGroup(@PathVariable long id, @RequestBody EditGroupDTO group) {
        return groupService.editOwnGroup(id, group);
    }
}
