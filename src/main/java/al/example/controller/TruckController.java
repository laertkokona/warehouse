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
import al.example.service.TruckService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class TruckController {
	
	private final TruckService truckService;
	
	@PostMapping("/save")
	public ResponseEntity<TruckDTO> saveUser(@RequestBody TruckModel truck){
		return ResponseEntity.ok(truckService.saveOrUpdateTruck(truck));
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<TruckDTO> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(truckService.getTruckById(id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TruckDTO> deleteById(@PathVariable("id") Long id){
		truckService.deleteTruckById(id);
		return ResponseEntity.ok(null);
	}
	
}
