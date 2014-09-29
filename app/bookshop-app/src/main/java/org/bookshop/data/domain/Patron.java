package org.bookshop.data.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patrons")
@CompoundIndexes({@CompoundIndex(name = "street_city", def = "{'address.city':1, 'address.street':1}")})
public class Patron {

    @Id
    private String id;

    @Indexed
    private String name;

    private Category category;

    private Address address;

    public Patron(String name, Category category, Address address) {
        this.name = name;
        this.category = category;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", address=" + address +
                '}';
    }
}
