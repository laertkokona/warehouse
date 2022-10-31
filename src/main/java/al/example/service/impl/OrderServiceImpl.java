package al.example.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import al.example.repo.OrderRepo;
import al.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepo orderRepo;
	private final ModelMapper modelMapper;
	
}
