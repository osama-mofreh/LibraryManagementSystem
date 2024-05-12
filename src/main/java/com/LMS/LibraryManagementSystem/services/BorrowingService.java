package com.LMS.LibraryManagementSystem.services;

import com.LMS.LibraryManagementSystem.model.Book;
import com.LMS.LibraryManagementSystem.model.BorrowingRecord;
import com.LMS.LibraryManagementSystem.model.Patron;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import com.LMS.LibraryManagementSystem.repository.BorrowingRepository;
import com.LMS.LibraryManagementSystem.repository.PatronRepository;
import com.LMS.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

// BorrowingService.java
@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    @CacheEvict(cacheNames = {"borrowingRecords", "availableBooks"}, allEntries = true)
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        // Check if the book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookId + " not found!"));

        // Check if the patron exists
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron with id " + patronId + " not found!"));

        // Check if the book is available for borrowing
        if (!isBookAvailableForBorrowing(bookId)) {
            throw new ResourceNotFoundException(bookId + " not available for borrowing!");
        }

        // Create a new borrowing record
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        // Save the borrowing record
        return borrowingRepository.save(borrowingRecord);
    }

    @CacheEvict(cacheNames = {"borrowingRecords", "availableBooks"}, allEntries = true)
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        // Check if the book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookId + " not found!"));

        // Check if the patron exists
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron with id " + patronId + " not found!"));

        // Find the borrowing record for the given book and patron
        BorrowingRecord borrowingRecord = borrowingRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("BorrowingRecord with bookId=" + bookId + ", patronId=" + patronId + " not found!"));

        // Set the return date
        borrowingRecord.setReturnDate(LocalDate.now());

        // Save the updated borrowing record
        return borrowingRepository.save(borrowingRecord);
    }

    @Cacheable(value = "availableBooks", key = "#bookId")
    private boolean isBookAvailableForBorrowing(Long bookId) {
        // Check if there are any unreturned borrowing records for the given book
        return !borrowingRepository.existsByBookIdAndReturnDateIsNull(bookId);
    }
}
