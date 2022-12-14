package al.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum OrderStatusesEnum {
	
	CREATED(1L, "CREATED"),
	AWAITING_APPROVAL(2L, "AWAITING_APPROVAL"),
	APPROVED(3L, "APPROVED"),
	DECLINED(4L, "DECLINED"),
	UNDER_DELIVERY(5L, "UNDER_DELIVERY"),
	FULFILLED(6L, "FULFILLED"),
	CANCELED(7L, "CANCELED");
	
	private Long id;
	private String name;
	
	public static OrderStatusesEnum getByName(String name) {
		for (OrderStatusesEnum os : values()) {
			if(os.getName().equalsIgnoreCase(name)) return os;
		}
		return null;
	}

}
