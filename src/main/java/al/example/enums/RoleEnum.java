package al.example.enums;

public enum RoleEnum {
	
	SYSTEM_ADMIN(1L, "SYSTEM_ADMIN"),
	WAREHOUSE_MANAGER(2L, "WAREHOUSE_MANAGER"),
	CLIENT(3L, "CLIENT");
	
	private Long id;
	private String name;
	
	RoleEnum(Long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

}
