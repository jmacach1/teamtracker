package ai.forjada.teamtracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ai.forjada.teamtracker.domain.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long> {
	
	Employee findByEmail(String email);
	
}
