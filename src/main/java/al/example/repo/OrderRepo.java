package al.example.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import al.example.model.OrderModel;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel, Long> {
	
	Page<OrderModel> findByOrderStatus_Name(String name, Pageable pageable);
	Page<OrderModel> findByUsernameAndOrderStatus_Name(String username, String name, Pageable pageable);
	
	@Query(value = "select nextval('warehouse.order_code_seq')", nativeQuery = true)
	Long getCodeSequence();

}
