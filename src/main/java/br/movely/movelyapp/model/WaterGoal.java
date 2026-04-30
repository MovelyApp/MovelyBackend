package br.movely.movelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@DiscriminatorValue("WATER")
@Getter
@Setter
@NoArgsConstructor
public class WaterGoal extends Goal {

    @Column(name = "ml_target")
    private double mlTarget;

    @Override
    public double calculateProgress(Map<String, Double> values) {
        double consumed = values.getOrDefault("ml", 0.0);

        if (mlTarget <= 0) {
            return 0.0;
        }

        return Math.min(consumed / mlTarget, 1.0);
    }

    @Override
    public double calculatePoints(Map<String, Double> values) {
        double consumed = values.getOrDefault("ml", 0.0);

        if (mlTarget <= 0) {
            return 0.0;
        }

        return (consumed / mlTarget) * 100.0;
    }
}