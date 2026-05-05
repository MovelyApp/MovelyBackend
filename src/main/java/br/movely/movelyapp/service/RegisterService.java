package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.*;
import br.movely.movelyapp.exceptions.ForbiddenException;
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

    public List<Register> getAllByUser(String username) {
        return registerRepository.findByUserId(userService.getInternalUser(username).getId());
    }

    public List<Register> getAllByGroup(UUID groupId) {
        return registerRepository.findByGroupId(groupId);
    }

    public List<Register> getAllByGroup(UUID groupId, String username) {
        validateGroupAccess(username, groupId);
        return getAllByGroup(groupId);
    }

    public List<Register> getAllByUserAndGroup(Long userId, UUID groupId) {
        return registerRepository.findByUserIdAndGroupId(userId, groupId);
    }

    public List<Register> getAllByUserAndGroup(Long userId, UUID groupId, String username) {
        validateGroupAccess(username, groupId);
        return getAllByUserAndGroup(userId, groupId);
    }

    private void validateGroupAccess(String username, UUID groupId) {
        Long currentUserId = userService.getInternalUser(username).getId();
        if (!groupService.canViewGroup(currentUserId, groupId)) {
            throw new ForbiddenException("You cannot view this group");
        }
    }

    private void fillBaseFields(Register register, Long userId, UUID groupId, String notes) {
        if (groupId == null) {
            throw new RuntimeException("Group Id is required");
        }
        if (userId == null) {
            throw new RuntimeException("User Id is required");
        }

        if (!groupService.isUserInGroup(userId, groupId)) {
            throw new RuntimeException("User is not in the group");
        }

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

    public WaterRegister createWaterRegister(WaterRegisterRequest request, String username) {
        request.setUserId(userService.getInternalUser(username).getId());
        return createWaterRegister(request);
    }

    public StepsRegister createStepsRegister(StepsRegisterRequest request, String username) {
        request.setUserId(userService.getInternalUser(username).getId());
        return createStepsRegister(request);
    }

    public SleepRegister createSleepRegister(SleepRegisterRequest request, String username) {
        request.setUserId(userService.getInternalUser(username).getId());
        return createSleepRegister(request);
    }

    public WorkoutRegister createWorkoutRegister(WorkoutRegisterRequest request, String username) {
        request.setUserId(userService.getInternalUser(username).getId());
        return createWorkoutRegister(request);
    }

    public StudyRegister createStudyRegister(StudyRegisterRequest request, String username) {
        request.setUserId(userService.getInternalUser(username).getId());
        return createStudyRegister(request);
    }
}
