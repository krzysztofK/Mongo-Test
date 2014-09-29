package org.bookshop.data.repository;

import org.bookshop.data.domain.Publisher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PublisherRepository extends MongoRepository<Publisher, String> {

    List<Publisher> findByName(String name);
}