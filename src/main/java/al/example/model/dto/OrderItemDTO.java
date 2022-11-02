package al.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItemDTO {
	
	private Long id;
	private ItemDTO item;
	private Integer quantity;

}
