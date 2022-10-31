package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.TruckModel;

public interface TruckRepo extends JpaRepository<TruckModel, Long> {
	
	Optional<TruckModel> findByChassisNumberOrLicensePlate(String chassisNumber, String licensePlate);

}
