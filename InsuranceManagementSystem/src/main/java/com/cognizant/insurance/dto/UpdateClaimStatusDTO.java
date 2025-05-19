package com.cognizant.insurance.dto;

import lombok.Data;

@Data
public class UpdateClaimStatusDTO {
    private int policyID;
    private String status; // "Approved" or "Rejected"
}
