package al.example.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
	
	private Long id;
	private Date submittedDate;
	private Date deadlineDate;
	private String code;
	private OrderStatusDTO orderStatus;
	private List<OrderItemDTO> items;
	private Long userId;

}
