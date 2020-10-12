package ru.gasheva.models.jsonhandler;

import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import java.util.List;

public class Message {
    Rule[] rules;
    Variable[] variables;
    Domain[] domains;

    public Message() {
    }

    public Message(Rule[] rules, Variable[] variables, Domain[] domains) {
        this.rules = rules;
        this.variables = variables;
        this.domains = domains;
    }

    //region Getter-Setter
    public Rule[] getRules() {
        return rules;
    }

    public void setRules(Rule[] rules) {
        this.rules = rules;
    }

    public Variable[] getVariables() {
        return variables;
    }

    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }

    public Domain[] getDomains() {
        return domains;
    }

    public void setDomains(Domain[] domains) {
        this.domains = domains;
    }
    //endregion
}
