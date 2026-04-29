package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.LoginRequest;
import br.movely.movelyapp.DTO.RegisterRequest;
import br.movely.movelyapp.model.User;
import br.movely.movelyapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService service;

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getSubject();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok("User Registered");
    }
}
