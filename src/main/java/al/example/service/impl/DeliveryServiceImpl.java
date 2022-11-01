package al.example.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import al.example.model.DeliveryModel;
import al.example.model.dto.DeliveryDTO;
import al.example.model.pojo.ResponseWrapper;
import al.example.repo.DeliveryRepo;
import al.example.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

	private final DeliveryRepo deliveryRepo;
	private final ModelMapper modelMapper;

	private DeliveryDTO convertToDTO(DeliveryModel delivery) {
		log.info("Converting Delivery Model to DTO");
		return modelMapper.map(delivery, DeliveryDTO.class);
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
		// TODO Auto-generated method stub
		return null;
	}

}
