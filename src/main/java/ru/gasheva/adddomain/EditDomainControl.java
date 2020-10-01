package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class EditDomainControl extends ManagerDomainAbstractClass {
    Domain oldDomain;

    public EditDomainControl(DomainModel domainModel, Domain domain) {
        super(domainModel);
        oldDomain = domain;
    }

    @Override
    protected boolean isDomainValid(Domain domain) {
        //TODO
        //если имя совпадает, то в хранилище не должно быть доменов с данным именем
        if (domain.getName().equals(oldDomain.getName())){
            return !domainModel.isDomainExisting(domain);
        }
        else{
            return domainModel.domainCount(domain)==1;
        }

    }
}
