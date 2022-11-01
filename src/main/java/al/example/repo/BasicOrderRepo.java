package al.example.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.BasicOrderModel;
import al.example.model.dto.BasicOrderDTO;

public interface BasicOrderRepo extends JpaRepository<BasicOrderModel, Long> {
	
	Page<BasicOrderDTO> findByOrderStatus_Name(String name, Pageable pageable);

}
