package com.unilink.backend.unilink.dto;

public class CredentialDto { //authentication Request
	private String email;
	private String password;
	
	public CredentialDto() {
	}
	
	public CredentialDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
}
