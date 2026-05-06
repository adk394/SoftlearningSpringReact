package com.example.core.entities.book.model;

import com.example.core.entities.shared.physicals.PhysicalData;
import com.example.core.entities.shared.products.Marketable;
import com.example.core.entities.shared.products.Product;
import com.example.core.entities.shared.products.Storable;
import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.BuildException;

/**
 * libro como producto almacenable y comercializable
 */
public class Book extends Product implements Storable, Marketable {

    // atributos
    protected String isbn, title, author, editorial;
    protected int yearPublished;

    // datos fisicos
    protected PhysicalData physicalData;

    public Book() {
    }

    /**
     * crear instancia
     */
    public static Book getInstance(String idProduct, String name, String description, double price, int stock,
            boolean isAvailable, String isbn, String title, String author, String editorial,
            int yearPublished, double weight, double height, double width, double depth) throws BuildException {

        Book book = new Book();

        // validar datos
        String error = book.validateBookData(idProduct, name, description, price, stock,
                isbn, title, author, editorial, yearPublished, weight, height, width, depth);

        if (!error.isEmpty()) {
            throw new BuildException(error);
        }

        book.setAvailable(isAvailable);

        return book;
    }

    /**
     * valida datos
     */
    private String validateBookData(String idProduct, String name, String description, double price, int stock,
            String isbn, String title, String author, String editorial, int yearPublished,
            double weight, double height, double width, double depth) throws BuildException {

        String errorMessage = "";

        errorMessage += productDataValidation(idProduct, name, description, price, stock);

        // validar campos propios
        if (setIsbn(isbn) != 0) errorMessage += "ISBN incorrecto. ";
        if (setTitle(title) != 0) errorMessage += "Titulo incorrecto. ";
        if (setAuthor(author) != 0) errorMessage += "Autor incorrecto. ";
        if (setEditorial(editorial) != 0) errorMessage += "Editorial incorrecta. ";
        if (setYearPublished(yearPublished) != 0) errorMessage += "Año de publicacion incorrecto. ";

        PhysicalData data = PhysicalData.getInstance(weight, height, width, depth);
        if (data == null) {
            errorMessage += "Datos fisicos incorrectos. ";
        } else {
            this.physicalData = data;
        }

        return errorMessage;
    }

    public String getIsbn() {
        return isbn;
    }

    public int setIsbn(String isbn) {
        if (Check.ISBN(isbn)) {
            this.isbn = isbn;
            return 0;
        }
        return -1;
    }

    public String getTitle() {
        return title;
    }

    public int setTitle(String title) {
        if (Check.minStringChars(title, 1)) {
            this.title = title;
            return 0;
        }
        return -1;
    }

    public String getAuthor() {
        return author;
    }

    public int setAuthor(String author) {
        if (Check.minStringChars(author, 1)) {
            this.author = author;
            return 0;
        }
        return -1;
    }

    public String getEditorial() {
        return editorial;
    }

    public int setEditorial(String editorial) {
        if (Check.minStringChars(editorial, 2)) {
            this.editorial = editorial;
            return 0;
        }
        return -1;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public int setYearPublished(int yearPublished) {
        if (Check.isValidNumber(yearPublished, 0)) {
            this.yearPublished = yearPublished;
            return 0;
        }
        return -1;
    }

    /**
     * disponibilidad
     */
    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    /**
     * volumen
     */
    @Override
    public double getVolume() {
        return physicalData.getVolume();
    }

    /**
     * area
     */
    @Override
    public double getArea() {
        return physicalData.getArea();
    }

    public PhysicalData getPhysicalData() {
        return physicalData;
    }
}
