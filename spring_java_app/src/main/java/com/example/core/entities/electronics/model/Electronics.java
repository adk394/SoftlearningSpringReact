package com.example.core.entities.electronics.model;

import com.example.core.entities.shared.products.Marketable;
import com.example.core.entities.shared.products.Product;
import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.BuildException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Electronics - Producto electronico que hereda de Product
 * Usa patron Factory con getInstance() para validar datos antes de crear
 */
public class Electronics extends Product implements Marketable {

    // Formato de fecha: dd/MM/yyyy HH:mm:ss
    public static final DateTimeFormatter FORMATO_DE_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Campos especificos
    protected String brand;                    // String
    protected int warrantyMonths;              // int
    protected float weightKg;                  // float
    protected double powerConsumptionWatts;    // double
    protected LocalDateTime manufacturedDateTime;  // LocalDateTime
    protected String[] features;               // Array

    // Constructor protegido - usar getInstance()
    protected Electronics() {
        super();
    }

    /**
     * Factory method - Crea instancia validada de Electronics
     * Lanza BuildException si los datos son invalidos
     */
    public static Electronics getInstance(
            String idProduct, String name, String description, double price, int stock, boolean isAvailable,
            String brand, int warrantyMonths, float weightKg, double powerConsumptionWatts,
            String manufacturedDateTimeStr, String[] features) throws BuildException {

        Electronics electronics = new Electronics();

        String error = electronics.validateElectronicsData(
                idProduct, name, description, price, stock,
                brand, warrantyMonths, weightKg, powerConsumptionWatts, 
                manufacturedDateTimeStr, features);

        if (!error.isEmpty()) {
            throw new BuildException("Error al crear Electronics: " + error);
        }

        electronics.setAvailable(isAvailable);
        return electronics;
    }

    // Valida todos los campos y retorna mensaje de error o vacio
    private String validateElectronicsData(
            String idProduct, String name, String description, double price, int stock,
            String brand, int warrantyMonths, float weightKg, double powerConsumptionWatts,
            String manufacturedDateTimeStr, String[] features) throws BuildException {

        String errorMessage = "";

        // Validacion datos heredados de Product
        errorMessage += productDataValidation(idProduct, name, description, price, stock);

        // Validacion campos propios
        if (setBrand(brand) != 0) errorMessage += "Marca incorrecta. ";
        if (setWarrantyMonths(warrantyMonths) != 0) errorMessage += "Garantia incorrecta. ";
        if (setWeightKg(weightKg) != 0) errorMessage += "Peso incorrecto. ";
        if (setPowerConsumptionWatts(powerConsumptionWatts) != 0) errorMessage += "Consumo incorrecto. ";

        // Parsear fecha
        if (manufacturedDateTimeStr == null || manufacturedDateTimeStr.isEmpty()) {
            errorMessage += "Fecha requerida. ";
        } else {
            try {
                this.manufacturedDateTime = LocalDateTime.parse(manufacturedDateTimeStr, FORMATO_DE_FECHA);
            } catch (DateTimeParseException e) {
                errorMessage += "Fecha invalida. Use: dd/MM/yyyy HH:mm:ss. ";
            }
        }

        // Validar array
        if (features != null && features.length > 0) {
            for (String feature : features) {
                if (feature == null || feature.trim().isEmpty()) {
                    errorMessage += "Caracteristica vacia. ";
                }
            }
            this.features = features;
        } else {
            this.features = new String[0];
        }

        return errorMessage;
    }

    // Getters y Setters con validacion
    public String getBrand() { return brand; }
    
    public int setBrand(String brand) {
        if (Check.minStringChars(brand, 2)) {
            this.brand = brand;
            return 0;
        }
        return -1;
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    
    public int setWarrantyMonths(int warrantyMonths) {
        if (Check.isValidNumber(warrantyMonths, 1)) {
            this.warrantyMonths = warrantyMonths;
            return 0;
        }
        return -1;
    }

    public float getWeightKg() { return weightKg; }
    
    public int setWeightKg(float weightKg) {
        if (weightKg > 0) {
            this.weightKg = weightKg;
            return 0;
        }
        return -1;
    }

    public double getPowerConsumptionWatts() { return powerConsumptionWatts; }
    
    public int setPowerConsumptionWatts(double powerConsumptionWatts) {
        if (powerConsumptionWatts >= 0) {
            this.powerConsumptionWatts = powerConsumptionWatts;
            return 0;
        }
        return -1;
    }

    public LocalDateTime getManufacturedDateTime() { return manufacturedDateTime; }
    public String[] getFeatures() { return features; }

    // Implementacion de Marketable
    @Override
    public boolean isAvailable() { return this.isAvailable; }

    @Override
    public double getPrice() { return this.price; }

    // Helper: array a string
    public String featuresToString() {
        if (features == null || features.length == 0) return "Sin caracteristicas";
        return String.join(" | ", features);
    }

    // Helper: formatear fecha
    public String getFormattedManufacturedDate() {
        if (manufacturedDateTime == null) return "No disponible";
        return manufacturedDateTime.format(FORMATO_DE_FECHA);
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", warrantyMonths=" + warrantyMonths +
                ", weightKg=" + weightKg +
                ", powerConsumptionWatts=" + powerConsumptionWatts +
                ", fecha=" + getFormattedManufacturedDate() +
                ", features=[" + featuresToString() + "]" +
                '}';
    }
}