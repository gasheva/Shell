package ru.gasheva.models;

import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;

import java.util.ArrayList;
import java.util.List;

public class DomainModel {
    List<Domain> domains = new ArrayList<>();
    Domain oldDomain;

    //region Getter\Setter
    public Domain getOldDomain() {
        return oldDomain;
    }

    public void setOldDomain(Domain oldDomain) {
        this.oldDomain = oldDomain;
    }
    //endregion

    public void add(Domain domain){
        domains.add(domain);
    }
    public void remove(Domain domain){
        domains.remove(domain);
    }
    public void removeValue(Domain domain, DomainValue value){
        domains.get(domains.indexOf(domain)).add(value);
    }


    public boolean isDomainExisting(Domain domain) {
        return domains.stream().anyMatch(x->x.getName().equals(domain.getName()));
    }
    //кол-во доменов с передаваемым именем
    public int domainCount(Domain domain){
        return (int)domains.stream().filter(x->x.getName().equals(domain.getName())).count();
    }

}
