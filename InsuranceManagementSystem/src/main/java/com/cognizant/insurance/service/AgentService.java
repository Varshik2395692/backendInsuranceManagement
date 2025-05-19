package com.cognizant.insurance.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.dto.UpdateClaimStatusDTO;
import com.cognizant.insurance.dto.UpdatedAgentDto;
import com.cognizant.insurance.dto.ViewPoliciesDTO;
import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Claim;

import com.cognizant.insurance.entity.Policy;
import com.cognizant.insurance.exception.AllException.AgentDetailNotFound;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.AgentRepository;
import com.cognizant.insurance.repository.ClaimRepository;
import com.cognizant.insurance.repository.PolicyRepository;
import com.cognizant.insurance.repository.UserRepo;



@Service
public class AgentService {
@Autowired
	ModelMapper modelMapper;
@Autowired
AgentRepository agentRepository ;

@Autowired
PolicyRepository policyRepository ;
@Autowired
UserRepo userRepository;
@Autowired 
ClaimRepository claimRepository;


BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	public AgentDTO addAgent(AgentDTO agentDTO){
		
		Agent agent= modelMapper.map(agentDTO,Agent.class);
		agent.setPassword(encoder.encode(agent.getPassword()));
		agent=agentRepository.save(agent);
		
		return modelMapper.map(agent, AgentDTO.class);
	}
	
	
	public String removeAgent(int agentId) {
	Optional<Agent> container=agentRepository.findById(agentId);
	if(!container.isPresent()) {
		return "failed no one";
	}
	Agent agent=container.get();
	agentRepository.delete(agent);
	return "successful";
	
	}
	
	
	public AgentDTO viewAgent(int agentId) {
Agent agent=agentRepository.findById(agentId).orElseThrow(
	()->new AgentDetailNotFound("Agent with Id "+agentId+" not found."));
		return modelMapper.map(agent,AgentDTO.class);
	}


	public UpdatedAgentDto updateAgent(int agentId, AgentDTO agentDTO) {
	    Agent agent = agentRepository.findById(agentId)
	            .orElseThrow(() -> new CustomerDetailNotFound("Agent with Id " + agentId + " not found."));

	    if (agentDTO.getName() != null) agent.setName(agentDTO.getName());
	    if (agentDTO.getEmail() != null) agent.setEmail(agentDTO.getEmail());
	    if (agentDTO.getPhno() != null) agent.setPhno(agentDTO.getPhno());
	    if (agentDTO.getPassword() != null) agent.setPassword(encoder.encode(agentDTO.getPassword()));

	    Agent updatedAgent = agentRepository.save(agent);

	    // Convert to UpdatedAgentDto
	    UpdatedAgentDto dto = new UpdatedAgentDto();
	    dto.setUserId(updatedAgent.getUserId());
	    dto.setName(updatedAgent.getName());
	    dto.setEmail(updatedAgent.getEmail());
	    dto.setPhno(updatedAgent.getPhno());

	    return dto;
	}


	
	public List<ReturnUserDTO> getAllCustomer() {
		// TODO Auto-generated method stub
		List<Agent> agents=agentRepository.findAll();
		return agents.stream()
                .map(agent -> modelMapper.map(agent, ReturnUserDTO.class))
                .collect(Collectors.toList());
	}

	//..................Policies
	
//create policy
	public PolicyDTO createPolicy(int agentId,PolicyDTO policyDTO) {
		Policy policy= modelMapper.map(policyDTO,Policy.class);
		Agent agent=agentRepository.findById(agentId)
				.orElseThrow(()->new AgentDetailNotFound("Agent with Id "+agentId+" not found."));
		policy.setAgent(agent);
		policy=policyRepository.save(policy);
		
		return modelMapper.map(policy, PolicyDTO.class);
	}




	 public List<ViewPoliciesDTO> viewPolicyById(int agentID) {
	        Agent agent = agentRepository.findById(agentID)
	                .orElseThrow(() -> new RuntimeException("Agent not found"));

	        return policyRepository.findByAgentUserId(agent.getUserId())
	                .stream()
	                .map(policy -> modelMapper.map(policy, ViewPoliciesDTO.class))
	                .collect(Collectors.toList());
	    }
	


	//.................claim
	 
	 public List<Claim> getAllClaimsByStatus(String status) {
		    return claimRepository.findByStatus(status);
		}


	 

	     public Claim updateClaimStatus(int claimId, UpdateClaimStatusDTO updateClaimStatusDTO) {
	         try {
	             Claim claim = claimRepository.findById(claimId)
	                     .orElseThrow(() -> new RuntimeException("Claim with Id " + claimId + " not found."));
	             
	             Policy policy = policyRepository.findById(updateClaimStatusDTO.getPolicyID())
	                     .orElseThrow(() -> new RuntimeException("Policy with Id " + updateClaimStatusDTO.getPolicyID() + " not found."));
	             
	             claim.setPolicy(policy);
	             claim.setStatus(updateClaimStatusDTO.getStatus()); // Update status to "Approved" or "Rejected"
	             claim = claimRepository.save(claim);

	             return claim;
	         } catch (Exception e) {
	             throw new RuntimeException("Error updating claim status: " + e.getMessage(), e);
	         }
	     }
	 }

	


