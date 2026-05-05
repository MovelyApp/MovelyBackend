package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.*;
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
    public Page<ResponseGroupDTO> getGroups(Pageable pageable) {
        return groupService.listGroups(pageable);
    }

    @PutMapping("/{id}")
    public ResponseGroupDTO editGroup(@PathVariable UUID id,
                                      @RequestBody EditGroupDTO group) {
        return groupService.editOwnGroup(id, group);
    }

    @PostMapping
    public ResponseGroupDTO createGroup(@RequestBody CreateGroupDTO request) {
        return groupService.save(request);
    }


    @PostMapping("/add")
    public ResponseGroupDTO addUser(@RequestBody AddUserGroupDTO request) {
        return groupService.addUser(request.getUserId(), request.getGroupId());
    }

    @PostMapping("/remove")
    public ResponseGroupDTO removeUser(@RequestBody RemoveUserGroupDTO request) {
        return groupService.removeUser(request.getUserId(), request.getGroupId());
    }
}
