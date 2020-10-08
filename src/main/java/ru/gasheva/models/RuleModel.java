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


    public void add(int index, String[] values) {
        Rule newRule = new Rule(values[0]);
        newRule.setOrder(index);
        rules.add(newRule);
    }

    public void update(String id, String newValue) {
        //rules.set(id, ) имя, значение порядок
    }

    public void remove(String name) {
        Optional ruleToRemove = rules.stream().filter(x->x.getName()==name).findAny();
        rules.remove(ruleToRemove.get());   //todo - add ifPresent() проверку на null
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String[] getValuesForTable(int index) {
        return new String[0];
    }
}
