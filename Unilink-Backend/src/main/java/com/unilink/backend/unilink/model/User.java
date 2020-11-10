package com.unilink.backend.unilink.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.unilink.backend.unilink.dto.UserDto;


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
	private String roles; //ADMIN,USER

	
	//projects to which the user is a member
	@ManyToMany(mappedBy = "members")
	private List<Project> projects;
	
	//projects you applied to
	@ManyToMany(mappedBy = "applicants")
	private List<Project> applied;
	
	
	@OneToMany(mappedBy = "creator")
	private List<Project> created;
	

	public User() {
		
	}
	
	public User(UserDto user) {
		this.email=user.getEmail();
		this.firstName=user.getFirstName();
	    this.lastName=user.getLastName();
		this.password=user.getPassword();
	    this.isActive=user.getIsActive();
	    this.roles=user.getRoles();
	    
	}
	
	
	public void addProjectMember(Project project) {
		
		if(projects==null) {
			projects=new ArrayList<Project>();
		}
		
		if(!projects.contains(project)) {
			//null
			this.projects.add(project);
		}
	
	}
	
	public List<Project> getProjectMember() {
		return projects;
	}
	
	public Boolean removeProjectMember(Project project) {
		return projects.remove(project);
	}
	
	
	public void addProjectApplied(Project p) {
		if(applied==null) {
			applied=new ArrayList<Project>();
		}
		this.applied.add(p);
	}
	
	public Boolean removeProjectApplied(Project project) {
		return applied.remove(project);
	
	}
	
	public List<Project> getProjectApplied(){
		return applied;
	}
	
	public void addProjectCreator(Project project) {
		if(created==null) {
			created=new ArrayList<Project>();
		}
		if(projects==null) {
			projects=new ArrayList<Project>();
		}
		this.created.add(project);
		this.projects.add(project);
	}
	
	public List<Project> getProjectCreator() {
		return this.created;
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
