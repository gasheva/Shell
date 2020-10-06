package ru.gasheva.models.classes;

public class Variable {
    String name;
    VarType varType;
    String question;
    Domain domain;
    boolean isUsed = false;

    public Variable() {
    }

    public Variable(String name) {
        this.name = name;
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
        this.domain = domain;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    //endregion


    @Override
    public boolean equals(Object obj) {
        return this.getName().equals(((Variable)obj).getName());
    }
}
