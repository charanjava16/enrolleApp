package com.enroll.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enroll.springboot.exception.ResourceNotFoundException;
import com.enroll.springboot.model.Dependents;
import com.enroll.springboot.model.Enrollee;
import com.enroll.springboot.repository.DependentsRepository;
import com.enroll.springboot.repository.EnrolleeRepository;

@RestController
@RequestMapping("/api/v1/")
public class DependentsController {
	
	@Autowired
	private DependentsRepository depRepo;
	
	@PutMapping("/dependents/{id}")
	public ResponseEntity<Dependents> updateEnrollee(@PathVariable(value = "id") Long dependentId,
			@RequestBody Dependents dependentDetails) throws ResourceNotFoundException {
		Dependents dependent = depRepo.findById(dependentId).orElseThrow(() -> new ResourceNotFoundException("Dependent not found for this id :: " + dependentId));
		dependent.setLastName(dependentDetails.getLastName());
		dependent.setFirstName(dependentDetails.getFirstName());
		dependent.setDob(dependentDetails.getDob());
		final Dependents updatedDependent = depRepo.save(dependent);
		return ResponseEntity.ok(updatedDependent);
	}

}
