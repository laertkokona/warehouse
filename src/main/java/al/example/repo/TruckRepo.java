package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.TruckModel;

@Repository
public interface TruckRepo extends JpaRepository<TruckModel, Long> {
	
	Optional<TruckModel> findByChassisNumberOrLicensePlate(String chassisNumber, String licensePlate);

}
