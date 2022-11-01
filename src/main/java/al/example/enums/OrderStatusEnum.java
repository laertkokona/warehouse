package al.example.enums;

public enum OrderStatusEnum {
	
	CREATED(1L, "CREATED"),
	AWAITING_APPROVAL(2L, "AWAITING_APPROVAL"),
	APPROVED(3L, "APPROVED"),
	DECLINED(4L, "DECLINED"),
	UNDER_DELIVERY(5L, "UNDER_DELIVERY"),
	FULFILLED(6L, "FULFILLED"),
	CANCELED(7L, "CANCELED");
	
	private Long id;
	private String name;
	
	OrderStatusEnum(Long id, String name){
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
