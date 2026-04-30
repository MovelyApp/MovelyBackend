package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SleepRegisterRequest {
    private Long userId;
    private String notes;
    private Double hours;
    private Integer quality;
}