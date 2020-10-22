package ru.gasheva.models;

import com.sun.org.apache.xpath.internal.operations.Mod;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DomainModel implements ModelInterface {
    List<Domain> domains = new LinkedList<>();

    //region Getter\Setter

    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(ArrayList<Domain> domains) {
        this.domains = domains;
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
    public void remove(String name){domains.remove(getDomain(name));}
    public void removeValue(Domain domain, DomainValue value){
        domains.get(domains.indexOf(domain)).add(value);
    }
    public Domain getDomain(int index){return domains.get(index);}
    public Domain getDomain(String name){return domains.stream().filter(x->x.getName().equals(name)).findAny().get();}

    @Override
    public int size(){return domains.size();}

    @Override
    public String[] getValuesForTable(int index) {
        Domain d = getDomain(index);
        //System.out.println("Domain name = "+d.getName());
        return new String[]{d.getName(), d.getDomainValuesInString()};
    }

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

    public void reorder(int from, int to, String id) {}

    public void clear() {
        domains.clear();
    }

    public boolean isEmpty() {
        return domains.isEmpty();
    }
}
