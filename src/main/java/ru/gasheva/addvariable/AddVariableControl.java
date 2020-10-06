package ru.gasheva.addvariable;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Variable;

public class AddVariableControl extends ManagerVariableAbstractClass {
    public AddVariableControl(VariableModel variableModel, DomainModel domainModel) {
        super(variableModel, domainModel);
        view.createView();
    }

    @Override
    protected boolean isVariableValid(Variable variable) {
        return !variableModel.isVariableExisting(variable);
    }
}
