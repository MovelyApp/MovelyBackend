package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.service.GroupInviteService;
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
    private final GroupInviteService groupInviteService;

    public GroupController(GroupService groupService, GroupInviteService groupInviteService) {
        this.groupService = groupService;
        this.groupInviteService = groupInviteService;
    }
    @GetMapping
    public Page<ResponseGroupDTO> getGroups(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        return groupService.listGroups(pageable, jwt.getSubject());
    }

    @PutMapping("/{id}")
    public ResponseGroupDTO editGroup(@PathVariable UUID id,
                                      @AuthenticationPrincipal Jwt jwt,
                                      @RequestBody EditGroupDTO group) {
        return groupService.editOwnGroup(id, group, jwt.getSubject());
    }

    @PostMapping
    public ResponseGroupDTO createGroup(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateGroupDTO request) {
        return groupService.save(request, jwt.getSubject());
    }


    @PostMapping("/add")
    public GroupInviteDTO addUser(@AuthenticationPrincipal Jwt jwt, @RequestBody AddUserGroupDTO request) {
        CreateGroupInviteDTO inviteRequest = new CreateGroupInviteDTO();
        inviteRequest.setGroupId(request != null ? request.getGroupId() : null);
        inviteRequest.setEmail(request != null ? request.getEmail() : null);
        return groupInviteService.invite(inviteRequest, jwt.getSubject());
    }

    @PostMapping("/remove")
    public ResponseGroupDTO removeUser(@AuthenticationPrincipal Jwt jwt, @RequestBody RemoveUserGroupDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        return groupService.removeUser(request.getUserId(), request.getGroupId(), jwt.getSubject());
    }
}
