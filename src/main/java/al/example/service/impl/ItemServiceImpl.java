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
import al.example.model.ItemModel;
import al.example.model.dto.ItemDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.repo.ItemRepo;
import al.example.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

	private final ItemRepo itemRepo;
	private final ModelMapper modelMapper;

	private ItemDTO convertToDTO(ItemModel item) {
		log.info("Converting Item Model to DTO");
		return modelMapper.map(item, ItemDTO.class);
	}

	private void checkIfExists(Optional<ItemModel> truckOpt) {
		if (truckOpt.isEmpty()) {
			log.error("Item not found");
			throw new GeneralException("Item not found", null);
		}
	}

	@Override
	public ResponseWrapper<ItemDTO> getItemById(Long id) {
		log.info("Fetching Item with id {} from database", id);
		Optional<ItemModel> itemOpt = itemRepo.findById(id);
		return itemOpt.isPresent()
				? new ResponseWrapper<ItemDTO>(true, Arrays.asList(convertToDTO(itemOpt.get())), "SUCCESS")
				: new ResponseWrapper<ItemDTO>(false, null, "Delivery with id " + id + " not found");
	}

	@Override
	public ResponseWrapper<ItemDTO> getAllItems(Pagination pagination) {
		log.info("Fetching all Items with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());
		List<ItemModel> itemsModel = itemRepo.findAll(pageable).getContent();
		List<ItemDTO> itemsDTO = itemsModel.stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseWrapper<ItemDTO>(true, itemsDTO, "Success");
	}

	@Override
	public ResponseWrapper<ItemDTO> saveItem(ItemModel item) {
		try {
			log.info("Generating Code for new Item");
			item.setCode("ITM_" + itemRepo.getCodeSequence().toString());
			log.info("Saving new Item with code {} to database", item.getCode());
			item = itemRepo.save(item);
			return new ResponseWrapper<ItemDTO>(true, Arrays.asList(convertToDTO(item)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<ItemDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<ItemDTO> updateItem(ItemModel item) {
		try {
			log.info("Fetching Item with id {} from database", item.getId());
			Optional<ItemModel> itemOpt = itemRepo.findById(item.getId());
			checkIfExists(itemOpt);
			log.info("Updating Item with id {}", item.getId());
			item.setCode(itemOpt.get().getCode());
			ItemModel updatedItem = itemRepo.save(item);
			return new ResponseWrapper<ItemDTO>(true, Arrays.asList(convertToDTO(updatedItem)), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<ItemDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<ItemDTO> deleteItem(Long id) {
		try {
			log.info("Fetching Item with id {} from database", id);
			Optional<ItemModel> itemOpt = itemRepo.findById(id);
			checkIfExists(itemOpt);
			log.info("Deleting Item with id {} from database", id);
			itemRepo.deleteById(id);
			return new ResponseWrapper<ItemDTO>(true, null, "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<ItemDTO>(false, null, e.getMessage());
		}
	}

}
