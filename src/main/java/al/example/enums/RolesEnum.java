package al.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum RolesEnum {
	
	SYSTEM_ADMIN(1L, "SYSTEM_ADMIN"),
	WAREHOUSE_MANAGER(2L, "WAREHOUSE_MANAGER"),
	CLIENT(3L, "CLIENT");
	
	private Long id;
	private String name;
	
	public static RolesEnum getByName(String name) {
		for (RolesEnum role : values()) {
			if(role.getName().equalsIgnoreCase(name)) return role;
		}
		return null;
	}

}
