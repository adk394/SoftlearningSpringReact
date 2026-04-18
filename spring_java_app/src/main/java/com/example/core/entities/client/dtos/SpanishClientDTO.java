package com.example.core.entities.client.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Cliente")
public class SpanishClientDTO {
    private int id;
    private String idPerson;
    private String email;
    private String phone;
    private String adress;
    private String namePerson;
    private String registrationDate;

    public SpanishClientDTO() {
    }

    public SpanishClientDTO(int id, String idPerson, String email, String phone, String adress, 
                            String namePerson, String registrationDate) {
        this.id = id;
        this.idPerson = idPerson;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.namePerson = namePerson;
        this.registrationDate = registrationDate;
    }

    @JsonGetter("id_cliente")
    public int getId() {
        return id;
    }

    @JsonSetter("id_cliente")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("dni")
    public String getIdPerson() {
        return idPerson;
    }

    @JsonSetter("dni")
    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    @JsonGetter("correo_electronico")
    public String getEmail() {
        return email;
    }

    @JsonSetter("correo_electronico")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonGetter("telefono")
    public String getPhone() {
        return phone;
    }

    @JsonSetter("telefono")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonGetter("direccion")
    public String getAdress() {
        return adress;
    }

    @JsonSetter("direccion")
    public void setAdress(String adress) {
        this.adress = adress;
    }

    @JsonGetter("nombre")
    public String getNamePerson() {
        return namePerson;
    }

    @JsonSetter("nombre")
    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    @JsonGetter("fecha_alta")
    public String getRegistrationDate() {
        return registrationDate;
    }

    @JsonSetter("fecha_alta")
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "SpanishClientDTO [id=" + id + ", idPerson=" + idPerson + ", email=" + email 
                + ", phone=" + phone + ", adress=" + adress + ", namePerson=" + namePerson 
                + ", registrationDate=" + registrationDate + "]";
    }
}
