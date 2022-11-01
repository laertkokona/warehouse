package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.DeliveryModel;
import java.util.Date;
import java.util.List;

@Repository
public interface DeliveryRepo extends JpaRepository<DeliveryModel, Long> {
	
	List<DeliveryModel> findByDate(Date date);

}
