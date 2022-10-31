package al.example.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devlivery")
@Data @NoArgsConstructor @AllArgsConstructor
public class DeliveryModel {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_seq")
    @SequenceGenerator(name = "delivery_seq", sequenceName = "delivery_id_seq", allocationSize = 1)
    private Long id;
    private Date date;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "delivery_trucks",
            joinColumns = @JoinColumn(name = "delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id")
    )
    private List<TruckModel> trucks;
    @OneToMany
    private List<OrderModel> orders;

}
