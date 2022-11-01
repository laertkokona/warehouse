package al.example.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ItemDTO {
	
	private Long id;
	private String name;
	private String code;
	private Integer quantity;
	private BigDecimal unitPrice;

}
