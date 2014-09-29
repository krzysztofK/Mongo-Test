package org.bookshop.data.repository;

import org.bookshop.data.domain.Book;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByPublishedDateGreaterThan(LocalDate localDate);

    List<Book> findAllBy(TextCriteria textCriteria);
}
