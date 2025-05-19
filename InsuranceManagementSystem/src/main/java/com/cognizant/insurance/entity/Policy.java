package com.cognizant.insurance.entity;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Policy {
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int policyID;
	
	@Column
    @NotBlank(message = "Policy name cannot be blank")
    @Size(min = 2, max = 50, message = "Policy name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Policy name must contain only letters and spaces")
	private String policyName;
	

    @NotNull
    @Positive
	private float premiumAmount;
    

    @NotNull
    @Size(max = 500)
	private String coverageDetails;
    

    @NotNull
    @Positive
	private float validityPeriod;
	
    @ManyToMany(mappedBy = "policies")
    @JsonIgnore
    private Set<Customer> customers;

	
	@ManyToOne
	@JoinColumn(name="agentID")
	private Agent agent;
	
	
	
}
