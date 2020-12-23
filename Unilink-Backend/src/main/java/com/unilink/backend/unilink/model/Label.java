package com.unilink.backend.unilink.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Label {

//	@Id
//	@GeneratedValue
//	@Column(name="id")
//	private Integer id;

	
	
	@Id
	@Column(name="labelName")
	private String labelName;

	@Column(name="description")
	private String description;
	
	@Column(name="numTags")
	private int numTags;


	//projects you applied to
	@ManyToMany(mappedBy = "labels")
	private List<Project> projects;


	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Label() {
		
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addProject(Project proj) {
		if(proj!=null&&proj.equals(null)==false) {
			if(projects==null) {
				projects= new ArrayList<Project>();
			}
			this.projects.add(proj);
			this.numTags++;
		}
		
	}
	public List<Project> getProjects() {
		return this.projects;
	}
	
	public int getNumTags() {
		return this.numTags;
	}
	
	public void removeProject(Project proj) {
		if(proj!=null&&proj.equals(null)==false) {
			if(projects==null) {
				projects= new ArrayList<Project>();
			}
			this.projects.remove(proj);
		}
		
	}


}
