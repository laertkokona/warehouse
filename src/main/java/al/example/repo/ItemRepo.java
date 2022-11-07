package al.example.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import al.example.model.ItemModel;

@Repository
public interface ItemRepo extends JpaRepository<ItemModel, Long> {
	
	@Query(value = "select nextval('warehouse.item_code_seq')", nativeQuery = true)
	Long getCodeSequence();
	
	Optional<ItemModel> findByIdAndActive(Long id, Boolean active);
	Page<ItemModel> findByActive(Boolean active, Pageable pageable);

}
