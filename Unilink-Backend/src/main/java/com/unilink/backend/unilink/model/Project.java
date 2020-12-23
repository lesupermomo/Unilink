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
	private List<Person> members;
	
	@ManyToMany
	@JoinTable(
	  name = "project_applicant", 
	  joinColumns = {@JoinColumn(name = "project_id")}, 
	  inverseJoinColumns = {@JoinColumn(name = "applicant_id")}
	  )
	private List<Person> applicants;
	
	@ManyToMany
	@JoinTable(
	  name = "project_label", 
	  joinColumns = {@JoinColumn(name = "project_id")}, 
	  inverseJoinColumns = {@JoinColumn(name = "label_id")}
	  )
	private List<Label> labels;
	
	
	@ManyToOne
	private Person creator;
	
	public Project() {
	
	}
	
	public Project(ProjectDto projectDto) {
		
		this.id=projectDto.getId();
		this.projectName=projectDto.getProjectName();
		this.description=projectDto.getDescription();
		this.status=projectDto.getStatus();
		
	}

	public Person getCreator(){
		return this.creator;
	}
	
	public List<Person> getMember(){
		return members;
	}
	
	public List<Person> getApplicants(){
		return applicants;
	}
	
	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	
	public void addMember(Person e) {
		if(this.members==null) {
			this.members=new ArrayList<Person>();
		}
		members.add(e);
	}
	public void addApplicant(Person e) {
		if(this.applicants==null) {
			this.applicants=new ArrayList<Person>();
		}
		if(!applicants.contains(e)) {
			applicants.add(e);
		}
		
	}
	
	public void addLabel(Label l) {
		if(this.labels==null) {
			this.labels=new ArrayList<Label>();
		}
		if(!labels.contains(l)) {
			labels.add(l);
		}
	}
	
	public void setCreator(Person person) {
		this.creator=person;
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
	
	public void removeApplicant(Person person) {
		this.applicants.remove(person);
		
	}
	
	public void removeMember(Person person) {
		this.members.remove(person);
	}
	
	public void removeLabel(Label l) {
		this.labels.remove(l);
	}
	
	
}
