package ru.gasheva.addrule.addfact;

import ru.gasheva.addvariable.AddVariableControl;
import ru.gasheva.addvariable.ManagerVariableAbstractClass;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.*;

import java.util.LinkedList;
import java.util.List;

public abstract class ManagerFactAbstractClass {
    DomainModel domainModel;
    VariableModel variableModel;
    CreateFactForm view;
    Fact newFact;
    private String factType;

    public ManagerFactAbstractClass(DomainModel domainModel, VariableModel variableModel, String type) {
        this.domainModel = domainModel;
        this.variableModel = variableModel;
        factType = type;

        view = new CreateFactForm(domainModel, variableModel, this);

    }

    public void ok() {
        if(!view.isCbVariableItemSelected()){
            view.showMessage("Выберите переменную!");
            newFact = null;
            return;
        }
        if(!view.isCbDomainItemSelected()){
            view.showMessage("Выберите значение домена!");
            newFact = null;
            return;
        }
        newFact = new Fact();
        Variable choosingVar = variableModel.getVariable(view.getVariableSelectedName());
        newFact.setVariable(choosingVar);
        DomainValue selectedDomainValue = new DomainValue(view.getDomainSelectedName());
        newFact.setDomainValue(selectedDomainValue);
        view.Dispose();
    }

    public void cancel() {
        view.Dispose();
    }

    public Fact getResult(){
        return newFact;
    }

    public void addVariable() {
        ManagerVariableAbstractClass addVariable = new AddVariableControl(variableModel, domainModel);
        Variable newVariable = addVariable.getResult();
        if (newVariable==null) return;
        variableModel.add(newVariable);
        if (factType.equals("concl")) {
            if (newVariable.getVarType()==VarType.RESOLVE||newVariable.getVarType()==VarType.ASK_RESOLVE)
                view.cbVariableAddItem(newVariable.getName());
        }
        else
            view.cbVariableAddItem(newVariable.getName());
    }

    public void variableSelectionChanged() {
        if (view.isCbVariableItemSelected()){
            String variableName = view.getVariableSelectedName();
            Domain domain = variableModel.getVariable(variableName).getDomain();
            List<String> domainValues = new LinkedList<>();
            for(int i=0; i<domain.domainValuesSize(); i++){
                domainValues.add(domain.getDomainValue(i).getValue());
            }
            view.fillCbDomainValue(domainValues);
        }
        return;
    }
}
