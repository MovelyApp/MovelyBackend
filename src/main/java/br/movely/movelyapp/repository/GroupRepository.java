package br.movely.movelyapp.repository;


import br.movely.movelyapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
