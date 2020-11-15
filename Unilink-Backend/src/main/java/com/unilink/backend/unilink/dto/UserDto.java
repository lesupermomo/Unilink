package com.unilink.backend.unilink.dto;


import com.unilink.backend.unilink.model.Person;


public class UserDto {

	
//	@GeneratedValue
//	@Column(name="id")
//	private Integer id;
	
	
	private String email;

	private String firstName;
	

	private String lastName;
	
	
	private String password;

	private Boolean isActive;

	private String roles;

	
	public UserDto() {
		
	}
	
	public UserDto(Person user) {
		this.email=user.getEmail();
		this.firstName=user.getFirstName();
		this.lastName=user.getLastName();
		this.password=user.getPassword();
		this.isActive=user.getIsActive();
		this.roles=user.getRoles();
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
