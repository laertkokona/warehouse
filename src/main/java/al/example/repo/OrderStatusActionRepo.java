package al.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import al.example.model.OrderStatusActionModel;
import al.example.model.dto.IOrderStatusActionDTO;

@Repository
public interface OrderStatusActionRepo extends JpaRepository<OrderStatusActionModel, Long> {

	@Query(value = "select s.id as id, o.name as orderStatusName, s.allowEdit as allowEdit, s.allowCancel as allowCancel, s.allowApprove as allowApprove, s.allowDeliver as allowDeliver, s.allowFulfill as allowFulfill, s.allowSubmit as allowSubmit from OrderStatusActionModel s join s.orderStatus as o where lower(s.orderStatus.name) = :statusName")
	Optional<IOrderStatusActionDTO> findOrderStatusActionByOrderStatusName(@Param("statusName") String statusName);

}
