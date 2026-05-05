package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.EditChallengeDTO;
import br.movely.movelyapp.DTO.ResponseChallengeDTO;
import br.movely.movelyapp.model.Challenge;
import br.movely.movelyapp.service.ChallengeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeService service;

    public ChallengeController(ChallengeService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Challenge> list(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        return service.list(pageable, jwt.getSubject());
    }
    @PutMapping("/{id}")
    public ResponseChallengeDTO edit(@PathVariable UUID id,
                                     @RequestBody EditChallengeDTO dto) {
        return service.edit(id, dto);
    }

    @PostMapping("/{id}/encerrar")
    public void encerrar(@PathVariable UUID id) {
        service.encerrar(id);
    }
}
