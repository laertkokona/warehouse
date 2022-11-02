package al.example.service;

import al.example.model.ItemModel;
import al.example.model.dto.ItemDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface ItemService {
	
	ResponseWrapper<ItemDTO> getItemById(Long id);
	ResponseWrapper<ItemDTO> getAllItems(Pagination pagination);
	ResponseWrapper<ItemDTO> saveItem(ItemModel item);
	ResponseWrapper<ItemDTO> updateItem(ItemModel item);
	ResponseWrapper<ItemDTO> deleteItem(Long id);
	ItemModel updateItemAvailableQuantity(Long id, Integer quantity);
	ItemModel updateItemTotalQuantity(Long id, Integer quantity);

}
