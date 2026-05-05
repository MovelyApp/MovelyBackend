package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public Page<ResponseGroupDTO> getGroups(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        return groupService.listGroups(pageable, jwt.getSubject());
    }

    @PutMapping("/{id}")
    public ResponseGroupDTO editGroup(@PathVariable UUID id,
                                      @RequestBody EditGroupDTO group) {
        return groupService.editOwnGroup(id, group);
    }

    @PostMapping
    public ResponseGroupDTO createGroup(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateGroupDTO request) {
        return groupService.save(request, jwt.getSubject());
    }


    @PostMapping("/add")
    public ResponseGroupDTO addUser(@RequestBody AddUserGroupDTO request) {
        return groupService.addUser(request);
    }

    @PostMapping("/remove")
    public ResponseGroupDTO removeUser(@RequestBody RemoveUserGroupDTO request) {
        return groupService.removeUser(request.getUserId(), request.getGroupId());
    }
}
