package com.example.core.entities.book.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.core.entities.book.dtos.BookDTO;

@Repository
public interface BookRepository extends JpaRepository<BookDTO, Integer> {

   public Optional<BookDTO> findById(int id);

   public List<BookDTO> findByTitle(String title);

   public List<BookDTO> findByTitleContaining(String title);

   public Integer countByTitleContaining(String title);

   public BookDTO save(BookDTO book);

   public void deleteById(int id);
}
