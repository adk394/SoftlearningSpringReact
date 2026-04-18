package com.example.core.entities.client.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientDTO {
    @Id
    @Column(name = "id_client")
    private int id;
    @Column(name = "dni")
    private String idPerson;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "address")
    private String adress;
    @Column(name = "name")
    private String namePerson;
    @Column(name = "registration_date")
    private String registrationDate;

    public ClientDTO() {
    }

    public ClientDTO(int id, String idPerson, String email, String phone, String adress,
            String namePerson, String registrationDate) {
        this.id = id;
        this.idPerson = idPerson;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.namePerson = namePerson;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    // alias para compatibilidad
    public int getIdClient() {
        return id;
    }

    public void setIdClient(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClientDTO [id=" + id + ", idPerson=" + idPerson + ", email=" + email
                + ", phone=" + phone + ", adress=" + adress + ", namePerson=" + namePerson
                + ", registrationDate=" + registrationDate + "]";
    }
}
