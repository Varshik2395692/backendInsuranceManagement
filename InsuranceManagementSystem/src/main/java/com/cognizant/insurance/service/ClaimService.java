package com.cognizant.insurance.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import java.util.List;
 
import com.cognizant.insurance.entity.Claim;
import com.cognizant.insurance.entity.Policy;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.repository.ClaimRepository;
import com.cognizant.insurance.repository.PolicyRepository;
import com.cognizant.insurance.repository.CustomerRepository;
 
import jakarta.validation.Valid;
 
@Service
public class ClaimService {
 
    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
 
    // Get all claims
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
 
    // Get claim by ID
    public Claim getClaimById(int claimID) {
        return claimRepository.findById(claimID)
                              .orElseThrow(() -> new RuntimeException("Claim not found"));
    }
    
    // File a claim
    public Claim fileClaim(@Valid Claim claim, int policyID, int customerID) {
        // Validate PolicyID and CustomerID
        Policy policy = policyRepository.findById(policyID)
                                        .orElseThrow(() -> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Policy with ID " + policyID + " not found."
                                        ));
        Customer customer = customerRepository.findById(customerID)
                                              .orElseThrow(() -> new ResponseStatusException(
                                                  HttpStatus.NOT_FOUND,
                                                  "Customer with ID " + customerID + " not found."
                                              ));
 
        // Associate the claim with the policy and customer
        claim.setPolicy(policy);
        claim.setCustomer(customer);
 
        // Save the claim entity
        return claimRepository.save(claim);
    }
 
    // Update claim
    public Claim updateClaim(int claimID, @Valid Claim updatedClaim) {
        Claim claim = claimRepository.findById(claimID).orElse(null);
        if (claim != null) {
            updatedClaim.setClaimID(claimID);
            return claimRepository.save(updatedClaim);
        } else {
            throw new RuntimeException("Claim not found");
        }
    }
 
    // Delete claim
    public void deleteClaim(int claimID) {
        claimRepository.deleteById(claimID);
    }
}
