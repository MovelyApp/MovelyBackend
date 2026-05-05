package br.movely.movelyapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateGroupInviteDTO {
    private UUID groupId;
    private String email;
}
