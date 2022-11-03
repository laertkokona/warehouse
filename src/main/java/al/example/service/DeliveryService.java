package al.example.service;

import java.util.List;

import al.example.model.DeliveryModel;
import al.example.model.dto.DeliveryDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface DeliveryService {
	
	ResponseWrapper<DeliveryDTO> getDeliveryById(Long id);
	ResponseWrapper<List<DeliveryDTO>> getAllDeliveries(Pagination pagination);
	ResponseWrapper<DeliveryDTO> createDelivery(DeliveryModel delivery);
	ResponseWrapper<DeliveryDTO> updateDelivery(Long id, DeliveryModel delivery);
	ResponseWrapper<DeliveryDTO> deleteDelivery(Long id);
	ResponseWrapper<DeliveryDTO> deliveryFulfilled(Long id);

}
