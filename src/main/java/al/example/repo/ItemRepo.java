package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import al.example.model.ItemModel;

@Repository
public interface ItemRepo extends JpaRepository<ItemModel, Long> {
	
	@Query(value = "select nextval('warehouse.item_code_seq')", nativeQuery = true)
	Long getCodeSequence();

}
