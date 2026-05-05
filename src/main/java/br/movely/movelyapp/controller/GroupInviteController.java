package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.CreateGroupInviteDTO;
import br.movely.movelyapp.DTO.GroupInviteDTO;
import br.movely.movelyapp.service.GroupInviteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups/invites")
public class GroupInviteController {

    private final GroupInviteService service;

    public GroupInviteController(GroupInviteService service) {
        this.service = service;
    }

    @PostMapping
    public GroupInviteDTO invite(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateGroupInviteDTO request) {
        return service.invite(request, jwt.getSubject());
    }

    @GetMapping("/mine")
    public List<GroupInviteDTO> listMine(@AuthenticationPrincipal Jwt jwt) {
        return service.listMine(jwt.getSubject());
    }

    @PostMapping("/{id}/accept")
    public GroupInviteDTO accept(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        return service.accept(id, jwt.getSubject());
    }

    @PostMapping("/{id}/decline")
    public GroupInviteDTO decline(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        return service.decline(id, jwt.getSubject());
    }
}
