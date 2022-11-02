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
import org.springframework.stereotype.Service;

import al.example.exception.GeneralException;
import al.example.model.DeliveryModel;
import al.example.model.OrderItemModel;
import al.example.model.OrderModel;
import al.example.model.TruckModel;
import al.example.model.dto.DeliveryDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.repo.DeliveryRepo;
import al.example.service.DeliveryService;
import al.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class DeliveryServiceImpl implements DeliveryService {

	private final DeliveryRepo deliveryRepo;
	private final OrderService orderService;
	private final ModelMapper modelMapper;

	private DeliveryDTO convertToDTO(DeliveryModel delivery) {
		log.info("Converting Delivery Model to DTO");
		return modelMapper.map(delivery, DeliveryDTO.class);
	}
	
	private List<TruckModel> checkIfTrucksAvailable(List<TruckModel> trucksToCheck) {
		List<TruckModel> duplicateTrucks = new ArrayList<>();
		
		Date date = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDay());
		List<DeliveryModel> deliveryCheckList = deliveryRepo.findByDate(date);
		for(DeliveryModel delivery : deliveryCheckList) {
			for (TruckModel truck : delivery.getTrucks()) {
				for(TruckModel truckToCheck : trucksToCheck) {
					if(truckToCheck.getLicensePlate().equals(truck.getLicensePlate())) duplicateTrucks.add(truckToCheck);
				}
			}
		}
		return duplicateTrucks;
	}

	@Override
	public ResponseWrapper<DeliveryDTO> getDeliveryById(Long id) {
		log.info("Fetching Delivery with id {} from database", id);
		Optional<DeliveryModel> deliveryOpt = deliveryRepo.findById(id);
		return deliveryOpt.isPresent()
				? new ResponseWrapper<DeliveryDTO>(true, Arrays.asList(convertToDTO(deliveryOpt.get())), "SUCCESS")
				: new ResponseWrapper<DeliveryDTO>(false, null, "Delivery with id " + id + " not found");
	}

	@Override
	public ResponseWrapper<DeliveryDTO> createDelivery(DeliveryModel delivery) {
		try {
			log.info("Saving new Delivery to database");
			List<TruckModel> duplicateTrucks = checkIfTrucksAvailable(delivery.getTrucks());
			if(duplicateTrucks.size() > 0) throw new GeneralException("One or more chosen Trucks not available", Arrays.asList(duplicateTrucks));
			int orderItemsQuantity = 0;
			for (OrderModel order : delivery.getOrders()) {
				for (OrderItemModel item : order.getItems()) {
					orderItemsQuantity += item.getQuantity();
				}
			}
			if(orderItemsQuantity > delivery.getTrucks().size() * 10) {
				log.error("Not enough truck for this many items: Items = {}, , Trucks = {}", delivery.getTrucks().size(), orderItemsQuantity);
				throw new GeneralException("Not enough truck for this many items: Items = " + delivery.getTrucks().size() + ", Trucks = " + orderItemsQuantity, null);
			}
			Date date = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDay());
			delivery.setDate(date);
			delivery = deliveryRepo.save(delivery);
			delivery.getOrders().stream().forEach(order -> orderService.deliverOrder(order.getId()));
			return new ResponseWrapper<DeliveryDTO>(true, Arrays.asList(convertToDTO(delivery)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<DeliveryDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<DeliveryDTO> getAllDeliveries(Pagination pagination) {
		if(pagination == null) pagination = new Pagination();
		log.info("Fetching all Deliverise with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());

		List<DeliveryModel> deliveriesModel = deliveryRepo.findAll(pageable).getContent();
		List<DeliveryDTO> deliveriesDTO = deliveriesModel.stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseWrapper<DeliveryDTO>(true, deliveriesDTO, "Success");
	}

	@Override
	public ResponseWrapper<DeliveryDTO> updateDelivery(Long id, DeliveryModel delivery) {
		try {
			log.info("Fetching Delivery with id {} from database", id);
			DeliveryModel deliveryDb = deliveryRepo.findById(id)
					.orElseThrow(() -> new GeneralException("Delivery not found", null));
			log.info("Updating Delivery Objects");
			deliveryDb.setOrders(delivery.getOrders());
			deliveryDb.setTrucks(delivery.getTrucks());
			deliveryDb = deliveryRepo.save(deliveryDb);
			return new ResponseWrapper<DeliveryDTO>(true, Arrays.asList(convertToDTO(deliveryDb)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<DeliveryDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<DeliveryDTO> deleteDelivery(Long id) {
		try {
			log.info("Fetching Delivery with id {} from database", id);
			DeliveryModel order = deliveryRepo.findById(id).orElseThrow(() -> new GeneralException("Delivery not found", null));
			log.info("Deleting Delivery with id {} from database", id);
			deliveryRepo.deleteById(order.getId());
			return new ResponseWrapper<DeliveryDTO>(true, null, "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<DeliveryDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<DeliveryDTO> deliveryFulfilled(Long id) {
		try {
			log.info("Fetching Delivery with id {} from database", id);
			DeliveryModel deliveryDb = deliveryRepo.findById(id)
					.orElseThrow(() -> new GeneralException("Delivery not found", null));
			log.info("Updating Delivery Objects");
			deliveryDb.getOrders().stream().forEach(order -> orderService.fulfillOrder(order.getId()));
			deliveryDb.setDelivered(true);
			deliveryDb = deliveryRepo.save(deliveryDb);
			return new ResponseWrapper<DeliveryDTO>(true, Arrays.asList(convertToDTO(deliveryDb)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<DeliveryDTO>(false, null, e.getMessage());
		}
	}

}
