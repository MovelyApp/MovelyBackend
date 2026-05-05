package br.movely.movelyapp.repository;

import br.movely.movelyapp.model.GroupInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupInviteRepository extends JpaRepository<GroupInvite, Long> {
    List<GroupInvite> findByInvitedUser_IdAndStatus(Long invitedUserId, String status);

    Optional<GroupInvite> findByGroup_IdAndInvitedUser_IdAndStatus(UUID groupId, Long invitedUserId, String status);
}
