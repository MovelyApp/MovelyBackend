package br.movely.movelyapp.repository;

import br.movely.movelyapp.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
    Page<Group> findByUsers_Id(Long userId, Pageable pageable);
}
