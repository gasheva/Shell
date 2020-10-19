package ru.gasheva.addrule.addfact;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Fact;

public class EditFactControl extends ManagerFactAbstractClass{
    Fact oldFact;
    public EditFactControl(DomainModel domainModel, VariableModel variableModel, Fact oldFact) {
        super(domainModel, variableModel);
        this.oldFact = oldFact;
        view.createView(oldFact);
    }
}
