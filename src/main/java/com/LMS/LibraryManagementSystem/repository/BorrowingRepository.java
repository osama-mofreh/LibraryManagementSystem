package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// BorrowingRepository.java
@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingRecord, Long> {

    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}

