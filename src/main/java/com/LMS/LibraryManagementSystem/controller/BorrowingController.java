package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.model.BorrowingRecord;
import com.LMS.LibraryManagementSystem.services.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    // Endpoint to borrow a book
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);
    }

    // Endpoint to return a borrowed book
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok().body(borrowingRecord);
    }
}
