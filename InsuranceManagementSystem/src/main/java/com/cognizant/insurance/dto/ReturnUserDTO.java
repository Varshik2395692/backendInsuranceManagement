package com.cognizant.insurance.dto;



public class ReturnUserDTO {
	
	private int id;
	private String name;
	
	private String password;
	
	private String email;
	
	
	private Long phno;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getPhno() {
		return phno;
	}


	public void setPhno(Long phno) {
		this.phno = phno;
	}
	
}
