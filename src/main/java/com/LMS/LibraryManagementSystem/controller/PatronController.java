package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.model.Patron;
import com.LMS.LibraryManagementSystem.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;

    // Endpoint to retrieve all patrons
    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok().body(patrons);
    }

    // Endpoint to retrieve a specific patron by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatronById(id);
        return ResponseEntity.ok().body(patron);
    }

    // Endpoint to add a new patron
    @PostMapping
    public ResponseEntity<Patron> createPatron(@Valid @RequestBody Patron patron) {
        Patron createdPatron = patronService.createPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatron);
    }

    // Endpoint to update an existing patron
    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patronDetails) {
        Patron updatedPatron = patronService.updatePatron(id, patronDetails);
        return ResponseEntity.ok().body(updatedPatron);
    }

    // Endpoint to delete a patron
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.ok().build();
    }
}

