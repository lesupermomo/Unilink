package com.unilink.backend.unilink.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Project {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="projectName")
	private String projectName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private Status status;
	
	@ManyToMany
	@JoinTable(
	  name = "project_member", 
	  joinColumns = {@JoinColumn(name = "project_id")}, 
	  inverseJoinColumns = {@JoinColumn(name = "member_id")}
	  )
	private List<User> members;
	
	@ManyToMany
	@JoinTable(
	  name = "project_applicant", 
	  joinColumns = {@JoinColumn(name = "project_id")}, 
	  inverseJoinColumns = {@JoinColumn(name = "applicant_id")}
	  )
	private List<User> applicants;
	
	
	@ManyToOne
	private User creator;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
}
