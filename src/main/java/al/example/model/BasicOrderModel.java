package al.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "order")
@Data @NoArgsConstructor @AllArgsConstructor
public class BasicOrderModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_id_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	private Date submittedDate;
	private Date deadlineDate;
	private String code;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_status_id")
	private OrderStatusModel orderStatus;
	private String username;

}
