package ai.forjada.teamtracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.forjada.teamtracker.domain.Employee;
import ai.forjada.teamtracker.service.ApiMessage;
import ai.forjada.teamtracker.service.EmployeeService;
import ai.forjada.teamtracker.service.MapValidationErrorService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private MapValidationErrorService validationService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/all")
	public ResponseEntity<?> findAll () {
		return ResponseEntity.ok(employeeService.findAll());
	}
	
	@GetMapping("/find")
	public ResponseEntity<?> findByEmail (@RequestParam String email) {
		return ResponseEntity.ok(employeeService.findByEmailIterable(email));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(
			@Valid @RequestBody Employee employee,
			BindingResult result
		) {
		
		// check if employee already exist
		String email = employee.getEmail();
		if(employeeService.exist(email)) {
			String msg = "Employee with email (" + email + ") already exists. Use POST /update instead.";
			String err = "CREATE_FAILED";
			return ResponseEntity.ok(new ApiMessage(err,msg));
		}
		
		// validate employee input
		ResponseEntity<?> errorMap = validationService.MapValidationService(result);
		if (errorMap != null) return errorMap;
		
		employeeService.create(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(
			@Valid @RequestBody Employee employee,
			BindingResult result
		) {
		
		ResponseEntity<?> errorMap = validationService.MapValidationService(result);
		if (errorMap != null) return errorMap;
		
		boolean updated = employeeService.update(employee);
		if(!updated) {
			String msg = "Employee with email (" + employee.getEmail() + ") was not found.";
			String err = "UPDATE_FAILED";
			return new ResponseEntity<>(new ApiMessage(err, msg), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String email) {
		
		String msg = "Employee with email (" + email + ") was deleted";
		String err = "";
		
		// check if employee exist
		if(!employeeService.exist(email)) {
			msg = "Employee with email (" + email + ") not found.";
			err = "DELETE_FAILED";
			return ResponseEntity.ok(new ApiMessage(err, msg));
		}
		
		employeeService.delete(email);
		return ResponseEntity.ok(new ApiMessage(err, msg));
	}

	
}
