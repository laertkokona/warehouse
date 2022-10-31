package al.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import al.example.exception.GeneralException;
import al.example.model.UserModel;
import al.example.model.dto.UserDTO;
import al.example.repo.UserRepo;
import al.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Transactional @Slf4j @RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepo userRepo;
	private final BCryptPasswordEncoder encoder;
	private final ModelMapper modelMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> userOpt = userRepo.findByUsername(username);
		log.info("Checking if username {} exists", username);
		if(userOpt.isEmpty() || !userOpt.get().isEnabled()) {
			log.info("User with username {} not found", username);
			throw new UsernameNotFoundException("User with username " + username + " not found");
		}
				
		return new User(userOpt.get().getUsername(), userOpt.get().getPassword(), userOpt.get().getAuthorities());
	}

	@Override
	public UserDTO saveUser(UserModel user) {
		log.info("Saving new user {} to database", user.getUsername());
		user.setPassword(encoder.encode(user.getPassword()));
		user = userRepo.save(user);
//		return userRepo.save(user);
		return convertToDTO(user);
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		log.info("Fetching User {} from database", username);
		UserModel user = userRepo.findByUsername(username).orElseThrow(() -> new GeneralException()) ;
		return convertToDTO(user);
	}
	
	@Override
	public UserDTO getUserById(Long id) {
		log.info("Fetching User with id: {} from database", id);
		UserModel user = userRepo.findById(id).orElseThrow(() -> new IllegalStateException());
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		log.info("Fetching all users");
//		return userRepo.findAll();
		return null;
	}
	
	private UserDTO convertToDTO(UserModel user) {
		return modelMapper.map(user, UserDTO.class);
	}

}
