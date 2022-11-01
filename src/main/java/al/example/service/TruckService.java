package al.example.service;

import al.example.model.TruckModel;
import al.example.model.dto.TruckDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;

public interface TruckService {
	
	ResponseWrapper<TruckDTO> getTruckById(Long id);
	ResponseWrapper<TruckDTO> saveTruck(TruckModel truck);
	ResponseWrapper<TruckDTO> getAllTrucks(Pagination pagination);
	ResponseWrapper<TruckDTO> deleteTruckById(Long id);

}
