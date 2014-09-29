package org.bookshop.data.repository;

import org.bookshop.data.domain.Patron;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatronRepository extends MongoRepository<Patron, String> {

    List<Patron> findByName(String name);

    List<Patron> findByAddressState(String state);

    List<Patron> findByAddressCityAndAddressStreet(String city, String street);
}
