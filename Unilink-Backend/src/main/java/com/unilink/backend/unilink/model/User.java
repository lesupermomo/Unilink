package com.unilink.backend.unilink.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class User {

	
//	@GeneratedValue
//	@Column(name="id")
//	private Integer id;
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	//has to be secure!
	@Column(name="password")
	private String password;
	
	@Column(name="isActive")
	private Boolean isActive;
	
	@Column(name="roles")
	private String roles;

	



	//projects to which the user is a member
	@ManyToMany(mappedBy = "members")
	private List<Project> projects;
	
	@ManyToMany(mappedBy = "applicants")
	private List<Project> applied;
	

	@OneToMany(mappedBy = "creator")
	private List<Project> postings;
	

	
	public User() {
	}
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
//	public Integer getId() {
//		return id;
//	}
//	
//	public void setId(Integer id) {
//		this.id = id;
//	}

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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
