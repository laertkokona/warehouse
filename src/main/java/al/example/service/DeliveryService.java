package al.example.service;

import al.example.model.DeliveryModel;
import al.example.model.dto.DeliveryDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface DeliveryService {
	
	ResponseWrapper<DeliveryDTO> getDeliveryById(Long id);
	ResponseWrapper<DeliveryDTO> getAllDeliveries(Pagination pagination);
	ResponseWrapper<DeliveryDTO> createDelivery(DeliveryModel delivery);
	ResponseWrapper<DeliveryDTO> updateDelivery(DeliveryModel delivery);
	ResponseWrapper<DeliveryDTO> deleteDelivery(Long id);

}
