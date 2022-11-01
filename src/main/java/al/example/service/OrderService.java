package al.example.service;

import al.example.model.OrderModel;
import al.example.model.dto.OrderDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface OrderService {
	
	ResponseWrapper<OrderDTO> getOrderById(Long id);
	ResponseWrapper<OrderDTO> getAllOrders(Pagination pagination);
	ResponseWrapper<OrderDTO> saveOrder(OrderModel order);
	ResponseWrapper<OrderDTO> deleteOrder(Long id);

}
