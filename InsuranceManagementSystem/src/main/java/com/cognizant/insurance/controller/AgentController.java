package com.cognizant.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cognizant.insurance.dto.AgentDTO;

import com.cognizant.insurance.dto.PolicyDTO;

import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UpdateClaimStatusDTO;
import com.cognizant.insurance.dto.UpdatedAgentDto;
import com.cognizant.insurance.dto.ViewPoliciesDTO;
import com.cognizant.insurance.entity.Claim;
import com.cognizant.insurance.security.UserService;
import com.cognizant.insurance.service.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/agents")
public class AgentController {

	
	@Autowired
	AgentService agentService;
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("#agentId==authentication.principal.id")
	@DeleteMapping("/{agentId}")
	public ResponseEntity<String> removeAgent(@PathVariable int agentId) {
	    ResponseEntity<String> response = new ResponseEntity<>(agentService.removeAgent(agentId), HttpStatus.OK);  
	   
	    return response;
	}
	@PreAuthorize("#agentId==authentication.principal.id")
	@GetMapping("/{agentId}")
	public ResponseEntity<AgentDTO> viewAgent(@PathVariable int agentId) {
		
	    ResponseEntity<AgentDTO> response = new ResponseEntity<>(agentService.viewAgent(agentId), HttpStatus.OK);
	    
	    return response;
	}
	
	//UPDATE agent details
	@PreAuthorize("#agentId==authentication.principal.id")
	@PutMapping("/update/{agentId}")
public ResponseEntity<UpdatedAgentDto> updateAgent(@PathVariable int agentId, @RequestBody AgentDTO agentDTO) {
UpdatedAgentDto updatedAgent = agentService.updateAgent(agentId, agentDTO);
 return new ResponseEntity<>(updatedAgent, HttpStatus.OK);
}


	
	//getallAgents
	@GetMapping("/getAll")
	public ResponseEntity<List<ReturnUserDTO>> getAllAgent() {
		
	    ResponseEntity<List<ReturnUserDTO>> response = new ResponseEntity<>(agentService.getAllCustomer(), HttpStatus.OK);
	    
	    return response;
	    
	}
	
	//add agent 
	@PostMapping("/add")
	public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) {
	  
	  ResponseEntity<AgentDTO> response = new ResponseEntity<>(agentService.addAgent(agentDTO), HttpStatus.CREATED); 
	
	  return response;
	}

//.....................policies
//add policy
@PreAuthorize("#agentId==authentication.principal.id")
@PostMapping("/{agentId}/addpolicy")
public ResponseEntity<PolicyDTO> createPolicy(@PathVariable int agentId,@RequestBody PolicyDTO policyDTO) {
	
  ResponseEntity<PolicyDTO> response = new ResponseEntity<>(agentService.createPolicy(agentId,policyDTO), HttpStatus.OK);       

  return response;
}




//getbyId
@GetMapping("/{agentId}/policies")
public ResponseEntity<List<ViewPoliciesDTO>> viewPolicyById(@PathVariable int agentId) {
	
    ResponseEntity<List<ViewPoliciesDTO>> response = new ResponseEntity<>(agentService.viewPolicyById(agentId), HttpStatus.OK);
    
    return response;
}

@GetMapping("/claims/status/{status}")
public ResponseEntity<List<Claim>> getAllClaimsByStatus(@PathVariable String status) {
    List<Claim> claims = agentService.getAllClaimsByStatus(status);
    return ResponseEntity.ok(claims);
}

//.........................CLAIMS


    @PatchMapping("/{claimId}/updateStatus")
    public ResponseEntity<Claim> updateClaimStatus(@PathVariable int claimId, @RequestBody UpdateClaimStatusDTO updateClaimStatusDTO) {
        try {
            Claim updatedClaim = agentService.updateClaimStatus(claimId, updateClaimStatusDTO);
            return ResponseEntity.ok(updatedClaim);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }



}





