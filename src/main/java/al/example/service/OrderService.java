package al.example.service;

import java.util.List;

import al.example.model.OrderItemModel;
import al.example.model.OrderModel;
import al.example.model.dto.BasicOrderDTO;
import al.example.model.dto.OrderDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface OrderService {
	
	ResponseWrapper<OrderDTO> getOrderById(Long id, String username);
	ResponseWrapper<BasicOrderDTO> getAllOrdersByUsernameAndStatusFilter(Pagination pagination, String authHeader, String statusName);
	ResponseWrapper<OrderDTO> createOrder(OrderModel order, String username);
	ResponseWrapper<OrderDTO> deleteOrder(Long id);
	ResponseWrapper<OrderDTO> editOrder(Long id, List<OrderItemModel> items);
	ResponseWrapper<OrderDTO> cancelOrder(Long id, String username);
	ResponseWrapper<OrderDTO> submitOrder(Long id, String username);
	ResponseWrapper<OrderDTO> approveOrder(Long id, Boolean isApproved);
	OrderModel deliverOrder(Long id);
	OrderModel fulfillOrder(Long id);
	
}
