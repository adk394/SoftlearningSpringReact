package com.example.core.entities.order.dtos;

import java.util.List;

public class OrderPublicDTO {

    private int orderID;
    private int clientID;
    private String recieverAddress;
    private String recieverPerson;
    private String phoneContact;
    private String startDate;
    private String description;
    private String packageDimensionsCsv;
    private List<OrderDetailDTO> shopCart;

    public OrderPublicDTO() {
    }

    public OrderPublicDTO(int orderID, int clientID, String recieverAddress, String recieverPerson,
            String phoneContact, String startDate, String description,
            String packageDimensionsCsv, List<OrderDetailDTO> shopCart) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.recieverAddress = recieverAddress;
        this.recieverPerson = recieverPerson;
        this.phoneContact = phoneContact;
        this.startDate = startDate;
        this.description = description;
        this.packageDimensionsCsv = packageDimensionsCsv;
        this.shopCart = shopCart;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getRecieverAddress() {
        return recieverAddress;
    }

    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

    public String getRecieverPerson() {
        return recieverPerson;
    }

    public void setRecieverPerson(String recieverPerson) {
        this.recieverPerson = recieverPerson;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageDimensionsCsv() {
        return packageDimensionsCsv;
    }

    public void setPackageDimensionsCsv(String packageDimensionsCsv) {
        this.packageDimensionsCsv = packageDimensionsCsv;
    }

    public List<OrderDetailDTO> getShopCart() {
        return shopCart;
    }

    public void setShopCart(List<OrderDetailDTO> shopCart) {
        this.shopCart = shopCart;
    }
}
