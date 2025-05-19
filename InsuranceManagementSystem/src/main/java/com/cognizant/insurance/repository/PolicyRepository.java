package com.cognizant.insurance.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Policy;
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {

	List<Policy> findByAgentUserId(int userId);
	List<Policy> findByCustomersUserId(int customerId);

//	List<Policy> findByAgentId(Agent agent);

	



}
