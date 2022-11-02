package al.example.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import al.example.exception.GeneralException;
import al.example.model.TruckModel;
import al.example.model.dto.TruckDTO;
import al.example.model.pojo.Pagination;
import al.example.model.pojo.ResponseWrapper;
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
	public ResponseWrapper<TruckDTO> getTruckById(Long id) {
		log.info("Fetching Truck with id {} from database", id);
		Optional<TruckModel> truckOpt = truckRepo.findById(id);
		checkIfExists(truckOpt);
		return new ResponseWrapper<TruckDTO>(true, Arrays.asList(convertToDTO(truckOpt.get())), "Success");
	}

	@Override
	public ResponseWrapper<TruckDTO> saveTruck(TruckModel truck) {
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
		return new ResponseWrapper<TruckDTO>(true, Arrays.asList(convertToDTO(truck)), "Success");
	}

	@Override
	public ResponseWrapper<TruckDTO> updateTruck(Long id, TruckModel truck) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseWrapper<TruckDTO> getAllTrucks(Pagination pagination) {
		if(pagination == null) pagination = new Pagination();
		log.info("Fetching all Trucks with {}", pagination.toString());
		Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(),
				pagination.getSortByAsc() ? Sort.by(pagination.getSortByProperty()).ascending()
						: Sort.by(pagination.getSortByProperty()).descending());

		List<TruckModel> trucksModel = truckRepo.findAll(pageable).getContent();
		List<TruckDTO> trucksDTO = trucksModel.stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseWrapper<TruckDTO>(true, trucksDTO, "Success");
	}

	@Override
	public ResponseWrapper<TruckDTO> deleteTruckById(Long id) {
		try {
			log.info("Fetching Truck with id {} from database", id);
			Optional<TruckModel> truckOpt = truckRepo.findById(id);
			checkIfExists(truckOpt);
			log.info("Deleting Truck with id {} from database", id);
			truckRepo.deleteById(id);
			return new ResponseWrapper<TruckDTO>(true, null, "Success");
		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
			return new ResponseWrapper<TruckDTO>(false, null, e.getMessage());
		}	
	}

	private TruckDTO convertToDTO(TruckModel truck) {
		log.info("Converting Truck Model to DTO");
		return modelMapper.map(truck, TruckDTO.class);
	}

	private void checkIfExists(Optional<TruckModel> truckOpt) {
		if (truckOpt.isEmpty()) {
			log.error("Truck not found");
			throw new GeneralException("Truck not found", null);
		}
	}

}
