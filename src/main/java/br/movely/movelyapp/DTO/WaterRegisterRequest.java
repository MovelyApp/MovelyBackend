package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WaterRegisterRequest {
    private Long userId;
    private UUID groupId;
    private String notes;
    private Double ml;
}
