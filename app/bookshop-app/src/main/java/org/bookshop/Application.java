package org.bookshop;

import org.bookshop.data.domain.*;
import org.bookshop.data.repository.BookRepository;
import org.bookshop.data.repository.PatronRepository;
import org.bookshop.data.repository.PublisherRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.query.TextCriteria;

import static com.google.common.collect.Lists.newArrayList;

@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        testOneToOne();
        testOneToMany();
        testTextIndex();
        testUpdate();
    }

    private void testOneToOne() {
        System.out.println("===== Testing one to one =====");
        patronRepository.deleteAll();

        patronRepository.save(new Patron("Boleslaw Kowalski", Category.NEW, new Address("Wadowicka", "Krakow", "Malopolskie", "30-100")));
        patronRepository.save(new Patron("Wladyslaw Nowak", Category.GOLD, new Address("Zielona", "Warszawa", "Mazowieckie", "10-100")));

        System.out.println(patronRepository.findByName("Boleslaw Kowalski"));
        System.out.println(patronRepository.findByAddressState("Mazowieckie"));
        System.out.println(patronRepository.findByAddressCityAndAddressStreet("Krakow", "Wadowicka"));
    }

    private void testOneToMany() {
        System.out.println("===== Testing one to many =====");
        publisherRepository.deleteAll();
        bookRepository.deleteAll();

        Book bookMongo = new Book("MongoDB", newArrayList("Boleslaw Kowalski", "Wladyslaw Nowak"), 100, new LocalDate(2010, 10, 2), "EN");
        Book bookJava = new Book("Java 8", newArrayList("Boleslaw Kowalski"), 25, new LocalDate(2012, 11, 2), "EN");
        bookRepository.save(newArrayList(bookJava, bookMongo));

        Publisher publisher = new Publisher("O'Reilly Media", 1980, "CA", newArrayList(bookMongo, bookJava));
        publisherRepository.save(publisher);

        System.out.println(publisherRepository.findByName("O'Reilly Media"));
        System.out.println(bookRepository.findByPublishedDateGreaterThan(new LocalDate(2012, 10, 1)));
    }

    private void testTextIndex() {
        System.out.println("===== Testing text indexes =====");
        bookRepository.deleteAll();

        Book bookGE = new Book("Die Leiden des jungen Werthers", newArrayList("Johann Wolfgang von Goethe"), 100, new LocalDate(1774, 10, 2), "DE");
        Book bookPL = new Book("W pustyni i w puszczy", newArrayList("Henryk Sienkiewicz"), 500, new LocalDate(1911, 11, 2), null);
        Book bookEN = new Book("Heart of Darkness", newArrayList("Joseph Conrad"), 121, new LocalDate(1899, 11, 2), "EN");

        bookRepository.save(newArrayList(bookGE, bookPL, bookEN));

        searchByText("jung", "DE");
        searchByText("junger", "DE");
        searchByText("jun", "DE");
        searchByText("leider", "DE");
        searchByText("dark", "EN");
        searchByText("hearts", "EN");
        searchByText("puszcza", "none");
        searchByText("puszczy", "none");
    }

    private void searchByText(String text, String language) {
        System.out.println("Searching by " + text + ": " + bookRepository.findAllBy(TextCriteria.forLanguage(language).matching(text)));
    }

    private void testUpdate() {
        System.out.println("===== Testing updates =====");
        publisherRepository.deleteAll();
        bookRepository.deleteAll();

        Book bookMongo = new Book("MongoDB", newArrayList("Boleslaw Kowalski", "Wladyslaw Nowak"), 100, new LocalDate(2010, 10, 2), "EN");
        Book bookJava = new Book("Java 8", newArrayList("Boleslaw Kowalski"), 25, new LocalDate(2012, 11, 2), "EN");
        bookRepository.save(newArrayList(bookJava, bookMongo));

        Publisher publisher = new Publisher("O'Reilly Media", 1980, "CA", newArrayList(bookMongo, bookJava));
        publisherRepository.save(publisher);

        System.out.println("Before update: " + publisherRepository.findByName("O'Reilly Media"));

        Book bookJavaNew = new Book("Java 8 - new edition", newArrayList("Boleslaw Kowalski"), 100, new LocalDate(2014, 9, 30), "EN");
        bookRepository.save(bookJavaNew);
        publisher.addBook(bookJavaNew);
        publisherRepository.save(publisher);
        System.out.println("After update: " + publisherRepository.findByName("O'Reilly Media"));
    }
}
