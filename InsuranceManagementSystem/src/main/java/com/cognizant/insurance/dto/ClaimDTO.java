package com.cognizant.insurance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ClaimDTO {
    @NotNull
    private int claimID;

    @NotNull
    @Positive
    private float claimAmount;

   

    @NotNull
    private int policyID;
    


     private int customerID;


}
