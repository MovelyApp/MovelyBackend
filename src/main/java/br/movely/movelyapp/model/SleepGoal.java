package br.movely.movelyapp.model;

import br.movely.movelyapp.model.enums.SleepQuality;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@DiscriminatorValue("SLEEP")
@Getter @Setter @NoArgsConstructor
public class SleepGoal extends Goal {

    @Column(name = "hours_target")
    private double hoursTarget;

    @Override
    public double calculateProgress(Map<String, Double> values) {
        double slept = values.getOrDefault("hours", 0.0);
        if (hoursTarget <= 0) return 0.0;
        return Math.min(slept / hoursTarget, 1.0);
    }

    @Override
    public double calculatePoints(Map<String, Double> values) {
        double slept = values.getOrDefault("hours", 0.0);
        double qualityCode = values.getOrDefault("quality", 2.0);

        if (hoursTarget <= 0) return 0.0;

        double hoursPoints = (slept / hoursTarget) * 100.0;

        SleepQuality quality = SleepQuality.values()[(int) qualityCode];
        return hoursPoints + quality.getBonus();
    }
}