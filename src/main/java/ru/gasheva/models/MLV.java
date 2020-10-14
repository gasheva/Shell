package ru.gasheva.models;

import ru.gasheva.consultation.ConsultationControl;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

public class MLV {
    Variable globalTarget;
    ConsultationControl control;
    RuleModel ruleModel;
    VariableModel variableModel;
    DomainModel domainModel;
    WorkingMemory workingMemory;
    final String UNDEF = "undef";

    public String defineGlobalTarget(Variable globalTarget, RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel, ConsultationControl control) {
        this.ruleModel = ruleModel;
        this.globalTarget = globalTarget;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        workingMemory = new WorkingMemory();
        this.control = control;
        int j = 0;
        Rule curRule = ruleModel.getRule(0);
        while (j <= curRule.conditionsSize()) {     // проходим по предпосылкам
            if (j == curRule.conditionsSize()) {      //если прошли все, то означиваем подцель
                workingMemory.add(curRule);
                workingMemory.add(globalTarget, curRule.getConclusion(0).getDomainValue());
                break;
            } else {
                Variable curVar = curRule.getCondition(j).getVariable();
                if (!workingMemory.hasValue(curVar))
                    switch (curVar.getVarType()) {
                        case ASK:
                            String val = control.askVariableValue(curVar);
                            workingMemory.add(curVar, new DomainValue(val));
                            break;
                        case RESOLVE:
                            defineLocalTarget(curVar);
                            break;
                    }
                if (workingMemory.get(curVar).equals(curRule.getCondition(j).getDomainValue())) {   //если значение нужное, то переходим к след переменной
                    j++;
                    continue;
                } else {       //иначе к следующему правилу
                    workingMemory.add(globalTarget, new DomainValue(UNDEF));
                    break;
                }
            }
        }
        return workingMemory.get(globalTarget).getValue();
    }

    private void defineLocalTarget(Variable localTarget) {
        if (workingMemory.hasValue(localTarget)) return;
        //находим правило с подцелью
        int i = 0;
        while (i <= ruleModel.size()) {
            if (i == ruleModel.size()) {     //если проверили все правила и не нашли подцель
                workingMemory.add(localTarget, new DomainValue(UNDEF));
                return;
            } else {
                //нашли правило с подцелью
                Rule curRule = ruleModel.getRule(i);
                if (!curRule.hasFactInConclusion(localTarget)) {
                    i++;
                    continue;
                }
                int j = 0;
                while (j <= curRule.conditionsSize()) {     // проходим по предпосылкам
                    if (j == curRule.conditionsSize()) {      //если прошли все, то означиваем подцель
                        workingMemory.add(curRule);
                        workingMemory.add(localTarget, curRule.getConclusion(0).getDomainValue());
                        return;
                    } else {
                        Variable curVar = curRule.getCondition(j).getVariable();
                        if (workingMemory.hasValue(curVar)) {        //если переменная означена, проверяем нужное ли значение
                            if (workingMemory.get(curVar).equals(curRule.getCondition(j).getDomainValue())) {   //если значение нужное, то переходим к след переменной
                                j++;
                                continue;
                            } else {       //иначе к следующему правилу
                                i++;
                                break;
                            }
                        } else {  // если переменная не означена
                            switch (curVar.getVarType()) {
                                case ASK:
                                    String val = control.askVariableValue(curVar);
                                    workingMemory.add(curVar, new DomainValue(val));
                                case RESOLVE:
                                    defineLocalTarget(curVar);
                            }
                            if (workingMemory.get(curVar).equals(curRule.getCondition(j).getDomainValue())) {   //если значение нужное, то переходим к след переменной
                                j++;
                                continue;
                            } else {       //иначе к следующему правилу
                                i++;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
