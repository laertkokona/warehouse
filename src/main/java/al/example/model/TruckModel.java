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
@Table(name = "truck")
@Data @NoArgsConstructor @AllArgsConstructor
public class TruckModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "truck_seq")
    @SequenceGenerator(name = "truck_seq", sequenceName = "truck_id_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	@Column(unique = true)
	private String chassisNumber;
	@Column(unique = true)
	private String licensePlate;

}
