package ru.gasheva.models.classes;

public class DomainValue {
    //int id;
    String value;
    //int id_domain;


    public DomainValue() {
    }

    public DomainValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
