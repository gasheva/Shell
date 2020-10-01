package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class AddDomainControl extends ManagerDomainAbstractClass {

    public AddDomainControl(DomainModel domainModel) {
        super(domainModel);
    }

    @Override
    protected boolean isDomainValid(Domain domain){
        System.out.println(domain.getName());
        return !domainModel.isDomainExisting(domain);
    }

}
