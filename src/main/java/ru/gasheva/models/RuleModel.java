package ru.gasheva.models;

import ru.gasheva.models.classes.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleModel  implements ModelInterface{
    List<Rule> rules;
    public RuleModel() {
        rules = new ArrayList<>();
    }


    public void add(Rule rule) {
        rules.add(rule);
    }
    public void add(int index, Rule rule) {
        rules.add(index, rule);
    }
    public void setRule(int index, Rule newRule) {rules.set(index, newRule);}
    public void remove(String name) {rules.remove(getRule(name));}
    public Rule getRule(String name){return rules.stream().filter(x->x.getName().equals(name)).findAny().get();}
    public Rule getRule(int index){return rules.get(index);}

    @Override
    public int size() {
        return rules.size();
    }

    @Override
    public String[] getValuesForTable(int index) {
        Rule r = getRule(index);
        return new String[]{r.getName(), r.getRuleToString()};
    }

    public void swapRules(int from, int to){
        if (from==to) return;
        Rule r = rules.get(from);
        rules.remove(from);
        rules.add(to, r);
    }
}
