package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.OrderStatusModel;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatusModel, Long> {
	
	Optional<OrderStatusModel> findByInitialStatus(Boolean initialStatus);
	Optional<OrderStatusModel> findByName(String name);

}
