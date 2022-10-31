package al.example.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import al.example.repo.DeliveryRepo;
import al.example.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class DeliveryServiceImpl implements DeliveryService {
	
	private final DeliveryRepo deliveryRepo;
	private final ModelMapper modelMapper;

}
