package al.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.UserModel;
import al.example.model.dto.UserDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseWrapper<UserDTO>> getAll(@RequestBody(required = false) Pagination pagination) {
		ResponseWrapper<UserDTO> res = userService.getAllUsers(pagination);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseWrapper<UserDTO>> getById(@PathVariable("id") Long id){
		ResponseWrapper<UserDTO> res = userService.getUserById(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper<UserDTO>> save(@RequestBody UserModel user){
		ResponseWrapper<UserDTO> res = userService.saveUser(user);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@PostMapping("/update")
	public ResponseEntity<ResponseWrapper<UserDTO>> update(@RequestBody UserModel user){
		ResponseWrapper<UserDTO> res = userService.updateUser(user);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<UserDTO>> delete(@PathVariable("id") Long id){
		ResponseWrapper<UserDTO> res = userService.passiveDeleteUser(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

}
