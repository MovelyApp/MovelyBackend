package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateWorkoutGoalDTO {
    private UUID challengeId;
    private String name;
    private String description;
    private int workoutsTarget;
    private String workoutType;
}