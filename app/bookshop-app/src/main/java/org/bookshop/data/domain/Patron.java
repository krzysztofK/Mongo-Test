package org.bookshop.data.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="patrons")
public class Patron {

	@Id
	private String id;
	
	@Indexed
	private String name;
	
	private String category;
	
	private Address address;
}
