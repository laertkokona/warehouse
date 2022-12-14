package al.example.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {
	
	Optional<UserModel> findByUsername(String username);
	Page<UserModel> findByActive(Boolean active, Pageable pageable);

}
