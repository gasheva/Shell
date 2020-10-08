package ru.gasheva.addrule;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;

public class AddRuleControl extends ManagerRuleAbstractClass {

    public AddRuleControl(VariableModel variableModel, DomainModel domainModel, RuleModel ruleModel) {
        super(variableModel, domainModel, ruleModel);
        view.createView();
    }
}
