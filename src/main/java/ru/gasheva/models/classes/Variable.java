package ru.gasheva.models.classes;

import java.util.Objects;

public class Variable implements Observable<Rule>{
    private String name;
    private VarType varType;
    private String question;
    private Domain domain;
    private transient ObserverManager<Rule> observerManager = new ObserverManager<Rule>(this);

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;
    }

    public static void copy(Variable from, Variable to){
        to.name = from.name;
        to.varType = from.varType;
        to.question = from.question;
        to.domain = from.domain;
    }

    //region Getter-Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VarType getVarType() {
        return varType;
    }

    public void setVarType(VarType varType) {
        this.varType = varType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        if (this.domain!=null)
            this.domain.unsubscribe(this);
        domain.subscribe(this);
        this.domain = domain;
    }

    public boolean isUsed() {
        return observerManager.size()>0;
    }

    //endregion


    @Override
    public boolean equals(Object obj) {
        if (obj==null || obj.getClass()!=getClass()) return false;
        return this.getName().equals(((Variable)obj).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    //region Subscribers

    @Override
    public void subscribe(Rule subscriber) {
        observerManager.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(Rule subscriber) {
        observerManager.unsubscribe(subscriber);
    }

    @Override
    public int subscribersNumber() {
        return observerManager.size();
    }

    @Override
    public Rule getSubscriber(int index) {
        return observerManager.getSubscriber(index);
    }

    //endregion
}
