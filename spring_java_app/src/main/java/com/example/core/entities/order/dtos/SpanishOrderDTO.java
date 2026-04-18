package com.example.core.entities.order.dtos;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SpanishOrderDTO {

    private int orderID;
    private int clientID;
    private String recieverAddress;
    private String recieverPerson;
    private String paymentDate;
    private String deliveryDate;
    private Set<String> phoneContact;
    private String status;
    private String startDate;
    private String description;
    private String packageDimensionsCsv;
    private List<OrderDetailDTO> shopCart;

    public SpanishOrderDTO(int orderID, int clientID, String recieverAddress, String recieverPerson,
            String paymentDate, String deliveryDate, Set<String> phoneContact, String status,
            String startDate, String description, String packageDimensionsCsv, List<OrderDetailDTO> shopCart) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.recieverAddress = recieverAddress;
        this.recieverPerson = recieverPerson;
        this.paymentDate = paymentDate;
        this.deliveryDate = deliveryDate;
        this.phoneContact = phoneContact;
        this.status = status;
        this.startDate = startDate;
        this.description = description;
        this.packageDimensionsCsv = packageDimensionsCsv;
        this.shopCart = shopCart;
    }

    public SpanishOrderDTO() {
    }

    @JsonGetter("id_pedido")
    public int getOrderID() {
        return orderID;
    }

    @JsonGetter("id_cliente")
    public int getClientID() {
        return clientID;
    }

    @JsonGetter("direccion_receptor")
    public String getRecieverAddress() {
        return recieverAddress;
    }

    @JsonGetter("persona_receptor")
    public String getRecieverPerson() {
        return recieverPerson;
    }

    @JsonGetter("fecha_pago")
    public String getPaymentDate() {
        return paymentDate;
    }

    @JsonGetter("fecha_entrega")
    public String getDeliveryDate() {
        return deliveryDate;
    }

    @JsonGetter("contacto_telefonico")
    public Set<String> getPhoneContact() {
        return phoneContact;
    }

    @JsonGetter("estado")
    public String getStatus() {
        return status;
    }

    @JsonGetter("fecha_inicio")
    public String getStartDate() {
        return startDate;
    }

    @JsonGetter("descripcion")
    public String getDescription() {
        return description;
    }

    @JsonGetter("dimensiones_paquete_csv")
    public String getPackageDimensionsCsv() {
        return packageDimensionsCsv;
    }

    @JsonGetter("carrito_compras")
    public List<OrderDetailDTO> getShopCart() {
        return shopCart;
    }

    @JsonIgnore
    public double getTotalPrice() {
        double totalPrice = 0;
        if (this.shopCart == null) {
            return 0;
        }
        for (OrderDetailDTO detail : this.shopCart) {
            totalPrice += (detail.getPrice() - detail.getDiscount()) * detail.getAmount();
        }
        return totalPrice;
    }

    @JsonSetter("id_pedido")
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @JsonSetter("id_cliente")
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @JsonSetter("direccion_receptor")
    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

    @JsonSetter("persona_receptor")
    public void setRecieverPerson(String recieverPerson) {
        this.recieverPerson = recieverPerson;
    }

    @JsonSetter("fecha_pago")
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @JsonSetter("fecha_entrega")
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @JsonSetter("contacto_telefonico")
    public void setPhoneContact(Set<String> phoneContact) {
        this.phoneContact = phoneContact;
    }

    @JsonSetter("estado")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonSetter("fecha_inicio")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonSetter("descripcion")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonSetter("dimensiones_paquete_csv")
    public void setPackageDimensionsCsv(String packageDimensionsCsv) {
        this.packageDimensionsCsv = packageDimensionsCsv;
    }

    @JsonSetter("carrito_compras")
    public void setShopCart(List<OrderDetailDTO> shopCart) {
        this.shopCart = shopCart;
    }

    @Override
    public String toString() {
        return "OrderDTO [orderID=" + orderID + ", clientID=" + clientID + ", recieverAddress=" + recieverAddress
                + ", recieverPerson=" + recieverPerson + ", paymentDate=" + paymentDate + ", deliveryDate="
                + deliveryDate + ", phoneContact=" + phoneContact + ", status=" + status + ", startDate=" + startDate
                + ", description=" + description + ", packageDimensionsCsv=" + packageDimensionsCsv + ", shopCart="
                + shopCart + "]";
    }

}
