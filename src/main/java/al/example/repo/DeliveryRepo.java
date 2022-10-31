package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.DeliveryModel;
import java.util.Date;
import java.util.List;

public interface DeliveryRepo extends JpaRepository<DeliveryModel, Long> {
	
	List<DeliveryModel> findByDate(Date date);

}
