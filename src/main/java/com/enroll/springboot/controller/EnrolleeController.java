package com.enroll.springboot.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class EnrolleeController {
	
	@Autowired
	private EnrolleeRepository enrolleeRepo;
	
	@Autowired
	private DependentsRepository dependentsRepo;
	
	@PostMapping("/enrollees")
	public Enrollee createEnrollee(@RequestBody Enrollee enrollee) {
		return enrolleeRepo.save(enrollee);
	}
	
	@PutMapping("/enrollees/{id}")
	public ResponseEntity<Enrollee> updateEnrollee(@PathVariable(value = "id") Long enrolleeID,
			@RequestBody Enrollee enrolleeDetails) throws ResourceNotFoundException {
		Enrollee enrollee = enrolleeRepo.findById(enrolleeID).orElseThrow(() -> new ResourceNotFoundException("Enrollee not found for this id :: " + enrolleeID));
		enrollee.setLastName(enrolleeDetails.getLastName());
		enrollee.setFirstName(enrolleeDetails.getFirstName());
		enrollee.setAcitvatedStatus(enrolleeDetails.isAcitvatedStatus());
		enrollee.setDob(enrolleeDetails.getDob());
		enrollee.setDependents(enrolleeDetails.getDependents());
		final Enrollee updatedEnrollee = enrolleeRepo.save(enrollee);
		return ResponseEntity.ok(updatedEnrollee);
	}
	
	@DeleteMapping("/enrollees/{id}")
	public Map<String, Boolean> deleteEnrollee(@PathVariable(value = "id") Long enrolleeID)
			throws ResourceNotFoundException {
		Enrollee enrollee = enrolleeRepo.findById(enrolleeID)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollee not found for this id :: " + enrolleeID));

		enrolleeRepo.delete(enrollee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PostMapping("/dependents/add/{id}")
	public ResponseEntity<Enrollee> createDependentsForEnrollee(@PathVariable(value = "id") Long enrolleeID,
			@RequestBody Enrollee enrolleeDetails) throws ResourceNotFoundException {
		Enrollee enrollee = enrolleeRepo.findById(enrolleeID).orElseThrow(() -> new ResourceNotFoundException("Enrollee not found for this id :: " + enrolleeID));
		if(enrollee != null) {
			Iterator<Dependents> it = enrolleeDetails.getDependents().iterator();
			while(it.hasNext()) {
				Dependents dependent = new Dependents();
				dependent.setLastName(enrolleeDetails.getLastName());
				dependent.setFirstName(enrolleeDetails.getFirstName());
				dependent.setDob(enrolleeDetails.getDob());
				dependentsRepo.save(dependent);
			}
			enrollee.setDependents(enrolleeDetails.getDependents());
		}
		final Enrollee updatedEnrollee = enrolleeRepo.save(enrollee);
		return ResponseEntity.ok(updatedEnrollee);
	}
	
	@PostMapping("/dependents/remove/{id}")
	public Map<String, Boolean> deleteDependentsFromEnrollee(@PathVariable(value = "id") Long enrolleeID)
			throws ResourceNotFoundException {
		Enrollee enrollee = enrolleeRepo.findById(enrolleeID)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollee not found for this id :: " + enrolleeID));
		Iterator<Dependents> it = enrollee.getDependents().iterator();
		while(it.hasNext()) {
		Dependents dependent = dependentsRepo.findById(it.next().getId()).orElseThrow(() -> new ResourceNotFoundException("Dependent not found for this id :: " + enrolleeID));
		dependentsRepo.delete(dependent);
		}
		enrollee.setDependents(null);
		final Enrollee updatedEnrollee = enrolleeRepo.save(enrollee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
