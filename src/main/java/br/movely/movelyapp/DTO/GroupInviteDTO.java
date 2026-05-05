package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.GroupInvite;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class GroupInviteDTO {
    private Long id;
    private UUID groupId;
    private String groupName;
    private String invitedUsername;
    private String invitedEmail;
    private String invitedByEmail;
    private String status;
    private LocalDateTime createdAt;

    public static GroupInviteDTO toDTO(GroupInvite invite) {
        GroupInviteDTO dto = new GroupInviteDTO();
        dto.setId(invite.getId());
        dto.setGroupId(invite.getGroup().getId());
        dto.setGroupName(invite.getGroup().getName());
        dto.setInvitedUsername(invite.getInvitedUser().getUsername());
        dto.setInvitedEmail(invite.getInvitedUser().getEmail());
        dto.setInvitedByEmail(invite.getInvitedBy().getEmail() != null
                ? invite.getInvitedBy().getEmail()
                : invite.getInvitedBy().getUsername());
        dto.setStatus(invite.getStatus());
        dto.setCreatedAt(invite.getCreatedAt());
        return dto;
    }
}
