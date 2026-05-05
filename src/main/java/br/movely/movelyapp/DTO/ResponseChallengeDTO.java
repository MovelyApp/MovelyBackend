package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.Challenge;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ResponseChallengeDTO {

    private UUID id;
    private UUID groupId;
    private String name;
    private String description;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public static ResponseChallengeDTO toDTO(Challenge challenge) {
        ResponseChallengeDTO dto = new ResponseChallengeDTO();

        dto.setId(challenge.getId());
        dto.setGroupId(challenge.getGroupId());
        dto.setName(challenge.getName());
        dto.setDescription(challenge.getDescription());
        dto.setDataInicio(challenge.getDataInicio());
        dto.setDataFim(challenge.getDataFim());

        return dto;
    }
}