package ru.gasheva.addrule;

import ru.gasheva.addrule.addfact.AddFactControl;
import ru.gasheva.addrule.addfact.ManagerFactAbstractClass;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;

import javax.swing.*;

public abstract class ManagerRuleAbstractClass {
    protected VariableModel variableModel;
    protected DomainModel domainModel;
    protected RuleModel ruleModel;
    protected CreateRuleForm view;
    private Rule newRule;

    public ManagerRuleAbstractClass(VariableModel variableModel, DomainModel domainModel, RuleModel ruleModel) {
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        this.ruleModel = ruleModel;
        newRule = new Rule();
        view = new CreateRuleForm(variableModel, domainModel, ruleModel, this);
    }

    public void ok(){
        if(view.isNameEmpty()){
            view.showMessage("Введите имя правила!");
            newRule = null;
            return;
        }
        if (!view.hasConclusions()){
            view.showMessage("Добавьте заключение!");
            newRule = null;
            return;
        }
        if (!view.hasConditions()){
            view.showMessage("Добавьте предпосылку!");
            newRule = null;
            return;
        }
        newRule = new Rule();
        newRule.setName(view.getRuleName());
        view.Dispose();
    }

    public void cancel(){view.Dispose();}

    public void addConclusion(){
        ManagerFactAbstractClass addFact = new AddFactControl(domainModel, variableModel);
        Fact newFact = addFact.getResult();
        if (newFact==null) return;
        newFact.setId(newRule.conclutionsSize());
        // добавляем в правило
        newRule.addConclusion(newFact);
        // обновляем форму
        view.addConclusion(newFact.toString());
    }
    public void addCondition(){
        ManagerFactAbstractClass addFact = new AddFactControl(domainModel, variableModel);
        Fact newFact = addFact.getResult();
        if (newFact==null) return;
        newFact.setId(newRule.conditionsSize());
        // добавляем в правило
        newRule.addCondition(newFact);
        // обновляем форму
        view.addCondition(newFact.toString());
    }

    public Rule getResult(){
        return newRule;
    }

    public void rowReorderConclusions(int from, int to) {
        String id = view.getConditionRowIndex(to);
        //domainModel.reorder(from, to, id); //TODO reorder domain values
    }
    public void rowReorderConditions(int from, int to) {
        String id = view.getConditionRowIndex(to);
        //domainModel.reorder(from, to, id); //TODO reorder domain values
    }
}
