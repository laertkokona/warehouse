package al.example.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DeliveryDTO {
	
	private Long id;
    private Date date;
    private List<TruckDTO> trucks;
    private List<OrderDTO> orders;

}
