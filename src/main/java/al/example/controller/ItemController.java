package al.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.ItemModel;
import al.example.model.dto.ItemDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.service.ItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseWrapper<ItemDTO>> getById(@PathVariable("id") Long id) {
		ResponseWrapper<ItemDTO> res = itemService.getItemById(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseWrapper<ItemDTO>> getAll(@RequestBody(required = false) Pagination pagination) {
		ResponseWrapper<ItemDTO> res = itemService.getAllItems(pagination);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper<ItemDTO>> save(@RequestBody ItemModel itemModel) {
		ResponseWrapper<ItemDTO> res = itemService.saveItem(itemModel);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

	@PostMapping("/update")
	public ResponseEntity<ResponseWrapper<ItemDTO>> update(@RequestBody ItemModel itemModel) {
		ResponseWrapper<ItemDTO> res = itemService.updateItem(itemModel);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<ItemDTO>> delete(@PathVariable("id") Long id) {
		ResponseWrapper<ItemDTO> res = itemService.deleteItem(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

}
