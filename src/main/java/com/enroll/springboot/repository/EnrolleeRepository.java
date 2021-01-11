package com.enroll.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enroll.springboot.model.Enrollee;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {

}
