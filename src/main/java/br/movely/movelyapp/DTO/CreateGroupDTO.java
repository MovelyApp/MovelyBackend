package br.movely.movelyapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateGroupDTO {
    private String name;
    private String description;
}
