package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.UpdateUserRequest;
import br.movely.movelyapp.DTO.UserDTO;
import br.movely.movelyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public List<UserDTO> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/by-email")
    public UserDTO getUserByEmail(@RequestParam String email) {
        return service.getUserByEmail(email);
    }

    @GetMapping("/{id:[0-9]+}")
    public UserDTO getUser(@PathVariable Long id) {
        return service.getUser(id);
    }

    @PutMapping
    public UserDTO updateUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateUserRequest request) {
        return service.updateUser(request, jwt.getSubject());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        service.deleteUser(id, jwt.getSubject());
    }
}
