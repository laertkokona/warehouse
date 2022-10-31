package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.OrderStatusModel;

public interface OrderStatusRepo extends JpaRepository<OrderStatusModel, Long> {

}
