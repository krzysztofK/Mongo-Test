package org.bookshop.data.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "publishers")
public class Publisher {

    @Id
    private String id;

    @Indexed
    private String name;

    private int founded;

    private String location;

    @DBRef
    private List<Book> books;

    public Publisher(String name, int founded, String location, List<Book> books) {
        this.name = name;
        this.founded = founded;
        this.location = location;
        this.books = books;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", founded=" + founded +
                ", location='" + location + '\'' +
                ", books=" + books +
                '}';
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
