package ru.gasheva.addrule.addfact;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;

public class AddFactControl extends ManagerFactAbstractClass{

    public AddFactControl(DomainModel domainModel, VariableModel variableModel, String type) {
        super(domainModel, variableModel, type);
        view.createView(type);
    }
}
