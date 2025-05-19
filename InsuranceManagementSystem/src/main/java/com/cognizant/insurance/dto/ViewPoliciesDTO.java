package com.cognizant.insurance.dto;

import lombok.Data;

@Data
public class ViewPoliciesDTO {
	private int policyID;
	
	private String policyName;
	
    private float premiumAmount;
    
    private String coverageDetails;
    
    private float validityPeriod;
}
