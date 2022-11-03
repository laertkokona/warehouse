package al.example.service;

import java.util.List;

import al.example.model.ItemModel;
import al.example.model.dto.ItemDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface ItemService {
	
	ResponseWrapper<ItemDTO> getItemById(Long id);
	ResponseWrapper<List<ItemDTO>> getAllItems(Pagination pagination);
	ResponseWrapper<ItemDTO> saveItem(ItemModel item);
	ResponseWrapper<ItemDTO> updateItem(Long id, ItemModel item);
	ResponseWrapper<ItemDTO> deleteItem(Long id);
	ResponseWrapper<ItemDTO> addQuantityToItem(Long id, Integer quantity);
	ResponseWrapper<ItemDTO> removeQuantityToItem(Long id, Integer quantity);
	ItemModel updateItemAvailableQuantity(Long id, Integer quantity);
	ItemModel updateItemTotalQuantity(Long id, Integer quantity);

}
