package com.cognizant.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Agent;
@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
}
