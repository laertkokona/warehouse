package al.example.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItemDTO {
	
	private Long id;
	private ItemDTO item;
	private String name;
	private String code;
	private BigDecimal unitPrice;
	private Integer quantity;

}
