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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;
	private final BCryptPasswordEncoder encoder;
	private final ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> userOpt = userRepo.findByUsername(username);
		log.info("Checking if username {} exists", username);
		if (userOpt.isEmpty() || !userOpt.get().isEnabled()) {
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
		Optional<UserModel> userOpt = userRepo.findByUsername(username);
		checkIfExists(userOpt);
		return convertToDTO(userOpt.get());
	}

	@Override
	public UserDTO getUserById(Long id) {
		log.info("Fetching User with id {} from database", id);
		Optional<UserModel> userOpt = userRepo.findById(id);
		checkIfExists(userOpt);
		return convertToDTO(userOpt.get());
	}

	@Override
	public List<UserDTO> getAllUsers() {
		log.info("Fetching all users");
//		return userRepo.findAll();
		return null;
	}

	@Override
	@Transactional
	public void passiveDeleteUser(Long id) {
		log.info("Fetching User with id: {} from database", id);
		Optional<UserModel> userOpt = userRepo.findById(id);
		checkIfExists(userOpt);
		log.info("Deleting User with id {}", id);
		UserModel user = userOpt.get();
		user.setActive(false);
	}

	private UserDTO convertToDTO(UserModel user) {
		return modelMapper.map(user, UserDTO.class);
	}

	private void checkIfExists(Optional<UserModel> userOpt) {
		if (userOpt.isEmpty() || !userOpt.get().isEnabled()) {
			if (userOpt.isEmpty())
				log.error("User not found");
			else
				log.error("User is not active");
			throw new GeneralException("User not found", null);
		}
	}

}
