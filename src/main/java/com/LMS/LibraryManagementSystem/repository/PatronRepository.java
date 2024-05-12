package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PatronRepository.java
@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
}
