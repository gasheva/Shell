package ru.gasheva.models;

import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkingMemory {
    Map<Variable, DomainValue> curVariableValues = new HashMap<Variable, DomainValue>();
    List<Rule> usingRules;

    public WorkingMemory() {
        usingRules = new LinkedList<>();
    }

    public void add(Variable var, DomainValue dv){
        curVariableValues.put(var, dv);
    }
    public boolean hasValue(Variable var){
        if (var == null) return false;
        System.out.println("Has "+var.getName()+ "got value = " + curVariableValues.containsKey(var));
        return curVariableValues.containsKey(var);
    }
    public void remove(Variable var){
        curVariableValues.remove(var);
    }
    public DomainValue get(Variable var){
        return curVariableValues.get(var);
    }

    public void add(Rule curRule) {
        usingRules.add(curRule);
    }
}
