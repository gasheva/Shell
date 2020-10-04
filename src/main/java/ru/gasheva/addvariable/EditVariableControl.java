package ru.gasheva.addvariable;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Variable;

public class EditVariableControl extends ManagerVariableAbstractClass {

    public EditVariableControl(VariableModel variableModel, DomainModel domainModel) {
        super(variableModel, domainModel);
    }

    @Override
    protected boolean isVariableValid(Variable domain) {
        return false;
    }
}
