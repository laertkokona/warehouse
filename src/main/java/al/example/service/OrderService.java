package al.example.service;

import al.example.model.dto.OrderDTO;

public interface OrderService {
	
	OrderDTO getOrderById(Long id);

}
