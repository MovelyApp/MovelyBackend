package br.movely.movelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@DiscriminatorValue("STEPS")
@Getter @Setter @NoArgsConstructor
public class StepsGoal extends Goal {

    @Column(name = "steps_target")
    private int stepsTarget;

    @Override
    public double calculateProgress(Map<String, Double> values) {
        double taken = values.getOrDefault("steps", 0.0);
        if (stepsTarget <= 0) return 0.0;
        return Math.min(taken / stepsTarget, 1.0);
    }

    @Override
    public double calculatePoints(Map<String, Double> values) {
        double taken = values.getOrDefault("steps", 0.0);
        if (stepsTarget <= 0) return 0.0;
        return (taken / stepsTarget) * 100.0;
    }
}