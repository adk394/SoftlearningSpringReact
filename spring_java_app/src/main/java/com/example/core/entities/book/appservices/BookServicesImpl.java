package com.example.core.entities.book.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.core.entities.book.dtos.BookDTO;
import com.example.core.entities.book.persistence.BookRepository;
import com.example.services.serializers.Serializer;
import com.example.services.serializers.Serializers;
import com.example.services.serializers.SerializersCatalog;
import com.example.shared.exceptions.ServiceException;

@Controller
public class BookServicesImpl implements BookServices {

    @Autowired
    private BookRepository bookRepository;
    private Serializer<BookDTO> serializer;

    protected BookDTO getDTO(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    protected BookDTO getById(int id) throws ServiceException {
        BookDTO bdto = this.getDTO(id);

        if (bdto == null) {
            throw new ServiceException("book " + id + " not found");
        }
        return bdto;
    }

    protected BookDTO checkInputData(String book) throws ServiceException {
        try {
            return this.serializer.deserialize(book, BookDTO.class);
        } catch (Exception e) {
            throw new ServiceException("error in the input book data: " + e.getMessage(), e);
        }
    }

    protected BookDTO newBook(String book) throws ServiceException {
        BookDTO bdto = this.checkInputData(book);

        if (this.getDTO(bdto.getId()) == null) {
            return bookRepository.save(bdto);
        }
        throw new ServiceException("book " + bdto.getId() + " already exists");
    }

    protected BookDTO updateBook(String book) throws ServiceException {
        try {
            BookDTO bdto = this.checkInputData(book);
            this.getById(bdto.getId());
            return bookRepository.save(bdto);
        } catch (ServiceException e) {
            throw e;
        }
    }

    // implementando metodos de interfaz

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        try {
            BookDTO dto = this.getById(id); // ← asegúrate de que esto devuelve un BookDTO
            return SerializersCatalog.getInstance(Serializers.JSON_BOOK).serialize(dto);
        } catch (Exception e) {
            throw new ServiceException("Error serializando el libro", e);
        }
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        try {
            return SerializersCatalog.getInstance(Serializers.XML_BOOK)
                    .serialize(this.getById(id));
        } catch (Exception e) {
            throw new ServiceException("Error serializando a XML el libro con ID " + id, e);
        }
    }

    @Override
    public String addFromJson(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
            return serializer.serialize(this.newBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error añadiendo libro desde JSON", e);
        }
    }

    @Override
    public String addFromXml(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
            return serializer.serialize(this.newBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error añadiendo libro desde XML", e);
        }
    }

    @Override
    public String updateOneFromJson(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
            return serializer.serialize(this.updateBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error actualizando libro desde JSON", e);
        }
    }

    @Override
    public String updateOneFromXml(String book) throws ServiceException {
        try {
            this.serializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
            return serializer.serialize(this.updateBook(book));
        } catch (Exception e) {
            throw new ServiceException("Error actualizando libro desde JSON", e);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        try {
            this.getById(id);
            bookRepository.deleteById(id);
        } catch (ServiceException e) {
            throw e;
        }
    }
}
