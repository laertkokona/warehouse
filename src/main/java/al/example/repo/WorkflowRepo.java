package al.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import al.example.model.WorkflowModel;

public interface WorkflowRepo extends JpaRepository<WorkflowModel, Long> {

}
