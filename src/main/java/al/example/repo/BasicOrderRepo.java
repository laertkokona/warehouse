package al.example.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.BasicOrderModel;

public interface BasicOrderRepo extends JpaRepository<BasicOrderModel, Long> {
	
	Page<BasicOrderModel> findByOrderStatus_Name(String name, Pageable pageable);
	Page<BasicOrderModel> findByUsernameAndOrderStatus_Name(String username, String name, Pageable pageable);

}
