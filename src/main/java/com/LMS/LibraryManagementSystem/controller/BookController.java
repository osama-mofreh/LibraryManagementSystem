package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BookController.java
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    // Endpoint to retrieve all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = (List<Book>) bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }

    // Endpoint to retrieve a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    // Endpoint to add a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    // Endpoint to update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok().body(updatedBook);
    }

    // Endpoint to delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}