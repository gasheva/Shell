package ru.gasheva.models.classes;

//x = ...
public class Fact {
    int id;
//    int id_variable;
//    int id_domain_value;
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
}
