package ru.gasheva.addrule;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;

public class RuleControl implements ControlInterface {
    RuleModel ruleModel;
    MainForm view;
    DomainModel domainModel;
    VariableModel variableModel;
    ManagerRuleAbstractClass addRuleControl;
    ManagerRuleAbstractClass editRuleControl;

    public RuleControl(RuleModel model, MainForm view, DomainModel domainModel, VariableModel variableModel) {
        this.ruleModel = model;
        this.view = view;
        this.domainModel = domainModel;
        this.variableModel = variableModel;
    }

    @Override
    public void add() {
        addRuleControl = new AddRuleControl(variableModel, domainModel, ruleModel);

    }
    @Override
    public void edit() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Правило", "Описание"});
        view.setTableModel();
        view.changePrepPanelText("Посылка");
        view.changeConclusionPanelText("Заключение");
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);
    }

    @Override
    public void rowReorder(int from, int to) {

    }

    @Override
    public void tableSelectionValueChanged() {

    }
}
