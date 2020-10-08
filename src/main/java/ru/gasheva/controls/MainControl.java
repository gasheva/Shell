package ru.gasheva.controls;

import ru.gasheva.adddomain.DomainControl;
import ru.gasheva.addrule.RuleControl;
import ru.gasheva.addvariable.VariableControl;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;

public class MainControl{
    RuleModel ruleModel;
    DomainModel domainModel;
    VariableModel variableModel;
    MainForm view;
    ControlInterface variableControl;
    ControlInterface domainControl;
    ControlInterface currentControl;
    ControlInterface ruleControl;

    public MainControl() {
        ruleModel = new RuleModel();
        variableModel = new VariableModel();
        domainModel = new DomainModel();

        view = new MainForm(this);
        view.createView();
        view.createControls();

        ruleControl = new RuleControl(ruleModel, view, domainModel, variableModel);
        domainControl = new DomainControl(domainModel, view);
        variableControl = new VariableControl(view, domainModel, variableModel);

        currentControl = ruleControl;

    }

    public void add() {
        currentControl.add();
    }


    public void edit() {
        currentControl.edit();
    }

    //или по индексу (номеру строки)?
    public void remove() {
        currentControl.remove();
    }


    public void changeControl(int index) {
        switch (index){
            case 0: currentControl = ruleControl; break;
            case 1: currentControl = variableControl; break;
            case 2: currentControl = domainControl; break;
        }
        currentControl.redraw();
    }

    public void rowReorder(int from, int to) {
        currentControl.rowReorder(from, to);
    }

    public void tableSelectionValueChanged() {
        currentControl.tableSelectionValueChanged();
    }
}
