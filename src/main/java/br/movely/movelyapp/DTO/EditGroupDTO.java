package br.movely.movelyapp.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditGroupDTO {
    private String name;
    private String description;
    @JsonAlias("imageUrl")
    private String urlImagem;
}
