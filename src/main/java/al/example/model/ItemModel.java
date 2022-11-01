package al.example.model;

import java.math.BigDecimal;

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
@Table(name = "item")
@Data @NoArgsConstructor @AllArgsConstructor
public class ItemModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_id_seq", allocationSize = 1, initialValue = 1)
	private Long id;
	private String name;
	@Column(unique = true, nullable = false)
	private String code;
	private Integer quantity;
	private BigDecimal unitPrice;

}
