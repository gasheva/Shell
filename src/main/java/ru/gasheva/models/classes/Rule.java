package ru.gasheva.models.classes;

import ru.gasheva.addrule.addfact.ManagerFactAbstractClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    //endregion

    public void addCondition(Fact fact){
        fact.getVariable().setUsed(true);
        conditions.add(fact);
    }
    public void addCondition(int index, Fact fact){
        fact.getVariable().setUsed(true);
        conditions.add(index, fact);
    }
    public Fact getCondition(int index){return conditions.get(index);}
    public int conditionsSize(){return conditions.size();}

    public void addConclusion(Fact fact){
        fact.getVariable().setUsed(true);
        conclutions.add(fact);
    }
    public void addConclusion(int index, Fact fact){
        fact.getVariable().setUsed(true);
        conclutions.add(index, fact);
    }
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

    public String getRuleToString() {
        final String[] rule = {"IF "};
        conditions.forEach(x-> rule[0] +=x.toString()+" AND ");
        rule[0] = rule[0].substring(0, rule[0].length()-4);
        rule[0]+="THEN ";
        conclutions.forEach(x-> rule[0] +=x.toString()+", ");
        rule[0] = rule[0].substring(0, rule[0].length()-2);
        rule[0] = rule[0].trim();
        return rule[0];
    }

    public void setConclusion(int id, Fact newFact) {
        conclutions.forEach(x->x.getVariable().setUsed(false));
        conclutions.set(id, newFact);
    }

    public void setCondition(int id, Fact newFact) {
        conditions.set(id, newFact);
    }

    public void removeCondition(int id) {
        deleteFact(id, conditions);
    }

    private void deleteFact(int id, List<Fact>facts){
        facts.remove(id);
        for(int i=id; i<facts.size(); i++){
            Fact f = facts.get(i);
            f.setId(i);
            facts.set(i, f);
        }
    }
    public void removeConclusion(int id) {
        deleteFact(id, conclutions);
    }

    public boolean hasFactInConclusion(Variable target) {
        return conclutions.get(0).getVariable().equals(target);
    }
}
