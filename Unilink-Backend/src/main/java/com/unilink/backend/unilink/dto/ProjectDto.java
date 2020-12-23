package com.unilink.backend.unilink.dto;

import java.util.ArrayList;
import java.util.List;

import com.unilink.backend.unilink.model.Project;
import com.unilink.backend.unilink.model.Status;


public class ProjectDto {
	
	
	private Integer id;
	private String projectName;
	private String description;
	private Status status;
	

	private List<UserDto> members;
	
	
	private List<UserDto> applicants;
	
	private List<LabelDto> labels;
	
	

	

	private UserDto creator;
	
	
	
	public ProjectDto() {
		
	}
	
	public ProjectDto(Project project) {
		this.id=project.getId();
		this.projectName=project.getProjectName();
		this.description=project.getDescription();
		this.status=project.getStatus();
		members=new ArrayList<UserDto>();
		applicants= new ArrayList<UserDto>();
		labels=new ArrayList<LabelDto>();
		
		if(project.getMember()!=null) {
			for(int i=0; i<project.getMember().size(); i++) {
				members.add(new UserDto(project.getMember().get(i)));
				
			}
		}
		
		if(project.getApplicants()!=null) {
			for(int i=0; i<project.getApplicants().size(); i++) {
				applicants.add(new UserDto(project.getApplicants().get(i)));
				
			}
		}
		
		if(project.getApplicants()!=null) {
			for(int i=0; i<project.getLabels().size(); i++) {
				labels.add(new LabelDto(project.getLabels().get(i)));
				
			}
		}
		
		if(project.getCreator()!=null) {
			creator=new UserDto(project.getCreator());
		}
		
	}
	
	public UserDto getCreator(){
		return this.creator;
	}
	
	public List<UserDto> getMember(){
		return members;
	}
	
	public List<UserDto> getApplicants(){
		return applicants;
	}
	
	public List<LabelDto> getLabels() {
		return labels;
	}

	public void setLabels(List<LabelDto> labels) {
		this.labels = labels;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setCreator(UserDto user) {
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

	
	
	
	
	
}
