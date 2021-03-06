package ai.forjada.teamtracker.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {

	public ResponseEntity<?> MapValidationService(BindingResult result) {
		
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError fe: result.getFieldErrors()) {
				errors.put(fe.getField(), fe.getDefaultMessage());
			}
			ResponseEntity.badRequest();
			return new ResponseEntity<Map<String,String>>(
					errors, HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}

}
