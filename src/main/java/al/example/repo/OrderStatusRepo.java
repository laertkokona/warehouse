package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.OrderStatusModel;

public interface OrderStatusRepo extends JpaRepository<OrderStatusModel, Long> {
	
	Optional<OrderStatusModel> findByInitialStatus(Boolean initialStatus);

}
