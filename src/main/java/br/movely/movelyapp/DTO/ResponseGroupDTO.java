package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResponseGroupDTO {

    private UUID id;
    private String name;
    private String description;
    private String urlImagem;
    private Long ownerId;
    private String ownerEmail;
    private List<UserDTO> users;


    public static ResponseGroupDTO toDTO(Group group) {
        ResponseGroupDTO dto = new ResponseGroupDTO();

        dto.setId(group.getId());
        dto.setDescription(group.getDescription());
        dto.setUrlImagem(group.getUrlImagem());
        dto.setName(group.getName());
        if (group.getOwner() != null) {
            dto.setOwnerId(group.getOwner().getId());
            dto.setOwnerEmail(group.getOwner().getEmail() != null
                    ? group.getOwner().getEmail()
                    : group.getOwner().getUsername());
        }
        dto.setUsers(group.getUsers().stream().map(UserDTO::get).collect(Collectors.toList()));
        return dto;
    }
}
