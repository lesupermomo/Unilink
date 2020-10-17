package com.unilink.backend.unilink.model;

import java.util.ArrayList;
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

import com.unilink.backend.unilink.dto.ProjectDto;


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
	
	public Project() {
	
	}
	
	public Project(ProjectDto projectDto) {
		
		this.id=projectDto.getId();
		this.projectName=projectDto.getProjectName();
		this.description=projectDto.getDescription();
		this.status=projectDto.getStatus();
		
	}

	public User getCreator(){
		return this.creator;
	}
	
	public List<User> getMember(){
		return members;
	}
	
	public List<User> getApplicants(){
		return applicants;
	}
	
	public void addMember(User e) {
		if(this.members==null) {
			this.members=new ArrayList<User>();
		}
		members.add(e);
	}
	public void addApplicant(User e) {
		if(this.applicants==null) {
			this.applicants=new ArrayList<User>();
		}
		if(!applicants.contains(e)) {
			//do nothing
			applicants.add(e);
		}
		
	}
	
	public void setCreator(User user) {
		this.creator=user;
	}
	
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
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status status) {
		this.status= status;
	}
	


	public void removeApplicant(User user) {
		this.applicants.remove(user);
		
	}

	
	
	
	
}
