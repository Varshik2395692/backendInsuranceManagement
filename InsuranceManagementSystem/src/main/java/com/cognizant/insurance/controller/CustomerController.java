package com.cognizant.insurance.controller;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.cognizant.insurance.dto.ClaimDTO;
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;

import com.cognizant.insurance.dto.ViewPoliciesDTO;
import com.cognizant.insurance.entity.Claim;

import com.cognizant.insurance.service.CustomerService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
    @Autowired
    CustomerService customerService;
    


 
    @GetMapping("/customer/all")
    public ResponseEntity<List<ReturnUserDTO>> getALlCustomer() {
        ResponseEntity<List<ReturnUserDTO>> response = new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
        
        return response;
    }

    @PreAuthorize("#customerId==authentication.principal.id")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> removeCustomer(@PathVariable int customerId) {
        ResponseEntity<String> response = new ResponseEntity<>(customerService.removeCustomer(customerId), HttpStatus.OK);
        return response;
    }
    
    @PutMapping("/update/{customerID}")
    public ResponseEntity<String> updateCustomer(@PathVariable int customerID,@RequestBody CustomerDTO customerDTO) {
        ResponseEntity<String> response = new ResponseEntity<>(customerService.updateCustomer(customerID,customerDTO), HttpStatus.CREATED);
        return response;
    }
    
    @PreAuthorize("#customerId==authentication.principal.id")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> viewCustomer(@PathVariable int customerId) {
        ResponseEntity<CustomerDTO> response = new ResponseEntity<>(customerService.viewCustomer(customerId), HttpStatus.OK);
        return response;
    }
    
    
  //getallPolicies
    @GetMapping("/getAllPolicies")
    public ResponseEntity<List<ViewPoliciesDTO>> getAllPolicies() {
      ResponseEntity<List<ViewPoliciesDTO>> response = new ResponseEntity<>(customerService.getAllPolicies(), HttpStatus.OK);
      return response;
      
    }
    //.....policy
    @PreAuthorize("#userId==authentication.principal.id")
    @PatchMapping("/{userId}/applyPolicy/{policyId}")
           public ResponseEntity<PolicyDTO> applyPolicy(@PathVariable int userId, @PathVariable int policyId) {  
   	 PolicyDTO updatedPolicy = customerService.applyPolicy(userId, policyId);
               return ResponseEntity.ok(updatedPolicy);
           }
            
     //...............CLAIM
    			@PreAuthorize("#userId==authentication.principal.id")
                @PatchMapping("/{userId}/fileClaim")
                public ResponseEntity<ClaimDTO> fileClaim(@PathVariable int userId, @RequestBody ClaimDTO claimDTO) {
                    ClaimDTO filedClaim = customerService.fileClaim(userId, claimDTO);
                    return ResponseEntity.ok(filedClaim);
                }


        
    			@PreAuthorize("#customerID==authentication.principal.id")
    			@GetMapping("/claims/user/{customerID}")
    			public ResponseEntity<Optional<List<Claim>>> getClaimsByUserId(@PathVariable int customerID) {
    			    Optional<List<Claim>> claims = customerService.getClaimsByUserId(customerID);
    			    return ResponseEntity.ok(claims);
    			}

  
//add customer
  @PostMapping("/add")
  public ResponseEntity<CustomerDTO> createAgent(@RequestBody CustomerDTO customerDTO) {
      ResponseEntity<CustomerDTO> response = new ResponseEntity<>(customerService.addCustomer(customerDTO), HttpStatus.CREATED);
      return response;
  }
    
  @GetMapping("/getAllPolicies/{customerId}")
  public ResponseEntity<List<ViewPoliciesDTO>> getAllPoliciesByCustomerId(@PathVariable int customerId) {
    ResponseEntity<List<ViewPoliciesDTO>> response = new ResponseEntity<>(customerService.getAllPoliciesByCustomerId(customerId), HttpStatus.OK);
    return response;
    
  }
    
    

}