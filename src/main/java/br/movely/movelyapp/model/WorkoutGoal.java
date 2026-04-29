package br.movely.movelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@DiscriminatorValue("WORKOUT")
@Getter @Setter @NoArgsConstructor
public class WorkoutGoal extends Goal {

    @Column(name = "workouts_target")
    private int workoutsTarget;

    @Column(name = "workout_type")
    private String workoutType;

    @Override
    public double calculateProgress(Map<String, Double> values) {
        double done = values.getOrDefault("workouts", 0.0);
        if (workoutsTarget <= 0) return 0.0;
        return Math.min(done / workoutsTarget, 1.0);
    }

    @Override
    public double calculatePoints(Map<String, Double> values) {
        double done = values.getOrDefault("workouts", 0.0);
        return done * 100.0;
    }
}