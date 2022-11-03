package al.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<ResponseWrapper<List<ItemDTO>>> getAll(@RequestBody(required = false) Pagination pagination) {
		ResponseWrapper<List<ItemDTO>> res = itemService.getAllItems(pagination);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper<ItemDTO>> save(@RequestBody ItemModel itemModel) {
		ResponseWrapper<ItemDTO> res = itemService.saveItem(itemModel);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<ResponseWrapper<ItemDTO>> update(@PathVariable("id") Long id, @RequestBody ItemModel itemModel) {
		ResponseWrapper<ItemDTO> res = itemService.updateItem(id, itemModel);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@PostMapping("/addQuantity/{id}")
	public ResponseEntity<ResponseWrapper<ItemDTO>> addQuantity(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity) {
		ResponseWrapper<ItemDTO> res = itemService.addQuantityToItem(id, quantity);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

	@PostMapping("/removeQuantity/{id}")
	public ResponseEntity<ResponseWrapper<ItemDTO>> removeQuantity(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity) {
		ResponseWrapper<ItemDTO> res = itemService.removeQuantityToItem(id, quantity);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<ItemDTO>> delete(@PathVariable("id") Long id) {
		ResponseWrapper<ItemDTO> res = itemService.deleteItem(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

}
