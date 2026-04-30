package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRegisterRequest {
    private Long userId;
    private String notes;
    private String workoutType;
    private Integer durationMin;
    private Double weight;
}