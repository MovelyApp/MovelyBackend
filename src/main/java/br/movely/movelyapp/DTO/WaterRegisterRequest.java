package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaterRegisterRequest {
    private Long userId;
    private String notes;
    private Double ml;
}