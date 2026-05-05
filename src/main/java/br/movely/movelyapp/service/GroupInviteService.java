package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.CreateGroupInviteDTO;
import br.movely.movelyapp.DTO.GroupInviteDTO;
import br.movely.movelyapp.exceptions.ForbiddenException;
import br.movely.movelyapp.exceptions.NotFoundException;
import br.movely.movelyapp.model.Group;
import br.movely.movelyapp.model.GroupInvite;
import br.movely.movelyapp.model.User;
import br.movely.movelyapp.repository.GroupInviteRepository;
import br.movely.movelyapp.repository.GroupRepository;
import br.movely.movelyapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupInviteService {

    private final GroupInviteRepository inviteRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupInviteService(GroupInviteRepository inviteRepository,
                              GroupRepository groupRepository,
                              UserRepository userRepository) {
        this.inviteRepository = inviteRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public GroupInviteDTO invite(CreateGroupInviteDTO request, String inviterUsername) {
        if (request == null || request.getGroupId() == null) {
            throw new IllegalArgumentException("Group id is required");
        }

        String email = normalizeEmail(request.getEmail());
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        User inviter = findUserByIdentity(inviterUsername);
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group Not Found"));

        if (!canInvite(inviter, group)) {
            throw new ForbiddenException("Only the group creator can invite members");
        }

        User invitedUser = findUserByIdentity(email);
        Long invitedUserId = invitedUser.getId();

        boolean alreadyInGroup = group.getUsers().stream()
                .anyMatch(member -> invitedUserId.equals(member.getId()));
        if (alreadyInGroup) {
            throw new IllegalArgumentException("User already in the group");
        }

        Optional<GroupInvite> existingInvite = inviteRepository
                .findByGroup_IdAndInvitedUser_IdAndStatus(group.getId(), invitedUserId, GroupInvite.STATUS_PENDING);
        if (existingInvite.isPresent()) {
            return GroupInviteDTO.toDTO(existingInvite.get());
        }

        GroupInvite invite = new GroupInvite();
        invite.setGroup(group);
        invite.setInvitedBy(inviter);
        invite.setInvitedUser(invitedUser);
        invite.setStatus(GroupInvite.STATUS_PENDING);

        return GroupInviteDTO.toDTO(inviteRepository.save(invite));
    }

    @Transactional(readOnly = true)
    public List<GroupInviteDTO> listMine(String username) {
        User currentUser = findUserByIdentity(username);
        return inviteRepository
                .findByInvitedUser_IdAndStatus(currentUser.getId(), GroupInvite.STATUS_PENDING)
                .stream()
                .map(GroupInviteDTO::toDTO)
                .collect(Collectors.toList());
    }

    public GroupInviteDTO accept(Long inviteId, String username) {
        GroupInvite invite = findInviteForCurrentUser(inviteId, username);
        Group group = invite.getGroup();
        Long invitedUserId = invite.getInvitedUser().getId();

        boolean alreadyInGroup = group.getUsers().stream()
                .anyMatch(member -> invitedUserId.equals(member.getId()));
        if (!alreadyInGroup) {
            group.addUser(invite.getInvitedUser());
            groupRepository.save(group);
        }

        invite.setStatus(GroupInvite.STATUS_ACCEPTED);
        return GroupInviteDTO.toDTO(inviteRepository.save(invite));
    }

    public GroupInviteDTO decline(Long inviteId, String username) {
        GroupInvite invite = findInviteForCurrentUser(inviteId, username);
        invite.setStatus(GroupInvite.STATUS_DECLINED);
        return GroupInviteDTO.toDTO(inviteRepository.save(invite));
    }

    private GroupInvite findInviteForCurrentUser(Long inviteId, String username) {
        if (inviteId == null) {
            throw new IllegalArgumentException("Invite id is required");
        }

        User currentUser = findUserByIdentity(username);
        GroupInvite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new NotFoundException("Invite Not Found"));

        if (!currentUser.getId().equals(invite.getInvitedUser().getId())) {
            throw new ForbiddenException("This invite belongs to another user");
        }

        if (!GroupInvite.STATUS_PENDING.equals(invite.getStatus())) {
            throw new IllegalArgumentException("Invite is not pending");
        }

        return invite;
    }

    private boolean canInvite(User inviter, Group group) {
        if (group.getOwner() != null) {
            return inviter.getId().equals(group.getOwner().getId());
        }

        return group.getUsers().stream()
                .anyMatch(member -> inviter.getId().equals(member.getId()));
    }

    private User findUserByIdentity(String identity) {
        String normalizedIdentity = normalizeEmail(identity);
        Optional<User> user = userRepository.findByEmailIgnoreCase(normalizedIdentity);

        if (!user.isPresent()) {
            user = userRepository.findByUsernameIgnoreCase(normalizedIdentity);
        }

        return user.orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}
