package br.movely.movelyapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RemoveUserGroupDTO {
    UUID groupId;
    Long userId;
}
