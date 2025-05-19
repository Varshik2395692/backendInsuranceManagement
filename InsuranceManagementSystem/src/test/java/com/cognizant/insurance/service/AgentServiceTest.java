package com.cognizant.insurance.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cognizant.insurance.dto.AgentDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.entity.Agent;
import com.cognizant.insurance.entity.Policy;
import com.cognizant.insurance.entity.Users;
import com.cognizant.insurance.exception.AllException.AgentDetailNotFound;
import com.cognizant.insurance.repository.AgentRepository;
import com.cognizant.insurance.repository.PolicyRepository;
import com.cognizant.insurance.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private AgentService agentService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private Agent agent;
    private AgentDTO agentDTO;
    private Policy policy;
    private PolicyDTO policyDTO;

    @BeforeEach
    public void setUp() {
        agent = new Agent();
        agent.setUserId(1);
        agent.setName("Jane Doe");
        agent.setEmail("jane.doe@example.com");
        agent.setPhno(987654321L);
        agent.setPassword("password");

        agentDTO = new AgentDTO();
        agentDTO.setName("Jane Doe");
        agentDTO.setEmail("jane.doe@example.com");
        agentDTO.setPhno(9876543210L);
        agentDTO.setPassword("password");

        policy = new Policy();
        policy.setPolicyName("Health Insurance");
        policy.setPremiumAmount(5000);
        policy.setCoverageDetails("Full coverage");
        policy.setValidityPeriod(1);

        policyDTO = new PolicyDTO();
        policyDTO.setPolicyName("Health Insurance");
        policyDTO.setPremiumAmount(5000);
        policyDTO.setCoverageDetails("Full coverage");
        policyDTO.setValidityPeriod(1);
    }

    @Test
    public void testAddAgent() {
        when(modelMapper.map(agentDTO, Agent.class)).thenReturn(agent);
        when(agentRepository.save(agent)).thenReturn(agent);
        when(modelMapper.map(agent, AgentDTO.class)).thenReturn(agentDTO);

        AgentDTO result = agentService.addAgent(agentDTO);

        assertNotNull(result);
        assertEquals(agentDTO.getName(), result.getName());
        verify(agentRepository, times(1)).save(agent);
    }

    @Test
    public void testRemoveAgent() {
        when(agentRepository.findById(1)).thenReturn(Optional.of(agent));

        String result = agentService.removeAgent(1);

        assertEquals("successful", result);
        verify(agentRepository, times(1)).delete(agent);
    }

    @Test
    public void testRemoveAgentNotFound() {
        when(agentRepository.findById(1)).thenReturn(Optional.empty());

        String result = agentService.removeAgent(1);

        assertEquals("failed no one", result);
        verify(agentRepository, times(0)).delete(agent);
    }

    @Test
    public void testViewAgent() {
        when(agentRepository.findById(1)).thenReturn(Optional.of(agent));
        when(modelMapper.map(agent, AgentDTO.class)).thenReturn(agentDTO);

        AgentDTO result = agentService.viewAgent(1);

        assertNotNull(result);
        assertEquals(agentDTO.getName(), result.getName());
    }

    @Test
    public void testViewAgentNotFound() {
        when(agentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AgentDetailNotFound.class, () -> {
            agentService.viewAgent(1);
        });
    }

   

    @Test
    public void testGetAllCustomer() {
        List<Agent> agents = Arrays.asList(agent);
        ReturnUserDTO returnUserDTO = new ReturnUserDTO();
        returnUserDTO.setName("Jane Doe");

        when(agentRepository.findAll()).thenReturn(agents);
        when(modelMapper.map(agent, ReturnUserDTO.class)).thenReturn(returnUserDTO);

        List<ReturnUserDTO> result = agentService.getAllCustomer();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jane Doe", result.get(0).getName());
    }

    @Test
    public void testCreatePolicy() {
        when(agentRepository.findById(1)).thenReturn(Optional.of(agent));
        when(modelMapper.map(policyDTO, Policy.class)).thenReturn(policy);
        when(policyRepository.save(policy)).thenReturn(policy);
        when(modelMapper.map(policy, PolicyDTO.class)).thenReturn(policyDTO);

        PolicyDTO result = agentService.createPolicy(1, policyDTO);

        assertNotNull(result);
        assertEquals(policyDTO.getPolicyName(), result.getPolicyName());
        verify(policyRepository, times(1)).save(policy);
    }

  

   

   
}
