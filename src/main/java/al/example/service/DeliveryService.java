package al.example.service;

import al.example.model.dto.DeliveryDTO;
import al.example.model.pojo.ResponseWrapper;

public interface DeliveryService {
	
	ResponseWrapper<DeliveryDTO> getDeliveryById(Long id);

}
