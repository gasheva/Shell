package ru.gasheva.models.classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Domain implements Observable<Variable>{
    String name;
    boolean isUsed = false;
    List<DomainValue> domainValues = new ArrayList<>();
    private transient ObserverManager<Variable> observerManager = new ObserverManager<Variable>(this);

    public Domain() {
    }

    public Domain(String name) {
        this.name = name;
    }

    //region Getter\Setter

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
    public void add(DomainValue domainValue){
        domainValues.add(domainValue);
    }
    public void remove(DomainValue domainValue){
        domainValues.remove(domainValue);
    }
    public DomainValue getDomainValue(int index){return domainValues.get(index);}
    public int domainValuesSize(){
        return domainValues.size();}

    public String getDomainValuesInString(){
        final String[] values = {""};
        domainValues.forEach(x-> values[0] +=x.getValue()+" / ");
        return values[0].trim();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Domain)obj).getName().equals(this.getName());
    }


    public static void copy(Domain from, Domain to){
        to.name = from.name;
        to.isUsed = from.isUsed;
        to.domainValues = from.domainValues;
    }

    //region Subscribers
    @Override
    public void subscribe(Variable subscriber) {
        observerManager.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Variable subscriber) {
        observerManager.unsubscribe(subscriber);
    }

    @Override
    public int subscribersNumber() {
        return observerManager.size();
    }

    @Override
    public Variable getSubscriber(int index) {
        return observerManager.getSubscriber(index);
    }
    //endregion
}
