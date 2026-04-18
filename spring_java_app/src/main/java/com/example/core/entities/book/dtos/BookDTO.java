package com.example.core.entities.book.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "books")
public class BookDTO {

    // ID del libro
    @Id
    @Column(name = "ident")
    private int id;

    // Datos básicos del libro
    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "publisher")
    private String publisher;

    // ===== Datos físicos =====
    @Column(name = "weight")
    private double weight;

    @Column(name = "height")
    private double height;

    @Column(name = "width")
    private double width;

    @Column(name = "depth")
    private double depth;

    // constructor vacio (requerido por JPA)
    public BookDTO() {
    }

    // constructor 
    public BookDTO(String title, int id, double price, String author, String isbn,
                   String releaseDate, String publisher,
                   double weight, double height, double width, double depth) {

        this.title = title;
        this.id = id;
        this.price = price;
        this.author = author;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    // ===== GETTERS Y SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "BookDTO[title=" + title +
                ", id=" + id +
                ", price=" + price +
                ", author=" + author +
                ", isbn=" + isbn +
                ", releaseDate=" + releaseDate +
                ", publisher=" + publisher +
                ", weight=" + weight +
                ", height=" + height +
                ", width=" + width +
                ", depth=" + depth +
                "]";
    }
}
