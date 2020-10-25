package ru.gasheva.models.jsonhandler;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
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
    public ArrayList<Rule> getRules() {
        return (ArrayList<Rule>) rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public ArrayList<Variable> getVariables() {
        return (ArrayList<Variable>) variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Domain> getDomains() {
        return (ArrayList<Domain>) domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }
    //endregion

    public void createMessageToWrite(){
        List<Domain> notUsedDomains = new ArrayList<>();
        domains.forEach(x->{if (!x.isUsed()) notUsedDomains.add(x);});
        domains = notUsedDomains;

        List<Variable> notUsedVariables = new ArrayList<>();
        variables.forEach(x->{if(!x.isUsed())notUsedVariables.add(x);});
        variables = notUsedVariables;
    }
    public void setUsages(RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel){
        for(Rule x:rules){
            int varIndex;
            for(int i=0; i<x.conditionsSize(); i++){
                varIndex = variables.indexOf(x.getCondition(i).getVariable());
                if(varIndex==-1){
                    variables.add(x.getCondition(i).getVariable());
                    x.getCondition(i).getVariable().subscribe(x);
                }
                else{
                    //пробросить ссылку от существующего правила к текущему факту
                    //Variable.copy();
                    x.getCondition(i).setVariable(variables.get(varIndex));
                    variables.get(varIndex).subscribe(x);
                }
            }
            varIndex = variables.indexOf(x.getConclusion(0).getVariable());
            if(varIndex==-1){
                variables.add(x.getConclusion(0).getVariable());
                x.getConclusion(0).getVariable().subscribe(x);
            }
            else{
                x.getConclusion(0).setVariable(variables.get(varIndex));
                variables.get(varIndex).subscribe(x);
            }
        }

        for(Variable x:variables){
            int domIndex = domains.indexOf(x.getDomain());
            if (domIndex==-1){
                domains.add(x.getDomain());
                x.getDomain().subscribe(x);
            }
            else{
                x.setDomain(domains.get(domIndex));
                //domains.get(domIndex).subscribe(x);
            }

        }


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
