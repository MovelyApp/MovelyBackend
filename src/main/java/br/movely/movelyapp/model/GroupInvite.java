package br.movely.movelyapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_invites")
@Getter
@Setter
public class GroupInvite {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_DECLINED = "DECLINED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invited_user_id")
    private User invitedUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invited_by_id")
    private User invitedBy;

    private String status = STATUS_PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
}
