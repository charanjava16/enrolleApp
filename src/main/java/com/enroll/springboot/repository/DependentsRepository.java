package com.enroll.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enroll.springboot.model.Dependents;

@Repository
public interface DependentsRepository extends JpaRepository<Dependents, Long> {

}
