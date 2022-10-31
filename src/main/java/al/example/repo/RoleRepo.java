package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.RoleModel;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, Long> {
	
	Optional<RoleModel> findByName(String name);

}
