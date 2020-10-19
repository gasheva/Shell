package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class AddDomainControl extends ManagerDomainAbstractClass {

    public AddDomainControl(DomainModel domainModel) {
        super(domainModel);
        view.createView();
    }

    @Override
    protected boolean isDomainValid(Domain domain){
        return !domainModel.isDomainExisting(domain);
    }

}
