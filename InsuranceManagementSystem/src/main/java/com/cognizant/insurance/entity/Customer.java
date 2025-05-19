package com.cognizant.insurance.entity;




import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;



//@Entity
//@Data
//public class Customer extends Users{
//	private Long phno;
//}
@Entity

public class Customer extends Users {

    private Long phno;

    public Long getPhno() {
		return phno;
	}

	public void setPhno(Long phno) {
		this.phno = phno;
	}

	public Set<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(Set<Policy> policies) {
		this.policies = policies;
	}

	@ManyToMany
    @JoinTable(
        name = "customer_policy",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
	private Set<Policy> policies = new HashSet<>();

}


