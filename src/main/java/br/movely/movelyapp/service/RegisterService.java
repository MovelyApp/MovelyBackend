package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.model.*;
import br.movely.movelyapp.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private UserService userService;

    public List<Register> getAllByUser(Long userId) {
        return registerRepository.findByUserId(userId);
    }

    public WaterRegister createWaterRegister(WaterRegisterRequest request) {
        WaterRegister register = new WaterRegister();
        register.setUser(userService.getUser(request.getUserId()));
        register.setDateTime(LocalDateTime.now());
        register.setNotes(request.getNotes());
        register.setMl(request.getMl());
        return registerRepository.save(register);
    }

    public StepsRegister createStepsRegister(StepsRegisterRequest request) {
        StepsRegister register = new StepsRegister();
        register.setUser(userService.getUser(request.getUserId()));
        register.setDateTime(LocalDateTime.now());
        register.setNotes(request.getNotes());
        register.setSteps(request.getSteps());
        return registerRepository.save(register);
    }

    public SleepRegister createSleepRegister(SleepRegisterRequest request) {
        SleepRegister register = new SleepRegister();
        register.setUser(userService.getUser(request.getUserId()));
        register.setDateTime(LocalDateTime.now());
        register.setNotes(request.getNotes());
        register.setHours(request.getHours());
        register.setQuality(request.getQuality());
        return registerRepository.save(register);
    }

    public WorkoutRegister createWorkoutRegister(WorkoutRegisterRequest request) {
        WorkoutRegister register = new WorkoutRegister();
        register.setUser(userService.getUser(request.getUserId()));
        register.setDateTime(LocalDateTime.now());
        register.setNotes(request.getNotes());
        register.setWorkoutType(request.getWorkoutType());
        register.setDurationMin(request.getDurationMin());
        register.setWeight(request.getWeight());
        return registerRepository.save(register);
    }

    public StudyRegister createStudyRegister(StudyRegisterRequest request) {
        StudyRegister register = new StudyRegister();
        register.setUser(userService.getUser(request.getUserId()));
        register.setDateTime(LocalDateTime.now());
        register.setNotes(request.getNotes());
        register.setHours(request.getHours());
        return registerRepository.save(register);
    }
}