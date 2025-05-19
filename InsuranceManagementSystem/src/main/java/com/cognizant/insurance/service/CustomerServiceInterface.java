package com.cognizant.insurance.service;
 
import java.util.List;
 
import com.cognizant.insurance.dto.CustomerDTO;
import com.cognizant.insurance.entity.Customer;
 
public interface CustomerServiceInterface {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomer(int customerID);
    void createCustomer(Customer customer);
    CustomerDTO updateCustomer(int customerID, CustomerDTO updatedCustomerDTO);
    void removeCustomer(int customerID);
    
    
}