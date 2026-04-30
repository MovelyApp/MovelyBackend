package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.Group;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseGroupDTO {

    private String name;
    private String description;
    private String urlImagem;


    public static ResponseGroupDTO toDTO(Group professor) {
        ResponseGroupDTO dto = new ResponseGroupDTO();

        dto.setDescription(professor.getDescription());
        dto.setUrlImagem(professor.getUrlImagem());
        dto.setName(professor.getName());
        return dto;
    }
}
