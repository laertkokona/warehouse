package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.UserModel;

public interface UserRepo extends JpaRepository<UserModel, Long> {
	
	Optional<UserModel> findByUsername(String username);

}
