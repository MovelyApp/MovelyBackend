package br.movely.movelyapp.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupDTO {
    private String name;
    private String description;
    @JsonAlias("imageUrl")
    private String urlImagem;
}
