package ru.gasheva.models;

import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DomainModel {
    List<Domain> domains = new LinkedList<>();
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
    public void add(int index, Domain domain){
        domains.add(index, domain);
    }
    public void remove(Domain domain){
        domains.remove(domain);
    }
    public void removeValue(Domain domain, DomainValue value){
        domains.get(domains.indexOf(domain)).add(value);
    }
    public void insertValue(int index, Domain domain) {domains.add(index, domain);}
    public Domain getDomain(int index){return domains.get(index);}
    public Domain getDomain(String name){return domains.stream().filter(x->x.getName().equals(name)).findAny().get();}
    public int size(){return domains.size();}

    public boolean isDomainExisting(Domain domain) {
        return domains.stream().anyMatch(x->x.equals(domain));
    }
    //кол-во доменов с передаваемым именем
    public int domainCount(Domain domain){
        return (int)domains.stream().filter(x->x.equals(domain)).count();
    }

    public void setDomain(int index, Domain newDomain) {
        domains.set(index, newDomain);
    }

    public int getDomainIndex(String name) {
        for(int i=0; i<domains.size();i++){
            if (domains.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public void reorder(int from, int to, String id) {
        Domain d = getDomain(id);
        if (to>from){
            domains.remove(from);
            domains.add(to, d);
        }
        if (from>to){
            domains.add(to, d);
            domains.remove(from);
        }
    }
}
