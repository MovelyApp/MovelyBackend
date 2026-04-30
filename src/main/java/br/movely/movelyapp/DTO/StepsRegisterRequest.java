package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepsRegisterRequest {
    private Long userId;
    private String notes;
    private Integer steps;
}