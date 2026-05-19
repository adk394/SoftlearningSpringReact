package com.example.core.entities.electronics.dtos;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ElectronicsDTO - Entidad JPA para tabla 'electronics'
 * Almacena: String, int, float, double, LocalDateTime, Array (como String)
 */
@Entity
// Para crear la tabla manualmente ejecuta el script SQL proporcionado
// La tabla NO se crea automaticamente (schema se gestiona manualmente)
@Table(name = "electronics")
public class ElectronicsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_product", unique = true, nullable = false, length = 50)
    private String idProduct;           // String

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;               // String

    @Column(name = "warranty_months", nullable = false)
    private int warrantyMonths;         // int

    @Column(name = "weight_kg")
    private float weightKg;             // float

    @Column(name = "power_consumption_watts")
    private double powerConsumptionWatts;  // double

    @Column(name = "manufactured_datetime")
    private LocalDateTime manufacturedDateTime;  // LocalDateTime

    @Column(name = "features", length = 1000)
    private String features;            // Array almacenado como String separado por |

    // Formato de fecha: dd/MM/yyyy HH:mm:ss
    public static final DateTimeFormatter FORMATO_DE_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Constructor vacio requerido por JPA
    public ElectronicsDTO() {}

    // Constructor completo
    public ElectronicsDTO(String idProduct, String name, String description, 
                          double price, int stock, boolean isAvailable,
                          String brand, int warrantyMonths, float weightKg, 
                          double powerConsumptionWatts, LocalDateTime manufacturedDateTime, 
                          String features) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isAvailable = isAvailable;
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
        this.weightKg = weightKg;
        this.powerConsumptionWatts = powerConsumptionWatts;
        this.manufacturedDateTime = manufacturedDateTime;
        this.features = features;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIdProduct() { return idProduct; }
    public void setIdProduct(String idProduct) { this.idProduct = idProduct; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    public float getWeightKg() { return weightKg; }
    public void setWeightKg(float weightKg) { this.weightKg = weightKg; }

    public double getPowerConsumptionWatts() { return powerConsumptionWatts; }
    public void setPowerConsumptionWatts(double powerConsumptionWatts) { this.powerConsumptionWatts = powerConsumptionWatts; }

    public LocalDateTime getManufacturedDateTime() { return manufacturedDateTime; }
    public void setManufacturedDateTime(LocalDateTime manufacturedDateTime) { this.manufacturedDateTime = manufacturedDateTime; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    // Convierte String separado por | a array
    public String[] getFeaturesAsArray() {
        if (this.features == null || this.features.isEmpty()) return new String[0];
        return this.features.split("\\|");
    }

    // Convierte array a String separado por |
    public void setFeaturesFromArray(String[] featuresArray) {
        if (featuresArray == null || featuresArray.length == 0) {
            this.features = null;
            return;
        }
        this.features = String.join("|", featuresArray);
    }

    // Formatea fecha a String legible
    public String getFormattedManufacturedDate() {
        if (this.manufacturedDateTime == null) return "Fecha no disponible";
        return this.manufacturedDateTime.format(FORMATO_DE_FECHA);
    }

    // Parsea String y establece fecha
    public boolean parseAndSetManufacturedDate(String dateString) {
        try {
            this.manufacturedDateTime = LocalDateTime.parse(dateString, FORMATO_DE_FECHA);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ElectronicsDTO{id=" + id + ", idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' + ", price=" + price +
                ", brand='" + brand + '\'' + ", fecha=" + getFormattedManufacturedDate() + '}';
    }
}