package ru.gasheva.models.classes;

import java.util.Objects;

public class Variable {
    private String name;
    private VarType varType;
    private String question;
    private Domain domain;
    private boolean isUsed = false;

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

    //TODO: добавление домена в переменную. Домен должен знать ид переменных
    public void setDomain(Domain domain) {
        if (this.domain!=null)
            this.domain.setUsed(false);
        domain.setUsed(true);
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
        if (obj==null || obj.getClass()!=getClass()) return false;
        return this.getName().equals(((Variable)obj).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
