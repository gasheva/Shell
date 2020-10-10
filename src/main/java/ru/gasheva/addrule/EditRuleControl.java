package ru.gasheva.addrule;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;

public class EditRuleControl extends ManagerRuleAbstractClass{
    Rule oldRule;
    public EditRuleControl(VariableModel variableModel, DomainModel domainModel, RuleModel ruleModel, Rule oldRule) {
        super(variableModel, domainModel, ruleModel);
        this.oldRule = oldRule;
        for(int i=0; i<oldRule.conditionsSize(); i++)
            newRule.addCondition(oldRule.getCondition(i));
        for(int i=0; i<oldRule.conclusionsSize(); i++)
            newRule.addConclusion(oldRule.getConclusion(i));
        view.createView(oldRule);
    }

    @Override
    protected boolean isRuleValid(Rule rule) {
        //если имя совпадает, то в хранилище уже есть домен с этим именем
        if (rule.equals(oldRule)){
            return ruleModel.ruleCount(rule)==1;
        }
        else{
            return !ruleModel.isRuleExisting(rule);
        }
    }
}
