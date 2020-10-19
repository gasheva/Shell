package ru.gasheva.addvariable;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Variable;

public class EditVariableControl extends ManagerVariableAbstractClass {

    Variable oldVariable;
    public EditVariableControl(VariableModel variableModel, DomainModel domainModel, Variable oldVariable) {
        super(variableModel, domainModel);
        this.oldVariable = oldVariable;
        view.createView(oldVariable);
    }

    @Override
    protected boolean isVariableValid(Variable variable) {
        //если имя совпадает, то в хранилище уже есть домен с этим именем
        if (variable.equals(oldVariable)){
            return variableModel.variableCount(variable)==1;
        }
        else{
            return !variableModel.isVariableExisting(variable);
        }

    }
}
