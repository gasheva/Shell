package ru.gasheva.addvariable;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.VarType;
import ru.gasheva.models.classes.Variable;

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
        addVariable = new AddVariableControl (variableModel, domainModel);
        Variable newVariable = addVariable.getResult();
        if (newVariable==null) return;
        String[] variableString = new String[3];
        variableString[0] = newVariable.getName();
        variableString[1] = newVariable.getVarType().toString();
        variableString[2] = newVariable.getDomain().getName();
        //добавление в конец
        if (view.getSelectedRowIndex()==-1){
            variableModel.add(newVariable);
            view.AddInTable(variableString);
        }
        //вставка
        else {
            variableModel.add(view.getSelectedRowIndex(), newVariable);
            view.InsertInTable(view.getSelectedRowIndex(), variableString);
        }
    }

    @Override
    public void edit() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Имя", "Тип", "Домен"});
        view.fillTable(variableModel);
        view.setTableModel();
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(false);
        view.changePrepPanelText("Вопрос");
        view.changeConclusionPanelText("");
        view.setTfTopText("");
        view.setTfBottomText("");
    }

    @Override
    public void rowReorder(int from, int to) {

    }

    @Override
    public void tableSelectionValueChanged() {
        String[] values = view.getRowValues(view.getSelectedRowIndex());
        if (values == null) return;

        view.setTfTopText(" " + variableModel.getVariable(values[0]).getQuestion());
    }
}
