package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.model.*;
import br.movely.movelyapp.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    public List<Register> getAllByUser(Long userId) {
        return registerRepository.findByUserId(userId);
    }

    public List<Register> getAllByGroup(UUID groupId) {
        return registerRepository.findByGroupId(groupId);
    }

    public List<Register> getAllByUserAndGroup(Long userId, UUID groupId) {
        return registerRepository.findByUserIdAndGroupId(userId, groupId);
    }

    private void fillBaseFields(Register register, Long userId, UUID groupId, String notes) {
        if (groupId == null) {
            throw new RuntimeException("Group Id is required");
        }

        groupService.findGroup(groupId);

        register.setUser(userService.getInternalUser(userId));
        register.setGroupId(groupId);
        register.setDateTime(LocalDateTime.now());
        register.setNotes(notes);
    }

    public WaterRegister createWaterRegister(WaterRegisterRequest request) {
        WaterRegister register = new WaterRegister();
        fillBaseFields(register, request.getUserId(), request.getGroupId(), request.getNotes());
        register.setMl(request.getMl());
        return registerRepository.save(register);
    }

    public StepsRegister createStepsRegister(StepsRegisterRequest request) {
        StepsRegister register = new StepsRegister();
        fillBaseFields(register, request.getUserId(), request.getGroupId(), request.getNotes());
        register.setSteps(request.getSteps());
        return registerRepository.save(register);
    }

    public SleepRegister createSleepRegister(SleepRegisterRequest request) {
        SleepRegister register = new SleepRegister();
        fillBaseFields(register, request.getUserId(), request.getGroupId(), request.getNotes());
        register.setHours(request.getHours());
        register.setQuality(request.getQuality());
        return registerRepository.save(register);
    }

    public WorkoutRegister createWorkoutRegister(WorkoutRegisterRequest request) {
        WorkoutRegister register = new WorkoutRegister();
        fillBaseFields(register, request.getUserId(), request.getGroupId(), request.getNotes());
        register.setWorkoutType(request.getWorkoutType());
        register.setDurationMin(request.getDurationMin());
        register.setWeight(request.getWeight());
        return registerRepository.save(register);
    }

    public StudyRegister createStudyRegister(StudyRegisterRequest request) {
        StudyRegister register = new StudyRegister();
        fillBaseFields(register, request.getUserId(), request.getGroupId(), request.getNotes());
        register.setHours(request.getHours());
        return registerRepository.save(register);
    }
}
