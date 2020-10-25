package ru.gasheva.models.classes;

//x = ...
public class Fact {
    int id;
    Variable variable;
    DomainValue domainValue;

    public Fact() {
    }

    public Fact(Variable variable, DomainValue domainValue) {
        this.variable = variable;
        this.domainValue = domainValue;
    }

    //region Getter-Setter
    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public DomainValue getDomainValue() {
        return domainValue;
    }

    public void setDomainValue(DomainValue domainValue) {
        this.domainValue = domainValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //endregion

    @Override
    public String toString(){
        return variable.getName()+"="+domainValue.getValue();
    }

    public static void copy(Fact from, Fact to){
        to.id = from.id;
        to.variable = from.variable;
        to.domainValue = from.domainValue;
    }

    public Fact clone(){
        Fact newFact = new Fact();
        newFact.domainValue=this.domainValue;
        newFact.variable = this.variable;
        newFact.id = this.id;
        return newFact;
    }
}
