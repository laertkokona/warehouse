package al.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_status")
@Data @NoArgsConstructor @AllArgsConstructor
public class OrderStatusModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_seq")
    @SequenceGenerator(name = "order_status_seq", sequenceName = "order_status_id_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	@Column(unique = true)
	private String name;
	private Boolean initialStatus;

}
