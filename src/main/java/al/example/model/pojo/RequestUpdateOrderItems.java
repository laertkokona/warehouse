package al.example.model.pojo;

import java.util.List;

import al.example.model.OrderItemModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RequestUpdateOrderItems {
	
	private List<OrderItemModel> items;

}
