package com.cognizant.insurance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Claim;
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer>{

List<Claim> findByStatus(String status);

Optional<List<Claim>> findByCustomerUserId(int customerID);


}
