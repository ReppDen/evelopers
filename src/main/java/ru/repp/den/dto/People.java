package ru.repp.den.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


public class People implements Serializable {
    @JsonProperty
    String name;
    @JsonProperty
    LocalDate birthDate;


    public People(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
