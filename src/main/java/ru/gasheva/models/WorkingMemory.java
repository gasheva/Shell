package ru.gasheva.models;

import javafx.util.Pair;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import java.util.*;

public class WorkingMemory {
    Map<Variable, DomainValue> curVariableValues = new HashMap<Variable, DomainValue>();
    List<Pair<Rule, Integer>> usingRules;

    public WorkingMemory() {
        usingRules = new LinkedList<>();
    }

    public List<Pair<Rule, Integer>> getUsingRules() {
        return usingRules;
    }

    public void add(Variable var, DomainValue dv){
        curVariableValues.put(var, dv);
    }
    public boolean hasValue(Variable var){
        if (var == null) return false;
        //System.out.println("Has "+var.getName()+ "got value = " + curVariableValues.containsKey(var));
        //return curVariableValues.containsKey(var);
        return curVariableValues.keySet().stream().anyMatch(x->x.getName().equals(var.getName()));
    }
    public void remove(Variable var){
        curVariableValues.remove(var);
    }
    public DomainValue get(Variable var){
        //Optional res = curVariableValues..stream().filter(x->x.getName().equals(var.getName())).findAny();
        //return res.get().
        return curVariableValues.get(var);
    }

    public void add(Rule curRule, int parentIndex) {
        usingRules.add(new Pair<Rule, Integer>(curRule, parentIndex));
    }

    public List<Fact> getAllVariables() {
        List<Fact> facts = new LinkedList<>();
        curVariableValues.forEach((x, y)-> facts.add(new Fact(x, y)));
        return facts;
    }
    public Rule getUsingRule(int index){
        return usingRules.get(index).getKey();
    }
    public int getParentIndex(int index){
        return usingRules.get(index).getValue();
    }
}
