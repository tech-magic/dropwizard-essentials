package com.ebuilder.microservices.contactservice.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Contact {

	private final long id;

	@NotBlank
	@Length(min=2,max=255)
	private final String firstName;
	@NotBlank
	@Length(min=2,max=255)
	private final String lastName;
	@NotBlank
	@Length(min=9,max=13)
	private String phone;
	
	
	public Contact(long id, String firstName, String lastName, String phone){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	
	public Contact(){
		this(0, null, null, null);
	}
	
	public long getId() {
		return id;
	}	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhone() {
		return phone;
	}
	
	
}
