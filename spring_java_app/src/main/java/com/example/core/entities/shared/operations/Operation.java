package com.example.core.entities.shared.operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.BuildException;
import com.example.shared.exceptions.GeneralDateTimeException;

public abstract class Operation {

    protected int ref;
    protected String description;
    protected LocalDateTime startDate, finishDate;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss");

    protected Operation() {

    }

    public void checkData(int id, String startDate, String description) throws BuildException, GeneralDateTimeException {
        if (this.setRef(id) == -1) {
            throw new BuildException("Bad id");
        }
        setStartDate(startDate);
        this.description = description;
    }

    public int getRef() {
        return ref;
    }

    public int setRef(int ref) {
        if (ref < 0) {
            return -1;
        }
        this.ref = ref;
        return 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() throws GeneralDateTimeException {
        return Check.convertDateTimeToString(this.startDate, formatter);
    }

    public void setStartDate(String startDate) throws GeneralDateTimeException {
        if (startDate == null) {
            throw new GeneralDateTimeException("Bad start date");
        }
        this.startDate = Check.convertStringToDateTime(startDate, this.formatter);
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) throws GeneralDateTimeException {
        if (finishDate == null) {
            throw new GeneralDateTimeException("Bad finish date");
        }
        this.finishDate = Check.convertStringToDateTime(finishDate, this.formatter);
    }

}
