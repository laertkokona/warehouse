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
	private OrderStatusDTO orderStatus;
	private Date deadlineDate;
	private List<ItemDTO> items;
	private DeliveryDTO delivery;

}
