package ru.gasheva.models;

import javafx.util.Pair;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import java.util.*;

public class WorkingMemory {
    Map<Variable, DomainValue> curVariableValues = new HashMap<Variable, DomainValue>();
    List<Pair<Rule, String>> usingRules;

    public WorkingMemory() {
        usingRules = new LinkedList<>();
    }

    public List<Pair<Rule, String>> getUsingRules() {
        return usingRules;
    }

    public void add(Variable var, DomainValue dv){
        curVariableValues.put(var, dv);
    }
    public boolean hasValue(Variable var){
        if (var == null) return false;
        //System.out.println("Has "+var.getName()+ "got value = " + curVariableValues.containsKey(var));
        return curVariableValues.containsKey(var);
    }
    public void remove(Variable var){
        curVariableValues.remove(var);
    }
    public DomainValue get(Variable var){
        return curVariableValues.get(var);
    }

    public void add(Rule curRule, String parentName) {
        usingRules.add(new Pair<Rule, String>(curRule, parentName));
    }

    public List<Fact> getAllVariables() {
        List<Fact> facts = new LinkedList<>();
        curVariableValues.forEach((x, y)-> facts.add(new Fact(x, y)));
        return facts;
    }
    public Rule getUsingRule(int index){
        return usingRules.get(index).getKey();
    }
    public int getUsingRuleNumber(){return usingRules.size();}
    public String getParentIndex(int index){
        return usingRules.get(index).getValue();
    }
    public Rule getRule(String name){
        return usingRules.stream().filter(x->x.getKey().getName().equals(name)).findAny().get().getKey();
    }
}
