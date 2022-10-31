package al.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public UserModel saveUser(UserModel user) {
		log.info("Saving new user {} to database", user.getUsername());
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public UserModel getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username).isPresent() ? userRepo.findByUsername(username).get() : null;
	}
	
	@Override
	public UserDTO getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserModel> getAllUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}

}
