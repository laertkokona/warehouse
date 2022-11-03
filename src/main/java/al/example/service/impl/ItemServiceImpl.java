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
				? new ResponseWrapper<ItemDTO>(true, convertToDTO(itemOpt.get()), "SUCCESS")
				: new ResponseWrapper<ItemDTO>(false, null, "Delivery with id " + id + " not found");
	}

	@Override
	public ResponseWrapper<List<ItemDTO>> getAllItems(Pagination pagination) {
		if(pagination == null) pagination = new Pagination();
		log.info("Fetching all Items with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());
		List<ItemModel> itemsModel = itemRepo.findAll(pageable).getContent();
		List<ItemDTO> itemsDTO = itemsModel.stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseWrapper<List<ItemDTO>>(true, itemsDTO, "Success");
	}

	@Override
	public ResponseWrapper<ItemDTO> saveItem(ItemModel item) {
		try {
			log.info("Generating Code for new Item");
			item.setCode("ITM_" + itemRepo.getCodeSequence().toString());
			log.info("Saving new Item with code {} to database", item.getCode());
			item.setTotalQuantity(item.getAvailableQuantity());
			item = itemRepo.save(item);
			return new ResponseWrapper<ItemDTO>(true, convertToDTO(item), "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<ItemDTO>(false, null, e.getMessage());
		}
	}

	@Override
	public ResponseWrapper<ItemDTO> updateItem(Long id, ItemModel item) {
		try {
			log.info("Fetching Item with id {} from database", id);
			Optional<ItemModel> itemOpt = itemRepo.findById(id);
			checkIfExists(itemOpt);
			log.info("Updating Item with id {}", item.getId());
			ItemModel updatedItem = itemOpt.get();
			if(item.getName() != null) updatedItem.setName(item.getName());
			if(item.getCode() != null) updatedItem.setCode(item.getCode());
//			if(item.getTotalQuantity() != null) updatedItem.setTotalQuantity(item.getTotalQuantity());
//			if(item.getAvailableQuantity() != null) updatedItem.setAvailableQuantity(item.getAvailableQuantity());
			updatedItem = itemRepo.save(updatedItem);
			return new ResponseWrapper<ItemDTO>(true, convertToDTO(updatedItem), "Success");
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

	@Override
	public ItemModel updateItemAvailableQuantity(Long id, Integer quantity) {
		log.info("Fetching Item with id {} from database", id);
		Optional<ItemModel> itemOpt = itemRepo.findById(id);
		checkIfExists(itemOpt);
		ItemModel item = itemOpt.get();
		if (item.getAvailableQuantity() < quantity)
			throw new GeneralException("Available quantity less then Requested quantity",
					Arrays.asList(convertToDTO(item)));
		log.info("Updating Available Quantity to Item with id {}", id);
		item.setAvailableQuantity(item.getAvailableQuantity() - quantity);
		item = itemRepo.save(item);
		return item;
	}

	@Override
	public ItemModel updateItemTotalQuantity(Long id, Integer quantity) {
		try {
			log.info("Fetching Item with id {} from database", id);
			Optional<ItemModel> itemOpt = itemRepo.findById(id);
			checkIfExists(itemOpt);
			ItemModel item = itemOpt.get();
			log.info("Updating Total Quantity to Item with id {}", id);
			item.setTotalQuantity(item.getTotalQuantity() - quantity);
			item = itemRepo.save(item);
			return item;
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResponseWrapper<ItemDTO> addQuantityToItem(Long id, Integer quantity) {
		log.info("Fetching Item with id {} from database", id);
		Optional<ItemModel> itemOpt = itemRepo.findById(id);
		checkIfExists(itemOpt);
		ItemModel item = itemOpt.get();
		log.info("Updating Quantity to Item with id {}", id);
		item.setTotalQuantity(item.getTotalQuantity() + quantity);
		item.setAvailableQuantity(item.getAvailableQuantity() + quantity);
		item = itemRepo.save(item);
		return new ResponseWrapper<ItemDTO>(true, convertToDTO(item), "Success");
	}

	@Override
	public ResponseWrapper<ItemDTO> removeQuantityToItem(Long id, Integer quantity) {
		log.info("Fetching Item with id {} from database", id);
		Optional<ItemModel> itemOpt = itemRepo.findById(id);
		checkIfExists(itemOpt);
		ItemModel item = itemOpt.get();
		if(item.getTotalQuantity() < quantity) throw new GeneralException("Total Quantity less than Quantity to be removed", null);
		if(item.getAvailableQuantity() < quantity) throw new GeneralException("Available Quantity less than Quantity to be removed", null);
		log.info("Updating Quantity to Item with id {}", id);
		item.setTotalQuantity(item.getTotalQuantity() + quantity);
		item.setAvailableQuantity(item.getAvailableQuantity() + quantity);
		item = itemRepo.save(item);
		return new ResponseWrapper<ItemDTO>(true, convertToDTO(item), "Success");
	}

}
