package com.matskevich.Project2Boot.services;

import com.matskevich.springcourse.models.Book;
import com.matskevich.springcourse.models.Person;
import com.matskevich.springcourse.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BookRepository bookRepository;

    @Autowired
    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("yearOfPublishing"));
        else
            return bookRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfPublishing"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);

        return foundBook.orElse(null);
    }

    public List<Book> searchByTitle(String query) {
        return bookRepository.findByTitleStartingWith(query);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBEUpdated = bookRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setBookOwner(bookToBEUpdated.getBookOwner());

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Person> findBookOwner(int id) {
        Optional<Person> bookOwner = bookRepository.findById(id).map(Book::getBookOwner);
        return bookOwner;
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setBookOwner(null);
                    book.setTakenAt(null);
                });
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setBookOwner(selectedPerson);
                    book.setTakenAt(new Date());        // current time
                });
    }
}
