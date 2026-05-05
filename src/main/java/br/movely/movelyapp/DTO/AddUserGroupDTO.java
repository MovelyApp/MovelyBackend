package br.movely.movelyapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class AddUserGroupDTO {
    UUID groupId;
    Long userId;
}
