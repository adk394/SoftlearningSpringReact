package com.example.core.entities.order.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.core.entities.shared.operations.Operation;
import com.example.core.entities.shared.physicals.PhysicalData;
import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.BuildException;
import com.example.shared.exceptions.GeneralDateTimeException;

public class Order extends Operation {

    protected int orderID, clientID;
    protected String recieverAddress, recieverPerson;
    protected LocalDateTime paymentDate, deliveryDate;
    protected Set<String> phoneContact;
    public PhysicalData orderPackage = null;
    public ArrayList<OrderDetail> shopCart;
    protected OrderStatus status;

    protected Order() {
    }

    // constructor factory simple
    public static Order getInstance(int orderID, int clientID, String startDate, String description) throws BuildException, GeneralDateTimeException {
        Order o = new Order();
        String message = "";

        try {
            o.checkData(orderID, startDate, description);
        } catch (BuildException ex) {
            message += ex.getMessage() + ". ";
        }

        if (!message.isEmpty()) {
            throw new BuildException(message);
        }

        o.orderID = orderID;
        o.clientID = clientID;
        o.phoneContact = new HashSet<>();
        o.shopCart = new ArrayList<>();
        o.status = OrderStatus.CREATED;

        return o;
    }

    // constructor factory completo
    public static Order getInstance(int orderID, int clientID, String startDate, String description, 
                                     String address, String name, String phone, String paymentDate, 
                                     String deliveryDate, String packageInfo, String shopCartDetails) throws BuildException, GeneralDateTimeException {
        Order o = getInstance(orderID, clientID, startDate, description);
        String message = "";

        if (o.setRecieverAddress(address) != 0) {
            message += "direccion del receptor incorrecta. ";
        }

        if (o.setRecieverPerson(name) != 0) {
            message += "nombre del receptor incorrecto. ";
        }

        if (o.setPhoneContacts(phone) != 0) {
            message += "telefonos incorrectos. ";
        }

        if (o.setPaymentDate(paymentDate) != 0) {
            message += "fecha de pago incorrecta. ";
        }

        if (deliveryDate != null && o.setDeliveryDate(deliveryDate) != 0) {
            message += "fecha de entrega incorrecta. ";
        }

        if (packageInfo != null && o.setDimensions(packageInfo) != 0) {
            message += "dimensiones del paquete incorrectas. ";
        }

        if (shopCartDetails != null && o.setShopCartDetails(shopCartDetails) != 0) {
            message += "carrito de compras incorrecto. ";
        }

        if (!message.isEmpty()) {
            throw new BuildException(message);
        }

        return o;
    }

    // setters que devuelven 0 si ok, -1 si falla (estilo Person)
    public int setRecieverAddress(String recieverAddress) {
        if (recieverAddress == null || recieverAddress.trim().isEmpty()) {
            return -1;
        }
        this.recieverAddress = recieverAddress;
        return 0;
    }

    public int setRecieverPerson(String recieverPerson) {
        if (recieverPerson == null || recieverPerson.trim().isEmpty()) {
            return -1;
        }
        this.recieverPerson = recieverPerson;
        return 0;
    }

    public int setPhoneContacts(String phones) {
        if (phones == null || phones.trim().isEmpty()) {
            // si no hay telefons, se considera correcto!!!
            return 0;
        }
        try {
            String[] phoneArray = phones.split(",");
            for (String phone : phoneArray) {
                if (phone != null && !phone.trim().isEmpty()) {
                    this.phoneContact.add(phone.trim());
                }
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public int setPaymentDate(String paymentDate) {
        try {
            this.paymentDate = Check.convertStringToDateTime(paymentDate, this.formatter);
            this.status = OrderStatus.CONFIRMED;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public int setDeliveryDate(String deliveryDate) {
        try {
            if (this.status == OrderStatus.CONFIRMED) {
                this.status = OrderStatus.FORTHCOMING;
            }
            this.deliveryDate = Check.convertStringToDateTime(deliveryDate, this.formatter);
            this.status = OrderStatus.DELIVERED;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public int setDimensions(String csvDimensions) {
        try {
            String[] parts = csvDimensions.split(",");
            if (parts.length != 4) {
                return -1;
            }
            double weight = Double.parseDouble(parts[0].trim());
            double height = Double.parseDouble(parts[1].trim());
            double width = Double.parseDouble(parts[2].trim());
            double depth = Double.parseDouble(parts[3].trim());

            PhysicalData pd = PhysicalData.getInstance(weight, height, width, depth);
            this.orderPackage = pd;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public int setShopCartDetails(String shopCartDetails) {
        if (shopCartDetails == null || shopCartDetails.trim().isEmpty()) {
            return -1;
        }
        try {
            String[] detailLines = shopCartDetails.split(";");
            for (String line : detailLines) {
                if (line == null) {
                    continue;
                }
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split(",");
                if (parts.length != 4) {
                    return -1;
                }

                String id = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());
                double discount = Double.parseDouble(parts[2].trim());
                int amount = Integer.parseInt(parts[3].trim());

                this.shopCart.add(new OrderDetail(id, price, discount, amount));
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    // getters basicos
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
        if (this.paymentDate == null) {
            return null;
        }
        return this.paymentDate.format(this.formatter);
    }

    public String getDeliveryDate() {
        if (this.deliveryDate == null) {
            return null;
        }
        return this.deliveryDate.format(this.formatter);
    }

    public Set<String> getPhoneContacts() {
        return new HashSet<>(this.phoneContact);
    }

    public ArrayList<OrderDetail> getShopCartList() {
        return new ArrayList<>(this.shopCart);
    }

    public String getStatus() {
        return status.toString();
    }

    public String getPackageDimensionsCsv() {
        if (this.orderPackage == null) {
            return null;
        }
        return this.orderPackage.getWeight() + "," + this.orderPackage.getHeight() + "," 
                + this.orderPackage.getWidth() + "," + this.orderPackage.getDepth();
    }

    public String getStartDateSafe() {
        try {
            return super.getStartDate();
        } catch (Exception e) {
            return null;
        }
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "Order [orderID=" + orderID + ", clientID=" + clientID + ", recieverAddress=" + recieverAddress 
                + ", status=" + status + "]";
    }
}
