package al.example.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import al.example.exception.GeneralException;
import al.example.model.OrderModel;
import al.example.model.dto.OrderDTO;
import al.example.repo.OrderRepo;
import al.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepo orderRepo;
	private final ModelMapper modelMapper;
	
	private OrderDTO convertToDTO(OrderModel order) {
		return modelMapper.map(order, OrderDTO.class);
	}
	
	private void checkIfExists(Optional<OrderModel> truckOpt) {
		if(truckOpt.isEmpty()) {
			log.error("Order not found");
			throw new GeneralException("Order not found", null);
		}
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		log.info("Fetching Truck with id {} from database", id);
		Optional<OrderModel> orderOpt = orderRepo.findById(id);
		checkIfExists(orderOpt);
		return convertToDTO(orderOpt.get());
	}
	
}
