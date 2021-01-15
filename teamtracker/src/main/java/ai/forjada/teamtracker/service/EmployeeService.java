package ai.forjada.teamtracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ai.forjada.teamtracker.domain.Employee;
import ai.forjada.teamtracker.repository.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Employee findByEmail(String email) {
		return employeeRepo.findByEmail(email);
	}
	
	public Iterable<Employee> findByEmailIterable(String email) {
		List<Employee> list = new ArrayList<>();
		Employee employee = employeeRepo.findByEmail(email);
		if (employee != null) list.add(employee);
		return list;
	}
	
	public Iterable<Employee> findAll() {
		return employeeRepo.findAll();
	}
	
	public Employee create(Employee employee) {
		employee.setPassword(encode(employee.getPassword()));
		return employeeRepo.save(employee);
	}

	public boolean update(Employee employee) {
		Employee employeeInDB = findByEmail(employee.getEmail());
		if (employeeInDB == null) return false;
		employee.setId(employeeInDB.getId());
		employee.setPassword(encode(employee.getPassword()));
		employeeRepo.save(employee);
		return true;
	}

	public void delete(String email) {
		employeeRepo.delete(findByEmail(email));
	}
	
	private String encode(String input) {
		return bCryptPasswordEncoder.encode(input);
	}

	public boolean exist(String email) {
		return findByEmail(email) != null;
	}
	

}
