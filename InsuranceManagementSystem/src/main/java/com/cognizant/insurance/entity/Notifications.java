package com.cognizant.insurance.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Notifications{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int notificationID;
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name="customerID")
	private Customer customer;
	
}
