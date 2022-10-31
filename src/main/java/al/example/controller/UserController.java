package al.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.UserModel;
import al.example.model.dto.UserDTO;
import al.example.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/getAll")
	public ResponseEntity<UserDTO> getAll(@RequestParam(defaultValue="1") Integer pageNo,
            							@RequestParam(defaultValue="10") Integer pageSize,
            							@RequestParam(defaultValue="id") String sortBy,
            							@RequestParam(defaultValue="asc") String sortType) {
		
		return null;
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PostMapping("/save")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserModel user){
		return ResponseEntity.ok(userService.saveUser(user));
	}

}
