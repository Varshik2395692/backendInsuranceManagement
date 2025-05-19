package com.cognizant.insurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.insurance.entity.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmail(String email);



	Users findByEmailAndPassword(String email, String password);
}
