package ru.gasheva.models.classes;

import ru.gasheva.addrule.addfact.ManagerFactAbstractClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Rule {
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

    public List<Fact> getConditions() {
        return conditions;
    }

    public List<Fact> getConclutions() {
        return conclutions;
    }

    //endregion

    public void addCondition(Fact fact){
        conditions.add(fact);
    }
    public void addCondition(int index, Fact fact){
        conditions.add(index, fact);
    }
    public Fact getCondition(int index){return conditions.get(index);}
    public int conditionsSize(){return conditions.size();}

    public void addConclusion(Fact fact){
        conclutions.add(fact);
    }
    public void addConclusion(int index, Fact fact){
        conclutions.add(index, fact);
    }
    public Fact getConclusion(int index){return conclutions.get(index);}
    public int conclusionsSize(){return conclutions.size();}

    //@Override
//    public boolean equals(Object obj) {
//        if(((Rule)obj).getName()==null) return false;
//        return ((Rule)obj).getName().equals(this.getName());
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return name.equals(rule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
        conclutions.set(id, newFact);
    }

//    public void setCondition(int id, Fact newFact)
//    {
//        conditions.set(id, newFact);
//    }

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
    public void removeConclusion(int id)
    {
        deleteFact(id, conclutions);
    }

    public boolean hasFactInConclusion(Variable target) {
        return conclutions.get(0).getVariable().equals(target);
    }

    public static void copy(Rule from, Rule to){
        to.conclutions = from.conclutions;
        to.conditions = from.conditions;
        to.explanation = from.explanation;
        to.name = from.name;
        to.order = from.order;
    }

    public void unsubscribeFromAll() {
        conclutions.get(0).getVariable().unsubscribe(this);
        conditions.forEach(x->x.getVariable().unsubscribe(this));
    }
    public void subscribeToAll(){
        conclutions.get(0).getVariable().subscribe(this);
        conditions.forEach(x->x.getVariable().subscribe(this));
    }

    public Rule clone(){
        Rule newRule = new Rule();
        newRule.name = this.name;
        newRule.order = this.order;
        newRule.explanation = this.explanation;
        this.conditions.forEach(x->newRule.addCondition(x.clone()));
        this.conclutions.forEach(x->newRule.addConclusion(x.clone()));
        return newRule;
    }
}
