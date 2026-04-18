package com.example.core.entities.shared.products;

import com.example.core.entities.shared.validations.Check;

public abstract class Product {

    protected String idProduct, name, description;
    protected double price;
    protected int stock;
    protected boolean isAvailable;

    protected Product() {
    }

    protected String productDataValidation(String idProduct, String name, String description, double price, int stock) {
        String errorMessage = "";
        if (setIdProduct(idProduct) != 0) {
            errorMessage += "ID incorrecto. ";
        }
        if (setName(name) != 0) {
            errorMessage += "Nombre incorrecto. ";
        }
        if (setDescription(description) != 0) {
            errorMessage += "Descripcion incorrecta. ";
        }
        if (setPrice(price) != 0) {
            errorMessage += "Precio incorrecto. ";
        }
        if (setStock(stock) != 0) {
            errorMessage += "Stock incorrecto. ";
        }

        return errorMessage;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public int setIdProduct(String idProduct) {
        if (Check.minStringChars(idProduct, 4)) {
            this.idProduct = idProduct;
            return 0;
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public int setName(String name) {
        if (Check.minStringChars(name, 3)) {
            this.name = name;
            return 0;
        }
        return -1;
    }

    public String getDescription() {
        return description;
    }

    public int setDescription(String description) {
        if (Check.minStringChars(description, 10)) {
            this.description = description;
            return 0;
        }
        return -1;
    }

    public double getPrice() {
        return price;
    }

    public int setPrice(double price) {
        if (Check.isValidNumber(price, 0)) {
            this.price = price;
            return 0;
        }
        return -1;
    }

    public int getStock() {
        return stock;
    }

    public int setStock(int stock) {
        if (Check.isValidNumber(stock, 0)) {
            this.stock = stock;
            return 0;
        }
        return -1;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
