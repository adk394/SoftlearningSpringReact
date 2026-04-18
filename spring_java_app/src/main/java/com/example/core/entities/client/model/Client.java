package com.example.core.entities.client.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.core.entities.shared.stakeholder.Person;
import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.BuildException;

public class Client extends Person {

    protected int idClient;
    protected LocalDateTime registrationDate;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");

    protected Client() {
    }

    public static Client getInstance(String idPerson, String email, String phone, String adress,
            String namePerson, int idClient, String registrationDate) throws BuildException {
        Client c = new Client();
        String message = c.PersonDataValidation(idPerson, email, phone, adress, namePerson);

        if (c.setIdClient(idClient) != 0) {
            message += "id cliente incorrecto. ";
        }

        if (c.setRegistrationDate(registrationDate) != 0) {
            message += "fecha de registro incorrecta. ";
        }

        if (!message.isEmpty()) {
            throw new BuildException(message);
        }
        return c;
    }

    public int getIdClient() {
        return idClient;
    }

    public int setIdClient(int idClient) {
        if (Check.isValidNumber(idClient, 1000)) {
            this.idClient = idClient;
            return 0;
        }
        return -1;
    }

    public String getRegistrationDate() {
        return this.registrationDate.format(formatter);
    }

    public int setRegistrationDate(String registrationDate) {
        try {
            this.registrationDate = LocalDateTime.parse(registrationDate, formatter);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Client [idClient=" + idClient + ", registrationDate=" + getRegistrationDate()
                + ", " + super.toString() + "]";
    }
}
