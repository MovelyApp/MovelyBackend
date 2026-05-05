package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StudyRegisterRequest {
    private Long userId;
    private UUID groupId;
    private String notes;
    private Double hours;
}
