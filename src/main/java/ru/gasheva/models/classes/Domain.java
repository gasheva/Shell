package ru.gasheva.models.classes;

import java.util.ArrayList;
import java.util.List;

public class Domain {
    //int id;
    String name;
    List<DomainValue> domainValues = new ArrayList<>();

    public Domain() {
    }

    public Domain(String name) {
        this.name = name;
    }

    //region Getter\Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
    public void add(DomainValue domainValue){
        domainValues.add(domainValue);
    }
    public void remove(DomainValue domainValue){
        domainValues.remove(domainValue);
    }

    @Override
    public boolean equals(Object obj) {
        return ((Domain)obj).getName().equals(this.getName());
    }
}
