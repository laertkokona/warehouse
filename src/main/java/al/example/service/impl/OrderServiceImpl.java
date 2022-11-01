package al.example.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import al.example.exception.GeneralException;
import al.example.model.OrderModel;
import al.example.model.dto.OrderDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.repo.OrderRepo;
import al.example.repo.OrderStatusRepo;
import al.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepo orderRepo;
	private final OrderStatusRepo orderStatusRepo;
	private final ModelMapper modelMapper;
	
	private OrderDTO convertToDTO(OrderModel order) {
		log.info("Converting Order Model to DTO");
		return modelMapper.map(order, OrderDTO.class);
	}
	
	private void checkIfExists(Optional<OrderModel> truckOpt) {
		if(truckOpt.isEmpty()) {
			log.error("Order not found");
			throw new GeneralException("Order not found", null);
		}
	}

	@Override
	public ResponseWrapper<OrderDTO> getOrderById(Long id) {
		log.info("Fetching Truck with id {} from database", id);
		Optional<OrderModel> orderOpt = orderRepo.findById(id);
		checkIfExists(orderOpt);
		return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(orderOpt.get())), "Success");
	}

	@Override
	public ResponseWrapper<OrderDTO> getAllOrders(Pagination pagination) {
		log.info("Fetching all Orders with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());
		List<OrderModel> ordersModel = orderRepo.findAll(pageable).getContent();
		List<OrderDTO> ordersDTO = ordersModel.stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseWrapper<OrderDTO>(true, ordersDTO, "Success");
	}

	@Override
	public ResponseWrapper<OrderDTO> saveOrder(OrderModel order) {
		log.info("Generating Code for new Order");
		order.setCode("ORD_" + orderRepo.getCodeSequence().toString());
		order.setOrderStatus(orderStatusRepo.findByInitialStatus(true).get());
		log.info("Saving new Order to database");
		order = orderRepo.save(order);
		return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(order)), "Success");
	}

	@Override
	public ResponseWrapper<OrderDTO> deleteOrder(Long id) {
		try {
			log.info("Fetching Order with id {} from database", id);
			Optional<OrderModel> orderOpt = orderRepo.findById(id);
			checkIfExists(orderOpt);
			log.info("Deleting Order with id {} from database", id);
			orderRepo.deleteById(id);
			return new ResponseWrapper<OrderDTO>(true, null, "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<OrderDTO>(false, null, e.getMessage());
		}
	}
	
}
