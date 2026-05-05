package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.model.*;
import br.movely.movelyapp.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Register>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(registerService.getAllByUser(userId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Register>> getAllByGroup(@PathVariable UUID groupId) {
        return ResponseEntity.ok(registerService.getAllByGroup(groupId));
    }

    @GetMapping("/group/{groupId}/user/{userId}")
    public ResponseEntity<List<Register>> getAllByUserAndGroup(
            @PathVariable UUID groupId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(registerService.getAllByUserAndGroup(userId, groupId));
    }

    @PostMapping("/water")
    public ResponseEntity<WaterRegister> createWater(@RequestBody WaterRegisterRequest request) {
        return ResponseEntity.ok(registerService.createWaterRegister(request));
    }

    @PostMapping("/steps")
    public ResponseEntity<StepsRegister> createSteps(@RequestBody StepsRegisterRequest request) {
        return ResponseEntity.ok(registerService.createStepsRegister(request));
    }

    @PostMapping("/sleep")
    public ResponseEntity<SleepRegister> createSleep(@RequestBody SleepRegisterRequest request) {
        return ResponseEntity.ok(registerService.createSleepRegister(request));
    }

    @PostMapping("/workout")
    public ResponseEntity<WorkoutRegister> createWorkout(@RequestBody WorkoutRegisterRequest request) {
        return ResponseEntity.ok(registerService.createWorkoutRegister(request));
    }

    @PostMapping("/study")
    public ResponseEntity<StudyRegister> createStudy(@RequestBody StudyRegisterRequest request) {
        return ResponseEntity.ok(registerService.createStudyRegister(request));
    }
}
