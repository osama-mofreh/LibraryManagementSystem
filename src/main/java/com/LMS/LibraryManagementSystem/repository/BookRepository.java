package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// BookRepository.java
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
