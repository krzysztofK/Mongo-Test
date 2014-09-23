package org.bookshop.data.domain;

import org.springframework.data.mongodb.core.index.Indexed;

public class Address {

	private String street;
	
	private String city;
	
	@Indexed
	private String state;
	
	private String zip;
}
