package com.example.core.entities.shared.stakeholder;

import com.example.core.entities.shared.validations.Check;

public abstract class Person {

    protected String idPerson, email, phone, adress, namePerson;

    protected Person() {
    }

  
    protected String PersonDataValidation(String idPerson, String email, String phone, String adress, String namePerson) {
        String errorMessage = "";
        if (setIdPerson(idPerson) != 0) {
            errorMessage += "ID incorrecto. ";
        }
        if (setEmail(email) != 0) {
            errorMessage += "Email incorrecto. ";
        }
        if (setPhone(phone) != 0) {
            errorMessage += "Telefono incorrecto. ";
        }
        if (setAdress(adress) != 0) {
            errorMessage += "Direccion incorrecta. ";
        }
        if (setNamePerson(namePerson) != 0) {
            errorMessage += "Nombre incorrecto. ";
        }

        return errorMessage;

    }

    public String getIdPerson() {
        return idPerson;
    }

    public int setIdPerson(String idPerson) {
        if (Check.minStringChars(idPerson, 8)) {
            this.idPerson = idPerson;
            return 0;
        }
        return -1;
    }

    public String getEmail() {
        return email;
    }

    public int setEmail(String email) {
        try {
            Check.email(email);
            this.email = email;
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String getPhone() {
        return phone;
    }

    public int setPhone(String phone) {
        if (Check.minStringChars(phone, 9)) {
            this.phone = phone;
            return 0;
        }
        return -1;
    }

    public String getAdress() {
        return adress;
    }

    public int setAdress(String adress) {
        if (Check.minStringChars(adress, 10)) {
            this.adress = adress;
            return 0;
        }
        return -1;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public int setNamePerson(String namePerson) {
        if (Check.minStringChars(namePerson, 3)) {
            this.namePerson = namePerson;
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Person [getIdPerson()=" + getIdPerson() + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone()
                + ", getAdress()=" + getAdress() + ", getNamePerson()=" + getNamePerson() + "]";
    }

    public String getContactData() {
        return this.getIdPerson() + "|" + this.getEmail() + "|" + this.getPhone();
    }
}
