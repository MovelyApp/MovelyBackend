package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyRegisterRequest {
    private Long userId;
    private String notes;
    private Double hours;
}