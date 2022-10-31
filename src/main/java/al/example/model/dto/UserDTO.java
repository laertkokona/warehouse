package al.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {
	
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private RoleDTO role;

}
