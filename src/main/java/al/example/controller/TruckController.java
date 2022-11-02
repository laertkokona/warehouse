package al.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import al.example.model.TruckModel;
import al.example.model.dto.TruckDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
import al.example.service.TruckService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class TruckController {
	
	private final TruckService truckService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseWrapper<TruckDTO>> save(@RequestBody TruckModel truck){
		ResponseWrapper<TruckDTO> res = truckService.saveTruck(truck);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<ResponseWrapper<TruckDTO>> update(@PathVariable("id") Long id, @RequestBody TruckModel truck){
		ResponseWrapper<TruckDTO> res = truckService.updateTruck(id, truck);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseWrapper<TruckDTO>> getById(@PathVariable("id") Long id){
		ResponseWrapper<TruckDTO> res = truckService.getTruckById(id);
				return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseWrapper<TruckDTO>> deleteById(@PathVariable("id") Long id){
		ResponseWrapper<TruckDTO> res = truckService.deleteTruckById(id);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseWrapper<TruckDTO>> getAll(@RequestBody(required = false) Pagination pagination) {
		ResponseWrapper<TruckDTO> res = truckService.getAllTrucks(pagination);
		return res.getStatus() ? ResponseEntity.ok(res) : ResponseEntity.status(410).body(res);
	}
	
}
