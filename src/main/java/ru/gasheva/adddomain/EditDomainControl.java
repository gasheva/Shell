package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class EditDomainControl extends ManagerDomainAbstractClass {
    Domain oldDomain;

    public EditDomainControl(DomainModel domainModel, Domain domain) {
        super(domainModel);
        oldDomain = domain;
        view.createView();
    }

    @Override
    protected boolean isDomainValid(Domain domain) {
        //TODO
        //если имя совпадает, то в хранилище уже есть домен с этим именем
        if (domain.equals(oldDomain)){
            return domainModel.domainCount(domain)==1;
        }
        else{
            return !domainModel.isDomainExisting(domain);
        }

    }
}
