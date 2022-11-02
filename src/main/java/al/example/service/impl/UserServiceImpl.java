package al.example.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import al.example.exception.GeneralException;
import al.example.model.UserModel;
import al.example.model.dto.UserDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
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
	public ResponseWrapper<UserDTO> saveUser(UserModel user) {
		log.info("Saving new user {} to database", user.getUsername());
		user.setPassword(encoder.encode(user.getPassword()));
		user = userRepo.save(user);
		return new ResponseWrapper<UserDTO>(true, Arrays.asList(convertToDTO(user)), "Success");
	}

	@Override
	public ResponseWrapper<UserDTO> updateUser(Long id, UserModel user) {
		log.info("Fetching User with id {} from database", id);
		Optional<UserModel> userOpt = userRepo.findById(id);
		checkIfExists(userOpt);
		log.info("Updating User {}", user.getUsername());
		user.setPassword(userOpt.get().getPassword());
		user.setActive(userOpt.get().getActive());
		UserModel updatedUser = userRepo.save(user);
		return new ResponseWrapper<UserDTO>(true, Arrays.asList(convertToDTO(updatedUser)), "Success");
	}

	@Override
	public ResponseWrapper<UserDTO> getUserByUsername(String username) {
		log.info("Fetching User {} from database", username);
		Optional<UserModel> userOpt = userRepo.findByUsername(username);
		checkIfExists(userOpt);
		return new ResponseWrapper<UserDTO>(true, Arrays.asList(convertToDTO(userOpt.get())), "Success");
	}

	@Override
	public ResponseWrapper<UserDTO> getUserById(Long id) {
		log.info("Fetching User with id {} from database", id);
		Optional<UserModel> userOpt = userRepo.findById(id);
		checkIfExists(userOpt);
		return new ResponseWrapper<UserDTO>(true, Arrays.asList(convertToDTO(userOpt.get())), "Success");
	}

	@Override
	public ResponseWrapper<UserDTO> getAllUsers(Pagination pagination) {
		if(pagination == null) pagination = new Pagination();
		log.info("Fetching all Users with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());
		List<UserModel> usersModel = userRepo.findAll(pageable).getContent();
		List<UserDTO> usersDTO = usersModel.stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseWrapper<UserDTO>(true, usersDTO, "Success");
	}

	@Override
	@Transactional
	public ResponseWrapper<UserDTO> passiveDeleteUser(Long id) {
		try {
			log.info("Fetching User with id {} from database", id);
			Optional<UserModel> userOpt = userRepo.findById(id);
			checkIfExists(userOpt);
			log.info("Deleting User with id {}", id);
			UserModel user = userOpt.get();
			user.setActive(false);
			return new ResponseWrapper<UserDTO>(true, null, "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<UserDTO>(false, null, e.getMessage());
		}

	}

	private UserDTO convertToDTO(UserModel user) {
		log.info("Converting User Model to DTO");
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
