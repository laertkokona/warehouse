package al.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workflow")
@Data @NoArgsConstructor @AllArgsConstructor
public class WorkflowModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workflow_seq")
	@SequenceGenerator(name = "workflow_seq", sequenceName = "workflow_id_seq", allocationSize = 1)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "status_from_id")
	private OrderStatusModel statusFrom;
	@ManyToOne
	@JoinColumn(name = "status_to_id")
	private OrderStatusModel statusTo;
	private Boolean allowEdit;
	private Boolean allowCancel;
	private Boolean allowSubmit;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private RoleModel role;

}
