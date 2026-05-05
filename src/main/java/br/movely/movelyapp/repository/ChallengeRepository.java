package br.movely.movelyapp.repository;

import br.movely.movelyapp.model.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {
    Page<Challenge> findByGroupIdIn(List<UUID> groupIds, Pageable pageable);
}
