package ru.gasheva.addrule;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;

public class AddRuleControl extends ManagerRuleAbstractClass {

    public AddRuleControl(VariableModel variableModel, DomainModel domainModel, RuleModel ruleModel) {
        super(variableModel, domainModel, ruleModel);
        view.createView();
    }

    @Override
    protected boolean isRuleValid(Rule rule) {
        return !ruleModel.isRuleExisting(rule);
    }
}
