package com.LMS.LibraryManagementSystem.services;

import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import com.LMS.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "allBooks")
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @CacheEvict(cacheNames = {"allBooks", "books"}, allEntries = true)
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @CacheEvict(cacheNames = {"allBooks", "books"}, allEntries = true)
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(cacheNames = {"allBooks", "books"}, allEntries = true)
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        // Set other attributes
        return bookRepository.save(book);
    }

    @CacheEvict(cacheNames = {"allBooks", "books"}, allEntries = true)
    public ResponseEntity<?> deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }
}