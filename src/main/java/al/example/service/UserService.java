package al.example.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import al.example.model.UserModel;
import al.example.model.dto.UserDTO;

public interface UserService extends UserDetailsService {
	
	UserModel getUserByUsername(String username);
	UserDTO getUserById(Long id);
	UserModel saveUser(UserModel user);
	List<UserModel> getAllUsers();

}
