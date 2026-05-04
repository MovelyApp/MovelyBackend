package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EditChallengeDTO {
    private String name;
    private String description;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}