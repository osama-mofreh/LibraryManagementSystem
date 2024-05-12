package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookControllerTests {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetBookById() {
        // Mocking
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");
        when(bookService.getBookById(bookId)).thenReturn(book);

        // Test
        ResponseEntity<Book> response = bookController.getBookById(bookId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testGetAllBooks() {
        // Mocking
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "Author 1"));
        books.add(new Book(2L, "Book 2", "Author 2"));
        when(bookService.getAllBooks()).thenReturn(books);

        // Test
        ResponseEntity<List<Book>> response = bookController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testCreateBook() {
        // Mocking
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        when(bookService.createBook(any(Book.class))).thenReturn(newBook);

        // Test
        ResponseEntity<Book> response = bookController.createBook(newBook);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newBook, response.getBody());
    }

    @Test
    public void testUpdateBook() {
        // Mocking
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setTitle("Existing Book");
        existingBook.setAuthor("Existing Author");
        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setTitle("Updated Book");
        updatedBook.setAuthor("Updated Author");
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(updatedBook);

        // Test
        ResponseEntity<Book> response = bookController.updateBook(bookId, updatedBook);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        doAnswer(invocation -> {
            return null;
        }).when(bookService).deleteBook(bookId);

        // Test
        ResponseEntity<?> response = bookController.deleteBook(bookId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}