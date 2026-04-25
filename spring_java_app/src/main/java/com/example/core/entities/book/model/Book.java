package com.example.core.entities.book.model;

import com.example.core.entities.shared.physicals.PhysicalData;
import com.example.core.entities.shared.products.Marketable;
import com.example.core.entities.shared.products.Product;
import com.example.core.entities.shared.products.Storable;
import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.BuildException;

/**
 * clase book
 * libro como producto almacenable y comercializable
 */
public class Book extends Product implements Storable, Marketable {

    // atributos del libro
    protected String isbn, title, author, editorial;
    protected int yearPublished;

    // datos fisicos (peso y dimensiones)
    protected PhysicalData physicalData;

    // constructor vacio
    public Book() {
    }

    /**
     * crear instancia y validar datos
     */
    public static Book getInstance(String idProduct, String name, String description, double price, int stock,
            boolean isAvailable, String isbn, String title, String author, String editorial,
            int yearPublished, double weight, double height, double width, double depth) throws BuildException {

        Book book = new Book();

        // validar datos del producto y del libro
        String error = book.validateBookData(idProduct, name, description, price, stock,
                isbn, title, author, editorial, yearPublished, weight, height, width, depth);

        // si hay errores se lanza exception
        if (!error.isEmpty()) {
            throw new BuildException(error);
        }

        // asignar disponibilidad recibida
        book.setAvailable(isAvailable);

        return book;
    }








    
    /**
     * valida datos y devuelve mensaje de error
     */
    private String validateBookData(String idProduct, String name, String description, double price, int stock,
            String isbn, String title, String author, String editorial, int yearPublished,
            double weight, double height, double width, double depth) throws BuildException {

        String errorMessage = "";

        // validar campos heredados de product
        errorMessage += productDataValidation(idProduct, name, description, price, stock);

        // validar campos propios del libro
        if (setIsbn(isbn) != 0) errorMessage += "ISBN incorrecto. ";
        if (setTitle(title) != 0) errorMessage += "Titulo incorrecto. ";
        if (setAuthor(author) != 0) errorMessage += "Autor incorrecto. ";
        if (setEditorial(editorial) != 0) errorMessage += "Editorial incorrecta. ";
        if (setYearPublished(yearPublished) != 0) errorMessage += "Año de publicacion incorrecto. ";

        // crear y validar datos fisicos
        PhysicalData data = PhysicalData.getInstance(weight, height, width, depth);
        if (data == null) {
            errorMessage += "Datos físicos incorrectos. ";
        } else {
            this.physicalData = data;
        }

        return errorMessage;
    }

    // getters y setters

    public String getIsbn() {
        return isbn;
    }

    // valida isbn antes de asignar
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

    // valida que el titulo tenga al menos 1 caracter
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

    // valida que el autor tenga al menos 1 caracter
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

    // valida que la editorial tenga al menos 2 caracteres
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

    // valida que el ano sea un numero valido
    public int setYearPublished(int yearPublished) {
        if (Check.isValidNumber(yearPublished, 0)) {
            this.yearPublished = yearPublished;
            return 0;
        }
        return -1;
    }

    /**
     * indica si esta disponible
     * implementacion de marketable
     */
    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    /**
     * devuelve volumen
     * implementacion de storable
     */
    @Override
    public double getVolume() {
        return physicalData.getVolume();
    }

    /**
     * devuelve area
     */
    @Override
    public double getArea() {
        return physicalData.getArea();
    }

    public PhysicalData getPhysicalData() {
        return physicalData;
    }
}
