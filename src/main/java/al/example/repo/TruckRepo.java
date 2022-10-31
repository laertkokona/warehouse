package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.TruckModel;

public interface TruckRepo extends JpaRepository<TruckModel, Long> {

}
