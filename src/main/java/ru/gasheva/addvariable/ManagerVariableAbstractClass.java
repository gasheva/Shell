package ru.gasheva.addvariable;

import ru.gasheva.adddomain.AddDomainControl;
import ru.gasheva.adddomain.ManagerDomainAbstractClass;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.VarType;
import ru.gasheva.models.classes.Variable;

public abstract class ManagerVariableAbstractClass {
    DomainModel domainModel;
    VariableModel variableModel;
    CreateVarForm view;
    Variable newVariable = null;

    public ManagerVariableAbstractClass(VariableModel variableModel, DomainModel domainModel) {
        this.domainModel = domainModel;
        this.variableModel = variableModel;
        view = new CreateVarForm(this, variableModel, domainModel);
    }
    abstract protected boolean isVariableValid(Variable domain);

    public void ok() {
        if (view.isTfVariableNameEmpty()){
            view.showMessage("Введите имя переменной!");
            return;
        }
        if(!view.isCbDomainItemSelected()){
            view.showMessage("Выберите домен!");
            newVariable = null;
            return;
        }

        newVariable = view.getNewVariable();
        //проверка на уникальность имени
        if(!isVariableValid(newVariable))
        {
            view.showMessage("Переменная с таким именем уже существует!");
            newVariable = null;
            return;
        }

        view.Dispose();
    }

    public void cancel() {
        view.Dispose();
    }

    public void addDomain() {
        ManagerDomainAbstractClass addDomain = new AddDomainControl(domainModel);
        Domain newDomain = addDomain.getResult();
        if (newDomain==null) return;
        domainModel.add(newDomain);
        view.cbDomainsAddItem(newDomain.getName());
    }

    public Variable getResult(){
        return newVariable;
    }

    public void varTypeChanged() {
        VarType selectedVarType = view.getSelectedType();
        if (selectedVarType == VarType.RESOLVE){
            view.isQuestionPanelVisible(false);
        }
        else view.isQuestionPanelVisible(true);
    }
}
