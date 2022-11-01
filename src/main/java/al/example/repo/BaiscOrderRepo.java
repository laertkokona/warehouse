package al.example.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.BasicOrderModel;

@Repository
public interface BaiscOrderRepo extends JpaRepository<BasicOrderModel, Long> {
	
	Page<BasicOrderModel> findByOrderStatus_Name(String name, Pageable pageable);

}
