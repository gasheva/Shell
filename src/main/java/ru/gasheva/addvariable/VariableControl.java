package ru.gasheva.addvariable;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;

public class VariableControl implements ControlInterface {
    MainForm view;
    DomainModel domainModel;
    VariableModel variableModel;
    ManagerVariableAbstractClass addVariable;
    ManagerVariableAbstractClass editVariable;

    public VariableControl(MainForm view, DomainModel domainModel, VariableModel variableModel) {
        this.view = view;
        this.domainModel = domainModel;
        this.variableModel = variableModel;
    }

    @Override
    public void add() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void redraw() {

    }

    @Override
    public void rowReorder(int from, int to) {

    }

    @Override
    public void tableSelectionValueChanged() {

    }
}
