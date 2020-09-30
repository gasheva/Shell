package ru.gasheva.models.classes;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    //int id;
    int order;
    String name;
    String explanation;
    List<Fact> facts = new ArrayList<>();


    public Rule(String name) {
        this.name = name;
    }

    //region Getter\Setter
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
}
