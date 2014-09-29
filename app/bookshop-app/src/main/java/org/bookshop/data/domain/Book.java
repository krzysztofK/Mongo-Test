package org.bookshop.data.domain;

import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Language;

import java.util.List;

@Document(collection = "books", language = "none")
public class Book {

    @Id
    private String id;

    @TextIndexed
    private String title;

    private List<String> authors;

    private int pages;

    private LocalDate publishedDate;

    @Language
    private String language;

    public Book(String title, List<String> authors, int pages, LocalDate publishedDate, String language) {
        this.title = title;
        this.authors = authors;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.language = language;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", pages=" + pages +
                ", publishedDate=" + publishedDate +
                ", language='" + language + '\'' +
                '}';
    }
}
