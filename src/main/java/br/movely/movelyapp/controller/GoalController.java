package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.GoalResponseDTO;
import br.movely.movelyapp.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.movely.movelyapp.DTO.CreateSleepGoalDTO;
import br.movely.movelyapp.DTO.CreateStepsGoalDTO;
import br.movely.movelyapp.DTO.CreateStudyGoalDTO;
import br.movely.movelyapp.DTO.CreateWaterGoalDTO;
import br.movely.movelyapp.DTO.CreateWorkoutGoalDTO;

import br.movely.movelyapp.model.SleepGoal;
import br.movely.movelyapp.model.StepsGoal;
import br.movely.movelyapp.model.StudyGoal;
import br.movely.movelyapp.model.WaterGoal;
import br.movely.movelyapp.model.WorkoutGoal;

import org.springframework.http.HttpStatus;

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
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
        return ResponseEntity.ok(goals);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        goalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // endpoints das subclasses

    @PostMapping("/sleep")
    public ResponseEntity<GoalResponseDTO> createSleep(@RequestBody CreateSleepGoalDTO dto) {
        SleepGoal goal = goalService.createSleepGoal(
                dto.getChallengeId(),
                dto.getName(),
                dto.getDescription(),
                dto.getHoursTarget()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GoalResponseDTO.fromEntity(goal));
    }

    @PostMapping("/steps")
    public ResponseEntity<GoalResponseDTO> createSteps(@RequestBody CreateStepsGoalDTO dto) {
        StepsGoal goal = goalService.createStepsGoal(
                dto.getChallengeId(),
                dto.getName(),
                dto.getDescription(),
                dto.getStepsTarget()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GoalResponseDTO.fromEntity(goal));
    }

    @PostMapping("/study")
    public ResponseEntity<GoalResponseDTO> createStudy(@RequestBody CreateStudyGoalDTO dto) {
        StudyGoal goal = goalService.createStudyGoal(
                dto.getChallengeId(),
                dto.getName(),
                dto.getDescription(),
                dto.getHoursTarget()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GoalResponseDTO.fromEntity(goal));
    }

    @PostMapping("/water")
    public ResponseEntity<GoalResponseDTO> createWater(@RequestBody CreateWaterGoalDTO dto) {
        WaterGoal goal = goalService.createWaterGoal(
                dto.getChallengeId(),
                dto.getName(),
                dto.getDescription(),
                dto.getMlTarget()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GoalResponseDTO.fromEntity(goal));
    }

    @PostMapping("/workout")
    public ResponseEntity<GoalResponseDTO> createWorkout(@RequestBody CreateWorkoutGoalDTO dto) {
        WorkoutGoal goal = goalService.createWorkoutGoal(
                dto.getChallengeId(),
                dto.getName(),
                dto.getDescription(),
                dto.getWorkoutsTarget(),
                dto.getWorkoutType()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GoalResponseDTO.fromEntity(goal));
    }

}
