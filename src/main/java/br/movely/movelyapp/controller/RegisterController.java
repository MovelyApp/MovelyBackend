package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.model.*;
import br.movely.movelyapp.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Register>> getAllByUser(@AuthenticationPrincipal Jwt jwt, @PathVariable Long userId) {
        return ResponseEntity.ok(registerService.getAllByUser(jwt.getSubject()));
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
    public ResponseEntity<WaterRegister> createWater(@AuthenticationPrincipal Jwt jwt, @RequestBody WaterRegisterRequest request) {
        return ResponseEntity.ok(registerService.createWaterRegister(request, jwt.getSubject()));
    }

    @PostMapping("/steps")
    public ResponseEntity<StepsRegister> createSteps(@AuthenticationPrincipal Jwt jwt, @RequestBody StepsRegisterRequest request) {
        return ResponseEntity.ok(registerService.createStepsRegister(request, jwt.getSubject()));
    }

    @PostMapping("/sleep")
    public ResponseEntity<SleepRegister> createSleep(@AuthenticationPrincipal Jwt jwt, @RequestBody SleepRegisterRequest request) {
        return ResponseEntity.ok(registerService.createSleepRegister(request, jwt.getSubject()));
    }

    @PostMapping("/workout")
    public ResponseEntity<WorkoutRegister> createWorkout(@AuthenticationPrincipal Jwt jwt, @RequestBody WorkoutRegisterRequest request) {
        return ResponseEntity.ok(registerService.createWorkoutRegister(request, jwt.getSubject()));
    }

    @PostMapping("/study")
    public ResponseEntity<StudyRegister> createStudy(@AuthenticationPrincipal Jwt jwt, @RequestBody StudyRegisterRequest request) {
        return ResponseEntity.ok(registerService.createStudyRegister(request, jwt.getSubject()));
    }
}
