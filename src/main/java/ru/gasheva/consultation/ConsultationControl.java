package ru.gasheva.consultation;

import ru.gasheva.models.*;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;

public class ConsultationControl {
    private RuleModel ruleModel;
    private VariableModel variableModel;
    private DomainModel domainModel;
    private ConsultationForm consultationView;
    private ExplanationForm explanationView;
    private MLV mlv;
    private WorkingMemory workingMemory;

    public ConsultationControl(RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel) {
        this.ruleModel = ruleModel;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        consultationView = new ConsultationForm(this, ruleModel, variableModel, domainModel);
        consultationView.createView();
    }

    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public String askVariableValue(Variable curVar) {
        return consultationView.askVarValue(curVar);
    }

    public void ok()
    {
        consultationView.Dispose();
    }
    public void cancel(){
        consultationView.Dispose();
    }

    public void startNewConsultation() {
        String varName = consultationView.askGlobalTarget();
        if (varName==null){return;}
        Variable target = variableModel.getVariable(varName);
        workingMemory = new WorkingMemory();
        mlv = new MLV();
        String val = mlv.defineGlobalTarget(target, ruleModel, variableModel, domainModel, this);
        consultationView.showMessage(varName+" равна "+val);
    }

    public void explainAnswer() {
        if (workingMemory.getUsingRules()==null ||workingMemory.getUsingRules().isEmpty()) {
            consultationView.showMessage("Консультация не была проведена");
            return;
        }
        explanationView = new ExplanationForm(this, ruleModel, variableModel, domainModel);
        explanationView.createView();
    }

}
