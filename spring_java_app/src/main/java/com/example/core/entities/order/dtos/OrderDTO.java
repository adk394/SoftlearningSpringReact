package com.example.core.entities.order.dtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "orders")
public class OrderDTO {

    @Id
    @Column(name = "order_id")
    private int orderID;

    @Column(name = "client_id")
    private int clientID;

    @Column(name = "receiver_address")
    private String recieverAddress;

    @Column(name = "receiver_person")
    private String recieverPerson;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "phone_contact", columnDefinition = "TEXT")
    private String phoneContact;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "description")
    private String description;

    @Column(name = "package_dimensions")
    private String packageDimensionsCsv;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    // necesario para serializar en xml (sin serializar los detalles en xml no se puede serializar el pedido tampoco)
    @XmlElement(name = "shopCart")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "shopCart")
    private List<OrderDetailDTO> shopCart = new ArrayList<>();

    public void addDetail(OrderDetailDTO detail) {
        if (detail == null) {
            return;
        }
        this.shopCart.add(detail);
        detail.setOrder(this);
    }

    public void removeDetail(OrderDetailDTO detail) {
        if (detail == null) {
            return;
        }
        this.shopCart.remove(detail);
        detail.setOrder(null);
    }

    public OrderDTO() {
    }

    public OrderDTO(int orderID, int clientID, String recieverAddress, String recieverPerson,
            String paymentDate, String deliveryDate, String phoneContact, String status,
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

    // getters
    public int getOrderID() {
        return orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public String getRecieverAddress() {
        return recieverAddress;
    }

    public String getRecieverPerson() {
        return recieverPerson;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public String getStatus() {
        return status;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPackageDimensionsCsv() {
        return packageDimensionsCsv;
    }

    public List<OrderDetailDTO> getShopCart() {
        return shopCart;
    }

    // setters
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setRecieverAddress(String recieverAddress) {
        this.recieverAddress = recieverAddress;
    }

    public void setRecieverPerson(String recieverPerson) {
        this.recieverPerson = recieverPerson;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPackageDimensionsCsv(String packageDimensionsCsv) {
        this.packageDimensionsCsv = packageDimensionsCsv;
    }

    public void setShopCart(List<OrderDetailDTO> shopCart) {
        this.shopCart = new ArrayList<>();
        if (shopCart != null) {
            for (OrderDetailDTO detail : shopCart) {
                this.addDetail(detail);
            }
        }
    }

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

    @Override
    public String toString() {
        return "OrderDTO [orderID=" + orderID + ", clientID=" + clientID + ", recieverAddress=" + recieverAddress
                + ", recieverPerson=" + recieverPerson + ", status=" + status + "]";
    }
}
