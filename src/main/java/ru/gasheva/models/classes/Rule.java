package ru.gasheva.models.classes;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    //int id;
    private int order;
    private String name;
    private String explanation;
    private List<Fact> conditions = new ArrayList<>();
    private List<Fact> conclutions = new ArrayList<>();


    public Rule() { }
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

    public void addCondition(Fact fact){conditions.add(fact);}
    public void addCondition(int index, Fact fact){conditions.add(index, fact);}
    public Fact getCondition(int index){return conditions.get(index);}
    public int conditionsSize(){return conditions.size();}

    public void addConclusion(Fact fact){conclutions.add(fact);}
    public void addConclusion(int index, Fact fact){conclutions.add(index, fact);}
    public Fact getConclusion(int index){return conclutions.get(index);}
    public int conclusionsSize(){return conclutions.size();}

    @Override
    public boolean equals(Object obj) {
        return ((Rule)obj).getName().equals(this.getName());
    }

    public void swapFacts(int from, int to, List<Fact> facts){
        Fact f = facts.get(from);
        if (from==to) return;
        facts.remove(from);
        facts.add(to, f);
    }
    public void swapConditions(int from, int to) {
        swapFacts(from, to, conditions);
    }

    public void swapConclusions(int from, int to) {
        swapFacts(from, to, conclutions);
    }
}
