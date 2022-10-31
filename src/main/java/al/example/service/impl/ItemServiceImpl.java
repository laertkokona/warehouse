package al.example.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import al.example.repo.ItemRepo;
import al.example.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ItemServiceImpl implements ItemService {
	
	private final ItemRepo itemRepo;
	private final ModelMapper modelMapper;

}
