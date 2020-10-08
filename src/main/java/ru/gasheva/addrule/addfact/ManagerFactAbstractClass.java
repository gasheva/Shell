package ru.gasheva.addrule.addfact;

import ru.gasheva.addvariable.AddVariableControl;
import ru.gasheva.addvariable.ManagerVariableAbstractClass;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Variable;

import java.util.LinkedList;
import java.util.List;

public abstract class ManagerFactAbstractClass {
    DomainModel domainModel;
    VariableModel variableModel;
    CreateFactForm view;
    Fact newFact;

    public ManagerFactAbstractClass(DomainModel domainModel, VariableModel variableModel) {
        this.domainModel = domainModel;
        this.variableModel = variableModel;

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
        newFact.setDomainValue(new DomainValue(view.getDomainSelectedName()));
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
