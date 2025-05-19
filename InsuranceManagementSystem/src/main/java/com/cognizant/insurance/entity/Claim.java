package com.cognizant.insurance.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Claim {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int claimID;
	private float ClaimAmount;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="policyID")
	private Policy policy;
	
	@ManyToOne	
	@JoinColumn(name="customerID")
	private Customer customer;
	
	
}


      
     