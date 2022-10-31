package al.example.service;

import al.example.model.TruckModel;
import al.example.model.dto.TruckDTO;

public interface TruckService {
	
	TruckDTO getTruckById(Long id);
	TruckDTO saveOrUpdateTruck(TruckModel truck);
	void deleteTruckById(Long id);

}
