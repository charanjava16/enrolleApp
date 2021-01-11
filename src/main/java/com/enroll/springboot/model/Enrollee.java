package com.enroll.springboot.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "enrollees")
public class Enrollee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name = "activated_status")
	private boolean acitvatedStatus;
	
	@Column(name= "dob")
	private Date dob;
	
	 @OneToMany(mappedBy="dependents")
    private Set<Dependents> dependents;
	
	
	public Enrollee(long id, String firstName, String lastName, boolean acitvatedStatus, Date dob,
			Set<Dependents> dependents) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.acitvatedStatus = acitvatedStatus;
		this.dob = dob;
		this.dependents = dependents;
	}


	public Enrollee() {
		super();
	}
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean isAcitvatedStatus() {
		return acitvatedStatus;
	}
	public void setAcitvatedStatus(boolean acitvatedStatus) {
		this.acitvatedStatus = acitvatedStatus;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Set<Dependents> getDependents() {
		return dependents;
	}

	public void setDependents(Set<Dependents> dependents) {
		this.dependents = dependents;
	}
	
	
}
