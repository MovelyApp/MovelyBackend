package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.GoalResponseDTO;
import br.movely.movelyapp.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> listAll() {
        List<GoalResponseDTO> goals = goalService.listAll().stream()
                .map(GoalResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(goals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(GoalResponseDTO.fromEntity(goalService.findById(id)));
    }

    @GetMapping("/challenge/{challengeId}")
    public ResponseEntity<List<GoalResponseDTO>> listByChallenge(@PathVariable UUID challengeId) {
        List<GoalResponseDTO> goals = goalService.listByChallenge(challengeId).stream()
                .map(GoalResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(goals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        goalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}