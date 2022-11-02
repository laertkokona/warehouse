package al.example.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import al.example.enums.OrderStatusesEnum;
import al.example.enums.RolesEnum;
import al.example.exception.GeneralException;
import al.example.model.BasicOrderModel;
import al.example.model.OrderItemModel;
import al.example.model.OrderModel;
import al.example.model.OrderStatusModel;
import al.example.model.UserModel;
import al.example.model.dto.BasicOrderDTO;
import al.example.model.dto.IOrderStatusActionDTO;
import al.example.model.dto.OrderDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.repo.BasicOrderRepo;
import al.example.repo.OrderRepo;
import al.example.repo.OrderStatusActionRepo;
import al.example.repo.OrderStatusRepo;
import al.example.repo.UserRepo;
import al.example.service.ItemService;
import al.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepo orderRepo;
	private final OrderStatusRepo orderStatusRepo;
	private final OrderStatusActionRepo orderStatusActionRepo;
	private final BasicOrderRepo basicOrderRepo;
	private final ItemService itemService;
	private final UserRepo userRepo;
	private final ModelMapper modelMapper;

	private OrderDTO convertToDTO(OrderModel order) {
		log.info("Converting Order Model to DTO");
		return modelMapper.map(order, OrderDTO.class);
	}
	
	private BasicOrderDTO convertToDTOBasic(BasicOrderModel order) {
		log.info("Converting Basic Order Model to DTO");
		return modelMapper.map(order, BasicOrderDTO.class);
	}

	private void checkIfExists(Optional<OrderModel> truckOpt) {
		log.info("Checking if Order exists");
		if (truckOpt.isEmpty()) {
			log.error("Order not found");
			throw new GeneralException("Order not found", null);
		}
	}
	
	private void checkIfOrderBelongsToClient(UserModel user, OrderModel order, String username) {
		if(user.getRole().getName().equalsIgnoreCase(RolesEnum.CLIENT.getName())) {
			if(!order.getUsername().equals(username)) {
				throw new AccessDeniedException("You are not allowed to execute this action!");
			}
		}
	}

	@Override
	public ResponseWrapper<OrderDTO> getOrderById(Long id, String username) {
		UserModel user = userRepo.findByUsername(username).get();
		log.info("Fetching Truck with id {} from database", id);
		Optional<OrderModel> orderOpt = orderRepo.findById(id);
		checkIfExists(orderOpt);
		checkIfOrderBelongsToClient(user, orderOpt.get(), username);
		return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(orderOpt.get())), "Success");
	}

	@Override
	public ResponseWrapper<BasicOrderDTO> getAllOrdersByUsernameAndStatusFilter(Pagination pagination, String authHeader, String statusName) {
		String token = authHeader.substring("Bearer ".length());
		Algorithm algorithm = Algorithm.HMAC256("superSecretAlgorithmHMAC256".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		String username = decodedJWT.getSubject();
//		String role = decodedJWT.getClaim("roles").asArray(String.class)[0];
		List<BasicOrderModel> ordersModel = new ArrayList<BasicOrderModel>();
		if(pagination == null) pagination = new Pagination();
		log.info("Fetching all Orders with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());
		log.info("Getting data from database");
		if(username == null && statusName == null) {
			log.info("CASE 1: username == null && statusName == null");
			ordersModel = basicOrderRepo.findAll(pageable).getContent();
		} else if (username == null && statusName != null) {
			log.info("CASE 2: userId == null && statusName != null");
			ordersModel = basicOrderRepo.findByOrderStatus_Name(statusName, pageable).getContent();
		} else if (username != null && statusName != null) {
			log.info("CASE 3: userId != null && statusName != null");
			ordersModel = basicOrderRepo.findByUsernameAndOrderStatus_Name(username, statusName, pageable).getContent();
		}
		List<BasicOrderDTO> ordersDTO = ordersModel.stream().map(this::convertToDTOBasic).collect(Collectors.toList());
		return new ResponseWrapper<BasicOrderDTO>(true, ordersDTO, "Success");
	}

	@Override
	public ResponseWrapper<OrderDTO> createOrder(OrderModel order, String username) {
		log.info("Generating Code for new Order");
		order.setCode("ORD_" + orderRepo.getCodeSequence().toString());
		order.setOrderStatus(orderStatusRepo.findByInitialStatus(true).get());
		log.info("Updating Order Items' Quantities");
		order.getItems().stream()
				.forEach(item -> itemService.updateItemAvailableQuantity(item.getItem().getId(), item.getQuantity()));
		log.info("Saving new Order to database");
		order.setUsername(username);
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

	@Override
	public ResponseWrapper<OrderDTO> editOrder(Long id, List<OrderItemModel> items) {
		try {
			log.info("Fetching Order with id {} from database", id);
			OrderModel orderDb = orderRepo.findById(id)
					.orElseThrow(() -> new GeneralException("Order not found", null));
			IOrderStatusActionDTO osaDTO = orderStatusActionRepo
					.findOrderStatusActionByOrderStatusName(orderDb.getOrderStatus().getName().toLowerCase())
					.orElseThrow(() -> new GeneralException("Cannot find the Order Status Action row", null));
			if (orderDb.getOrderStatus() == null) {
				log.error("Order does not have a Status");
				throw new GeneralException("Order does not have a Status", Arrays.asList(convertToDTO(orderDb)));
			}
			if (!osaDTO.getAllowEdit()) {
				log.error("Order cannot be Edited");
				throw new GeneralException("Order cannot be Edited", Arrays.asList(convertToDTO(orderDb)));
			}
			log.info("Updating Order Items' Quantities");
			orderDb.getItems().stream().forEach(
					item -> itemService.updateItemAvailableQuantity(item.getItem().getId(), -item.getQuantity()));
			items.stream().forEach(
					item -> itemService.updateItemAvailableQuantity(item.getItem().getId(), item.getQuantity()));
			log.info("Replacing old Order Items with new Order Items", id);
			orderDb.setItems(items);
			orderDb = orderRepo.save(orderDb);
			return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(orderDb)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<OrderDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<OrderDTO> cancelOrder(Long id, String username) {
		try {
			UserModel user = userRepo.findByUsername(username).get();
			log.info("Fetching Order with id {} from database", id);
			OrderModel orderDb = orderRepo.findById(id)
					.orElseThrow(() -> new GeneralException("Order not found", null));
			checkIfOrderBelongsToClient(user, orderDb, username);
			IOrderStatusActionDTO osaDTO = orderStatusActionRepo
					.findOrderStatusActionByOrderStatusName(orderDb.getOrderStatus().getName().toLowerCase())
					.orElseThrow(() -> new GeneralException("Cannot find the Order Status Action row", null));
			if (orderDb.getOrderStatus() == null) {
				log.error("Order does not have a Status");
				throw new GeneralException("Order does not have a Status", Arrays.asList(orderDb));
			}
			if (!osaDTO.getAllowCancel()) {
				log.error("Order cannot be Cancelled");
				throw new GeneralException("Order cannot be Cancelled", Arrays.asList(orderDb));
			}
			Optional<OrderStatusModel> osDTO = orderStatusRepo.findByName(OrderStatusesEnum.CANCELED.getName());
			log.info("Setting {} status to Order", osDTO.get().getName());
			orderDb.setOrderStatus(osDTO.get());
			log.info("Updating Order Items' Quantities");
			orderDb.getItems().stream().forEach(
					item -> itemService.updateItemAvailableQuantity(item.getItem().getId(), -item.getQuantity()));
			orderDb = orderRepo.save(orderDb);
			return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(orderDb)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<OrderDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<OrderDTO> submitOrder(Long id, String username) {
		try {
			UserModel user = userRepo.findByUsername(username).get();
			log.info("Fetching Order with id {} from database", id);
			OrderModel orderDb = orderRepo.findById(id)
					.orElseThrow(() -> new GeneralException("Order not found", null));
			checkIfOrderBelongsToClient(user, orderDb, username);
			IOrderStatusActionDTO osaDTO = orderStatusActionRepo
					.findOrderStatusActionByOrderStatusName(orderDb.getOrderStatus().getName().toLowerCase())
					.orElseThrow(() -> new GeneralException("Cannot find the Order Status Action row", null));
			if (orderDb.getOrderStatus() == null) {
				log.error("Order does not have a Status");
				throw new GeneralException("Order does not have a Status", Arrays.asList(orderDb));
			}
			if (!osaDTO.getAllowSubmit()) {
				log.error("Order cannot be Submitted");
				throw new GeneralException("Order cannot be Submitted", Arrays.asList(orderDb));
			}
			Optional<OrderStatusModel> osDTO = orderStatusRepo
					.findByName(OrderStatusesEnum.AWAITING_APPROVAL.getName());
			log.info("Setting {} status to Order", osDTO.get().getName());
			orderDb.setOrderStatus(osDTO.get());
			orderDb.setSubmittedDate(new Date());
			orderDb = orderRepo.save(orderDb);
			return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(orderDb)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<OrderDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<OrderDTO> approveOrder(Long id, Boolean isApproved) {
		try {
			log.info("Fetching Order with id {} from database", id);
			OrderModel orderDb = orderRepo.findById(id)
					.orElseThrow(() -> new GeneralException("Order not found", null));
			IOrderStatusActionDTO osaDTO = orderStatusActionRepo
					.findOrderStatusActionByOrderStatusName(orderDb.getOrderStatus().getName().toLowerCase())
					.orElseThrow(() -> new GeneralException("Cannot find the Order Status Action row", null));
			if (orderDb.getOrderStatus() == null) {
				log.error("Order does not have a Status");
				throw new GeneralException("Order does not have a Status", Arrays.asList(orderDb));
			}
			if (!osaDTO.getAllowApprove()) {
				log.error("Order cannot be Approved or Declined");
				throw new GeneralException("Order cannot be Approved or Declined", Arrays.asList(orderDb));
			}
			Optional<OrderStatusModel> osDTO;
			if (isApproved) {
				osDTO = orderStatusRepo.findByName(OrderStatusesEnum.APPROVED.getName());
			} else {
				osDTO = orderStatusRepo.findByName(OrderStatusesEnum.DECLINED.getName());
			}
			log.info("Setting {} status to Order", osDTO.get().getName());
			orderDb.setOrderStatus(osDTO.get());
			orderDb = orderRepo.save(orderDb);
			return new ResponseWrapper<OrderDTO>(true, Arrays.asList(convertToDTO(orderDb)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<OrderDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public OrderModel deliverOrder(Long id) {
		log.info("Fetching Order with id {} from database", id);
		OrderModel orderDb = orderRepo.findById(id).orElseThrow(() -> new GeneralException("Order not found", null));
		IOrderStatusActionDTO osaDTO = orderStatusActionRepo
				.findOrderStatusActionByOrderStatusName(orderDb.getOrderStatus().getName().toLowerCase())
				.orElseThrow(() -> new GeneralException("Cannot find the Order Status Action row", null));
		if (orderDb.getOrderStatus() == null) {
			log.error("Order does not have a Status");
			throw new GeneralException("Order does not have a Status", Arrays.asList(orderDb));
		}
		if (!osaDTO.getAllowDeliver()) {
			log.error("Order cannot be Delivered");
			throw new GeneralException("Order cannot be Delivered", Arrays.asList(orderDb));
		}
		Optional<OrderStatusModel> osDTO = orderStatusRepo.findByName(OrderStatusesEnum.UNDER_DELIVERY.getName());
		log.info("Setting {} status to Order", osDTO.get().getName());
		orderDb.setOrderStatus(osDTO.get());
		log.info("Updating Order Items' Quantities");
		orderDb.getItems().stream().forEach(
				item -> itemService.updateItemTotalQuantity(item.getItem().getId(), item.getQuantity()));
		orderDb = orderRepo.save(orderDb);
		return orderDb;
	}

	@Override
	public OrderModel fulfillOrder(Long id) {
		log.info("Fetching Order with id {} from database", id);
		OrderModel orderDb = orderRepo.findById(id).orElseThrow(() -> new GeneralException("Order not found", null));
		IOrderStatusActionDTO osaDTO = orderStatusActionRepo
				.findOrderStatusActionByOrderStatusName(orderDb.getOrderStatus().getName().toLowerCase())
				.orElseThrow(() -> new GeneralException("Cannot find the Order Status Action row", null));
		if (orderDb.getOrderStatus() == null) {
			log.error("Order does not have a Status");
			throw new GeneralException("Order does not have a Status", Arrays.asList(orderDb));
		}
		if (!osaDTO.getAllowFulfill()) {
			log.error("Order cannot be Fulfilled");
			throw new GeneralException("Order cannot be Fulfilled", Arrays.asList(orderDb));
		}
		Optional<OrderStatusModel> osDTO = orderStatusRepo.findByName(OrderStatusesEnum.FULFILLED.getName());
		log.info("Setting {} status to Order", osDTO.get().getName());
		orderDb.setOrderStatus(osDTO.get());
		orderDb = orderRepo.save(orderDb);
		return orderDb;
	}

}
