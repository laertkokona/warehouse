package al.example.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import al.example.model.UserModel;
import al.example.model.dto.UserDTO;

public interface UserService extends UserDetailsService {
	
	UserDTO getUserByUsername(String username);
	UserDTO getUserById(Long id);
	UserDTO saveUser(UserModel user);
	List<UserDTO> getAllUsers();

}
