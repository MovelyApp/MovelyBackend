package br.movely.movelyapp.service;

import br.movely.movelyapp.model.Goal;
import br.movely.movelyapp.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import br.movely.movelyapp.model.SleepGoal;
import br.movely.movelyapp.model.StepsGoal;
import br.movely.movelyapp.model.StudyGoal;
import br.movely.movelyapp.model.WaterGoal;
import br.movely.movelyapp.model.WorkoutGoal;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> listAll() {
        return goalRepository.findAll();
    }

    public List<Goal> listByChallenge(UUID challengeId) {
        return goalRepository.findByChallengeId(challengeId);
    }

    public Goal findById(UUID id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found: " + id));
    }

    public void delete(UUID id) {
        if (!goalRepository.existsById(id)) {
            throw new RuntimeException("Goal not found: " + id);
        }
        goalRepository.deleteById(id);
    }

    // metodos para criar as subclasses

    public SleepGoal createSleepGoal(UUID challengeId, String name, String description, double hoursTarget) {
        SleepGoal goal = new SleepGoal();
        goal.setChallengeId(challengeId);
        goal.setName(name);
        goal.setDescription(description);
        goal.setHoursTarget(hoursTarget);
        return goalRepository.save(goal);
    }

    public StepsGoal createStepsGoal(UUID challengeId, String name, String description, int stepsTarget) {
        StepsGoal goal = new StepsGoal();
        goal.setChallengeId(challengeId);
        goal.setName(name);
        goal.setDescription(description);
        goal.setStepsTarget(stepsTarget);
        return goalRepository.save(goal);
    }

    public StudyGoal createStudyGoal(UUID challengeId, String name, String description, double hoursTarget) {
        StudyGoal goal = new StudyGoal();
        goal.setChallengeId(challengeId);
        goal.setName(name);
        goal.setDescription(description);
        goal.setHoursTarget(hoursTarget);
        return goalRepository.save(goal);
    }

    public WaterGoal createWaterGoal(UUID challengeId, String name, String description, double mlTarget) {
        WaterGoal goal = new WaterGoal();
        goal.setChallengeId(challengeId);
        goal.setName(name);
        goal.setDescription(description);
        goal.setMlTarget(mlTarget);
        return goalRepository.save(goal);
    }

    public WorkoutGoal createWorkoutGoal(UUID challengeId, String name, String description, int workoutsTarget, String workoutType) {
        WorkoutGoal goal = new WorkoutGoal();
        goal.setChallengeId(challengeId);
        goal.setName(name);
        goal.setDescription(description);
        goal.setWorkoutsTarget(workoutsTarget);
        goal.setWorkoutType(workoutType);
        return goalRepository.save(goal);
    }

}