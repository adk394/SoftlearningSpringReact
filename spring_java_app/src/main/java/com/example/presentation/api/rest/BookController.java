package com.example.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.core.entities.book.dtos.BookDTO;
import com.example.core.entities.book.persistence.BookRepository;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable int id, @RequestBody BookDTO book) {
        book.setId(id);
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        bookRepository.deleteById(id);
    }


}