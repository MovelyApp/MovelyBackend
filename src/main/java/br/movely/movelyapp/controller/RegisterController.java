package br.movely.movelyapp.controller;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.model.*;
import br.movely.movelyapp.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Register>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(registerService.getAllByUser(userId));
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