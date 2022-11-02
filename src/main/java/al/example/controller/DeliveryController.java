package al.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.DeliveryModel;
import al.example.model.dto.DeliveryDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.service.DeliveryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
	
	private final DeliveryService deliveryService;
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseWrapper<DeliveryDTO>> getById(@PathVariable("id") Long id) {
		ResponseWrapper<DeliveryDTO> res = deliveryService.getDeliveryById(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseWrapper<DeliveryDTO>> getAll(@RequestBody(required = false) Pagination pagination) {
		ResponseWrapper<DeliveryDTO> res = deliveryService.getAllDeliveries(pagination);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper<DeliveryDTO>> save(@RequestBody DeliveryModel deliveryModel) {
		ResponseWrapper<DeliveryDTO> res = deliveryService.createDelivery(deliveryModel);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<ResponseWrapper<DeliveryDTO>> save(@PathVariable("id") Long id, @RequestBody DeliveryModel deliveryModel) {
		ResponseWrapper<DeliveryDTO> res = deliveryService.updateDelivery(id, deliveryModel);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}
	
	@PostMapping("/fulfill/{id}")
	public ResponseEntity<ResponseWrapper<DeliveryDTO>> fulfill(@PathVariable("id") Long id) {
		ResponseWrapper<DeliveryDTO> res = deliveryService.deliveryFulfilled(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(405).body(res);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<DeliveryDTO>> delete(@PathVariable("id") Long id){
		ResponseWrapper<DeliveryDTO> res = deliveryService.deleteDelivery(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
}
