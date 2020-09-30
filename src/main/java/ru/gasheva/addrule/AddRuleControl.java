package ru.gasheva.addrule;

import ru.gasheva.addrule.AddForm;
import ru.gasheva.models.RuleModel;

public class AddRuleControl {
    AddForm view;
    RuleModel model;

    public AddRuleControl(RuleModel model) {
        this.model = model;
        model.init();
        view = new AddForm(this, this.model);
        view.createView();
    }
}
