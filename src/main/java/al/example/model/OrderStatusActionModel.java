package al.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_status_action")
@Data @NoArgsConstructor @AllArgsConstructor
public class OrderStatusActionModel {
	
	@Id
	private Long id;
	@OneToOne
	@JoinColumn(name = "order_status_id", nullable = false, unique = true)
	private OrderStatusModel orderStatus;
	private Boolean allowEdit = false;
	private Boolean allowCancel = false;
	private Boolean allowDeliver = false;
	private Boolean allowSubmit = false;
	private Boolean allowApprove = false;
	private Boolean allowFulfill = false;
	
}
