package ru.gasheva.models;

import ru.gasheva.models.classes.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuleModel  implements ModelInterface{
    List<Rule> rules;
    public RuleModel() {
        rules = new ArrayList<>();
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void add(Rule rule) {
        rule.subscribeToAll();
        rules.add(rule);
    }
    public void add(int index, Rule rule) {
        rule.subscribeToAll();
        rules.add(index, rule);
    }
    public void setRule(int index, Rule newRule) {rules.set(index, newRule);}
    public void setRule(Rule oldRule, Rule newRule) {rules.set(rules.indexOf(oldRule), newRule);}
    public void remove(String name) {
        Rule r = getRule(name);
        r.unsubscribeFromAll();
        rules.remove(r);
    }
    public Rule getRule(String name){
        return rules.stream().filter(x->x.getName().equals(name)).findAny().get();
    }
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

    public boolean isRuleExisting(Rule rule) {return rules.stream().anyMatch(x->x.equals(rule));}

    public int ruleCount(Rule rule) {return (int)rules.stream().filter(x->x.equals(rule)).count();}

    public void clear() {
        rules.clear();
    }

    public boolean isEmpy() {
        return rules.isEmpty();
    }
}
