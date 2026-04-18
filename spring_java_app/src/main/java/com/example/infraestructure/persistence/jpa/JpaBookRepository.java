package com.example.infraestructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.core.entities.book.dtos.BookDTO;

import jakarta.transaction.Transactional;

@Repository
public interface JpaBookRepository extends JpaRepository<BookDTO, Integer> {
    public Optional<BookDTO> findById(int id);

    public List<BookDTO> findByTitle(String title);

    @Query(value="SELECT b FROM BookDTO b WHERE b.title LIKE %:title%")
    public List<BookDTO> findByPartialTitle(String title);

    @Query(value="SELECT count(b) FROM BookDTO b WHERE b.title LIKE %:title%")
    public Long countByPartialTitle(String title);

    @Transactional
    public BookDTO save(BookDTO book);
    public void deleteById(int id);
    
}
