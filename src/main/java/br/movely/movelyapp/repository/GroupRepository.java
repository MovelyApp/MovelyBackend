package br.movely.movelyapp.repository;

import br.movely.movelyapp.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
    Page<Group> findByUsers_Id(Long userId, Pageable pageable);

    @Query("select distinct g from Group g left join g.users u left join g.owner o where u.id = :userId or o.id = :userId")
    Page<Group> findVisibleToUser(@Param("userId") Long userId, Pageable pageable);

    @Query("select distinct g.id from Group g left join g.users u left join g.owner o where u.id = :userId or o.id = :userId")
    List<UUID> findVisibleGroupIdsToUser(@Param("userId") Long userId);
}
