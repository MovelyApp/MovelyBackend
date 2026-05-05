package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseGroupDTO {

    private UUID id;
    private String name;
    private String description;
    private String urlImagem;


    public static ResponseGroupDTO toDTO(Group group) {
        ResponseGroupDTO dto = new ResponseGroupDTO();

        dto.setId(group.getId());
        dto.setDescription(group.getDescription());
        dto.setUrlImagem(group.getUrlImagem());
        dto.setName(group.getName());
        return dto;
    }
}
