package com.spring.web.rest;

import com.spring.web.exception.JediNotFoundException;
import com.spring.web.model.Jedi;
import com.spring.web.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class JediResource {

    @Autowired
    private JediRepository repository;

    @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return repository.findAll();
    }

    @GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id) throws JediNotFoundException {
        final Optional<Jedi> jedi = repository.findById(id);
        if(jedi.isPresent()) {
           return ResponseEntity.ok(jedi.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED)
    public Jedi createJedi(@Valid @RequestBody Jedi jedi) {
        return repository.save(jedi);
    }

    @PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@PathVariable("id") Long id, @Valid @RequestBody Jedi dto) {
        final Optional<Jedi> jediEntity = repository.findById(id);
        final Jedi jedi;

        if(jediEntity.isPresent()) {
            jedi = jediEntity.get();
        }
        else {
            return ResponseEntity.notFound().build();
        }

        jedi.setName(dto.getName());
        jedi.setLastName(dto.getLastName());
        return ResponseEntity.ok(jedi);
    }

    @DeleteMapping("/api/jedi/{id}")
    public ResponseEntity deleteJedi(@PathVariable("id") Long id) {
        final Optional<Jedi> jedi = repository.findById(id);

        if (jedi.isPresent()) {
            repository.delete(jedi.get());
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
