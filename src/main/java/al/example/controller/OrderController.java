package al.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.OrderModel;
import al.example.model.dto.OrderDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> getById(@PathVariable("id") Long id){
		ResponseWrapper<OrderDTO> res = orderService.getOrderById(id);
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseWrapper<OrderDTO>> getAll(@RequestBody(required = false) Pagination pagination){
		ResponseWrapper<OrderDTO> res = orderService.getAllOrders(pagination);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper<OrderDTO>> save(@RequestBody OrderModel order){
		ResponseWrapper<OrderDTO> res = orderService.saveOrder(order);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> delete(@PathVariable("id") Long id){
		ResponseWrapper<OrderDTO> res = orderService.deleteOrder(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> edit(@PathVariable("id") Long id, @RequestBody OrderModel order){
		ResponseWrapper<OrderDTO> res = orderService.editOrder(id, order);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@PostMapping("/cancel/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> cancel(@PathVariable("id") Long id){
		ResponseWrapper<OrderDTO> res = orderService.cancelOrder(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@PostMapping("/submit/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> submit(@PathVariable("id") Long id){
		ResponseWrapper<OrderDTO> res = orderService.submitOrder(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

}
