package al.example.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.OrderModel;
import al.example.model.dto.BasicOrderDTO;
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
	public ResponseEntity<ResponseWrapper<OrderDTO>> getById(@PathVariable("id") Long id, Principal principal) {
		ResponseWrapper<OrderDTO> res = orderService.getOrderById(id, principal.getName());
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@GetMapping(value = "/getAllClient")
	public ResponseEntity<ResponseWrapper<List<BasicOrderDTO>>> getAllForClient(
			@RequestBody(required = false) Pagination pagination, @RequestParam(required = false) String status,
			@RequestHeader("Authorization") String authHeader) {
		ResponseWrapper<List<BasicOrderDTO>> res = orderService.getAllOrdersByUsernameAndStatusFilter(pagination,
				authHeader, status);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

//	@GetMapping(value = "/getAll")
//	public ResponseEntity<ResponseWrapper<BasicOrderDTO>> getAll(@RequestBody(required = false) Pagination pagination,
//			@PathVariable(required = false) String username,
//			@RequestParam(required = false) String status) {
//		ResponseWrapper<BasicOrderDTO> res = orderService.getAllOrdersByUsernameAndStatusFilter(pagination, authHeader,
//				status);
//		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
//	}

	@PostMapping("/create")
	public ResponseEntity<ResponseWrapper<OrderDTO>> create(@RequestBody OrderModel order, Principal principal) {
		ResponseWrapper<OrderDTO> res = orderService.createOrder(order, principal.getName());
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> delete(@PathVariable("id") Long id) {
		ResponseWrapper<OrderDTO> res = orderService.deleteOrder(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> edit(@PathVariable("id") Long id,
			@RequestBody OrderModel order) {
		ResponseWrapper<OrderDTO> res = orderService.editOrder(id, order);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@PostMapping("/cancel/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> cancel(@PathVariable("id") Long id, Principal principal) {
		ResponseWrapper<OrderDTO> res = orderService.cancelOrder(id, principal.getName());
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@PostMapping("/submit/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> submit(@PathVariable("id") Long id, Principal principal) {
		ResponseWrapper<OrderDTO> res = orderService.submitOrder(id, principal.getName());
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

	@PostMapping("/approve/{id}")
	public ResponseEntity<ResponseWrapper<OrderDTO>> approve(@PathVariable("id") Long id,
			@RequestParam("isApproved") Boolean isApproved) {
		ResponseWrapper<OrderDTO> res = orderService.approveOrder(id, isApproved);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}

}
