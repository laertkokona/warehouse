package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import al.example.model.WorkflowModel;

@Repository
public interface WorkflowRepo extends JpaRepository<WorkflowModel, Long> {

}
