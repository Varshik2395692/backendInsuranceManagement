package com.cognizant.insurance.entity;


import jakarta.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Agent extends Users{
	
	private Long phno;
	
	
}







