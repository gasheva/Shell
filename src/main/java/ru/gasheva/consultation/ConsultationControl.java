package ru.gasheva.consultation;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.MLV;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;

public class ConsultationControl {
    RuleModel ruleModel;
    VariableModel variableModel;
    DomainModel domainModel;
    ConsultationForm consultationView;
    ExplanationForm explanationView;

    public ConsultationControl(RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel) {
        this.ruleModel = ruleModel;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        consultationView = new ConsultationForm(this, ruleModel, variableModel, domainModel);
        consultationView.createView();
    }

    public String askVariableValue(Variable curVar) {
        return consultationView.askVarValue(curVar);
    }

    public void ok()
    {
        consultationView.Dispose();
        return;
    }
    public void cancel(){
        consultationView.Dispose();
        return;
    }

    public void runMLV(Variable target) {
        MLV mlv = new MLV();
        String val = mlv.defineGlobalTarget(target, ruleModel, variableModel, domainModel, this);
        consultationView.showMessage("Целевая переменная "+target.getName()+" = "+val);
    }

    public void startNewConsultation() {
        String varName = consultationView.askGlobalTarget();
        if (varName==null){}        //TODO
        runMLV(variableModel.getVariable(varName));
    }
}
