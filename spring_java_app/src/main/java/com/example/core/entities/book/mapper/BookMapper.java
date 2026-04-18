package com.example.core.entities.book.mapper;

import com.example.core.entities.book.model.Book;  
import com.example.core.entities.book.dtos.BookDTO;

/**
 * Mapper de Book
 * Convierte entre Book (dominio) y BookDTO
 */
public class BookMapper {

    /**
     * Convierte un Book del dominio a BookDTO
     */
    public static BookDTO BookToDTO(Book b) {

        return new BookDTO(
                b.getTitle(),                              // title
                Integer.parseInt(b.getIdProduct()),        // id
                b.getPrice(),                              // price
                b.getAuthor(),                             // author
                b.getIsbn(),                               // isbn
                String.valueOf(b.getYearPublished()),      // releaseDate
                b.getEditorial(),                          // publisher
                b.getPhysicalData().getWeight(),           // weight
                b.getPhysicalData().getHeight(),           // height
                b.getPhysicalData().getWidth(),            // width
                b.getPhysicalData().getDepth()             // depth
        );
    }

    /**
     * Convierte un BookDTO a Book del dominio
     */
    public static Book DTOtoBook(BookDTO dto) throws Exception {

        return Book.getInstance(
                String.valueOf(dto.getId()),          // idProduct
                dto.getTitle(),                       // name
                dto.getTitle(),                       // description (no viene en dto)
                dto.getPrice(),                       // price
                0,                                    // stock (no está en DTO)
                true,                                 // isAvailable
                dto.getIsbn(),                        // isbn
                dto.getTitle(),                       // title
                dto.getAuthor(),                      // author
                dto.getPublisher(),                   // publisher
                Integer.parseInt(dto.getReleaseDate()), // publishYear
                dto.getWeight(),                      // weight
                dto.getHeight(),                      // height
                dto.getWidth(),                       // width
                dto.getDepth()                        // depth
        );
    }
}
