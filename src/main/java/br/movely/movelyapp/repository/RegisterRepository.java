package br.movely.movelyapp.repository;

import br.movely.movelyapp.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
    List<Register> findByUserId(Long userId);
    List<Register> findByGroupId(UUID groupId);
    List<Register> findByUserIdAndGroupId(Long userId, UUID groupId);
}
