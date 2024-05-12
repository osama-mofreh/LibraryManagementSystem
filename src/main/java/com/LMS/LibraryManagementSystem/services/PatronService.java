package com.LMS.LibraryManagementSystem.services;

import com.LMS.LibraryManagementSystem.model.Patron;
import com.LMS.LibraryManagementSystem.repository.PatronRepository;
import com.LMS.LibraryManagementSystem.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

// PatronService.java
@Service
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;

    @Cacheable(value = "allPatrons")
    public List<Patron> getAllPatrons() {
        return (List<Patron>) patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key = "#id")
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
    }

    @CacheEvict(cacheNames = {"allPatrons", "patrons"}, allEntries = true)
    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @CacheEvict(cacheNames = {"allPatrons", "patrons"}, allEntries = true)
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        patron.setName(patronDetails.getName());
        patron.setContactInformation(patronDetails.getContactInformation());
        // Set other attributes
        return patronRepository.save(patron);
    }

    @CacheEvict(cacheNames = {"allPatrons", "patrons"}, allEntries = true)
    public ResponseEntity<?> deletePatron(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        patronRepository.delete(patron);
        return ResponseEntity.ok().build();
    }
}