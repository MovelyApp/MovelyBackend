package br.movely.movelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@DiscriminatorValue("STUDY")
@Getter
@Setter
@NoArgsConstructor
public class StudyGoal extends Goal {

    @Column(name = "hours_target")
    private double hoursTarget;

    @Override
    public double calculateProgress(Map<String, Double> values) {
        double studied = values.getOrDefault("hours", 0.0);

        if (hoursTarget <= 0) {
            return 0.0;
        }

        return Math.min(studied / hoursTarget, 1.0);
    }

    @Override
    public double calculatePoints(Map<String, Double> values) {
        double studied = values.getOrDefault("hours", 0.0);

        if (hoursTarget <= 0) {
            return 0.0;
        }

        return (studied / hoursTarget) * 100.0;
    }
}