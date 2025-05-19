package com.cognizant.insurance.dto;

import java.util.Set;

import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class PolicyDTO {
	
	private int policyID;
	
    private String policyName;
	
    private float premiumAmount;
    
    private String coverageDetails;
    
    private float validityPeriod;
	
	private Agent agent;
	
	private Set<Customer> customer;

}
