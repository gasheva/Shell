package ru.gasheva.models.jsonhandler;

import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
    List<Rule> rules;
    List<Variable> variables;
    List<Domain> domains;

    public Message() {
    }
    public Message(List<Rule> rules, List<Variable> variables, List<Domain> domains) {
        this.rules = rules;
        this.variables = variables;
        this.domains = domains;
    }

    //region Getter-Setter
    public Rule[] getRules() {
        return rules.toArray(new Rule[0]);
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public Variable[] getVariables() {
        return variables.toArray(new Variable[0]);
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public Domain[] getDomains() {
        return domains.toArray(new Domain[0]);
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }
    //endregion

    public void createMessageToWrite(){
        List<Domain> notUsedDomains = new ArrayList<>();
        domains.forEach(x->{if (!x.isUsed()) notUsedDomains.add(x);});
        domains = notUsedDomains;
    }
    public void setUsages(){
        variables.forEach(x->domains.add(x.getDomain()));
    }
//    public void setUsages(){
//        for(int i=0; i<variables.length; i++){
//            for(int j=0; j<domains.length; j++){
//                if (variables[i].getDomain().getName().equals(domains[j].getName())){
//                    variables[i].setDomain(domains[j]);
//                    break;
//                }
//            }
//        }
////        for(int i=0; i<rules.length; i++){
////            for(int j=0; j<variables.length; j++){
////                if (rules.get){
////                    variables[i].setDomain(domains[j]);
////                    break;
////                }
////            }
////        }
//    }
}
