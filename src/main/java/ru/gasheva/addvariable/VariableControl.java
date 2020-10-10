package ru.gasheva.addvariable;

import ru.gasheva.mainform.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
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
        if(!view.isTblInfoSelectRow()){
            view.showMessage("Выберите переменную!");
            return;
        }
        String variableName = view.getSelectedRowFirstColumnValue();
        Variable selectedVariable = variableModel.getVariable(variableName);
        editVariable = new EditVariableControl (variableModel, domainModel, selectedVariable);
        Variable newVariable = editVariable.getResult();
        if (newVariable==null) return;
        //обновляем модель
        variableModel.setVariable(variableModel.getVariableIndex(variableName), newVariable);

        //обновляем вьюшку
        String[] domainString = new String[3];
        domainString[0] = newVariable.getName();
        domainString[1] = newVariable.getVarType().toString();
        domainString[2] = newVariable.getDomain().getName();
        int selectedRowIndex = view.getSelectedRowIndex();
        view.ChangeRowInTable(selectedRowIndex, domainString);

        String values = " " + newVariable.getQuestion();
        view.setTfTopText(values);
        view.setTfBottomText(" "+newVariable.getDomain().getDomainValuesInString().replaceAll("/", "\n"));
    }

    @Override
    public void remove() {
        if(!view.isTblInfoSelectRow()){
            view.showMessage("Выберите переменную!");
            return;
        }
        String id = view.getSelectedRowFirstColumnValue();
        //удаляем из модели
        variableModel.remove(id);
        view.removeRow(view.getSelectedRowIndex());
        view.setTfTopText("");
        view.setTfBottomText("");
    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Имя", "Тип", "Домен"});
        view.fillTable(variableModel);
        view.setTableModel();
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);
        view.changePrepPanelText("Вопрос");
        view.changeConclusionPanelText("Значения домена");
        view.setTfTopText("");
        view.setTfBottomText("");
    }

    @Override
    public void rowReorder(int from, int to) {}

    @Override
    public void tableSelectionValueChanged() {
        String[] values = view.getRowValues(view.getSelectedRowIndex());
        if (values == null) return;

        Variable v = variableModel.getVariable(values[0]);
        view.setTfTopText(" " + variableModel.getVariable(values[0]).getQuestion());
        view.setTfBottomText(" "+v.getDomain().getDomainValuesInString().replaceAll("/", "\n"));
    }
}
