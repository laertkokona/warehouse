package al.example.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import al.example.model.UserModel;
import al.example.model.dto.UserDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface UserService extends UserDetailsService {
	
	ResponseWrapper<UserDTO> getUserByUsername(String username);
	ResponseWrapper<UserDTO> getUserById(Long id);
	ResponseWrapper<UserDTO> saveUser(UserModel user);
	ResponseWrapper<UserDTO> updateUser(Long id, UserModel user);
	ResponseWrapper<List<UserDTO>> getAllUsers(Pagination pagination);
	ResponseWrapper<UserDTO> passiveDeleteUser(Long id);

}
