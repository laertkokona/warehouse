package al.example.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import al.example.exception.GeneralException;
import al.example.model.TruckModel;
import al.example.model.dto.TruckDTO;
import al.example.repo.TruckRepo;
import al.example.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TruckServiceImpl implements TruckService {

	private final TruckRepo truckRepo;
	private final ModelMapper modelMapper;

	@Override
	public TruckDTO getTruckById(Long id) {
		log.info("Fetching Truck with id {} from database", id);
		Optional<TruckModel> truckOpt = truckRepo.findById(id);
		checkIfExists(truckOpt);
		return convertToDTO(truckOpt.get());
	}

	@Override
	public TruckDTO saveOrUpdateTruck(TruckModel truck) {
		log.info("Checking for duplicate Truck");
		Optional<TruckModel> truckCheck = truckRepo.findByChassisNumberOrLicensePlate(truck.getChassisNumber(),
				truck.getLicensePlate());
		if (truckCheck.isPresent()) {
			log.error("Truck with Chassis Number {} or LicensePlate {} already exists", truck.getChassisNumber(),
					truck.getLicensePlate());
			throw new GeneralException(
					"Truck with same Chassis Number or same License Plate already exists in database",
					Arrays.asList(convertToDTO(truckCheck.get())));
		}
		log.info("Saving new Truck with plate {} to database", truck.getLicensePlate());
		truck = truckRepo.save(truck);
		return convertToDTO(truck);
	}

	@Override
	public void deleteTruckById(Long id) {
		log.info("Fetching Truck with id {} from database", id);
		Optional<TruckModel> truckOpt = truckRepo.findById(id);
		checkIfExists(truckOpt);
		log.info("Deleting Truck with id {} from database", id);
		truckRepo.deleteById(id);
	}
	
	private TruckDTO convertToDTO(TruckModel truck) {
		return modelMapper.map(truck, TruckDTO.class);
	}
	
	private void checkIfExists(Optional<TruckModel> truckOpt) {
		if(truckOpt.isEmpty()) {
			log.error("Truck not found");
			throw new GeneralException("Truck not found", null);
		}
	}

}
