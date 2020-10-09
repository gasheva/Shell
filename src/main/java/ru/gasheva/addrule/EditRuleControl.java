package ru.gasheva.addrule;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;

public class EditRuleControl extends ManagerRuleAbstractClass{
    Rule oldRule;
    public EditRuleControl(VariableModel variableModel, DomainModel domainModel, RuleModel ruleModel, Rule oldRule) {
        super(variableModel, domainModel, ruleModel);
        //this.oldRule = oldRule;
        newRule = oldRule;
        view.createView(oldRule);
    }
}
