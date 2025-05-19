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

import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.dto.PolicyDTO;
import com.cognizant.insurance.dto.ReturnUserDTO;
import com.cognizant.insurance.entity.Customer;
import com.cognizant.insurance.entity.Policy;
import com.cognizant.insurance.exception.AllException.CustomerDetailNotFound;
import com.cognizant.insurance.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUserId(1);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhno((long) 123456789);
        customer.setPassword("password");

        customerDTO = new CustomerDTO();
        customerDTO.setName("John Doe");
        customerDTO.setEmail("john.doe@example.com");
        customerDTO.setPhno((long)1234567890);
        customerDTO.setPassword("password");
    }

    @Test
    public void testAddCustomer() {
        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = customerService.addCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getName(), result.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testRemoveCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        String result = customerService.removeCustomer(1);

        assertEquals("successful", result);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testRemoveCustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        String result = customerService.removeCustomer(1);

        assertEquals("failed no one", result);
        verify(customerRepository, times(0)).delete(customer);
    }

    @Test
    public void testViewCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = customerService.viewCustomer(1);

        assertNotNull(result);
        assertEquals(customerDTO.getName(), result.getName());
    }

    @Test
    public void testViewCustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerDetailNotFound.class, () -> {
            customerService.viewCustomer(1);
        });
    }

    
    @Test
    public void testUpdateCustomer() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        String result = customerService.updateCustomer(1, customerDTO);

        assertEquals("Created", result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetAllCustomer() {
        List<Customer> customers = Arrays.asList(customer);
        ReturnUserDTO returnUserDTO = new ReturnUserDTO();
        returnUserDTO.setName("John Doe");

        when(customerRepository.findAll()).thenReturn(customers);
        when(modelMapper.map(customer, ReturnUserDTO.class)).thenReturn(returnUserDTO);

        List<ReturnUserDTO> result = customerService.getAllCustomer();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }
    
  
}
