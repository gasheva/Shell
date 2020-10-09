package ru.gasheva.addrule;

import ru.gasheva.addrule.addfact.AddFactControl;
import ru.gasheva.addrule.addfact.EditFactControl;
import ru.gasheva.addrule.addfact.ManagerFactAbstractClass;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

public abstract class ManagerRuleAbstractClass {
    protected VariableModel variableModel;
    protected DomainModel domainModel;
    protected RuleModel ruleModel;
    protected CreateRuleForm view;
    protected Rule newRule;


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
            return;
        }
        if (!view.hasConclusions()){
            view.showMessage("Добавьте заключение!");
            return;
        }
        if (!view.hasConditions()){
            view.showMessage("Добавьте предпосылку!");
            return;
        }
        newRule.setName(view.getRuleName());
        view.Dispose();
    }

    public void cancel(){newRule = null; view.Dispose();}

    public void addConclusion(){
        ManagerFactAbstractClass addFact = new AddFactControl(domainModel, variableModel);
        Fact newFact = addFact.getResult();
        if (newFact==null) return;
        newFact.setId(newRule.conclusionsSize());
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
        newRule.swapConclusions(from, to);
    }
    public void rowReorderConditions(int from, int to) {
        newRule.swapConditions(from, to);
    }

    public void editCondition() {
        if(!view.isTblConditionSelectRow()){
            view.showMessage("Выберите предпосылку!");
            return;
        }
        int id = view.getConditionRowIndex();
        Fact fact = newRule.getCondition(id);
        ManagerFactAbstractClass editFact = new EditFactControl(domainModel, variableModel, fact);
        Fact newFact = editFact.getResult();
        newFact.setId(id);
        //обновляем правило
        newRule.setCondition(id, newFact);

        //обновляем вьюшку
        view.setTblConditionRow(id, new String[]{newFact.toString()});
    }

    public void editConclusion(){
        if(!view.isTblConclusionSelectRow()){
            view.showMessage("Выберите заключение!");
            return;
        }
    }

    public void removeConclusion(){
        int id = view.getConclusionRowIndex();
        //удаляем из модели
        newRule.removeConclusion(id);
        //удаляем из вьюшки
        view.removeRowFromConclusions(id);
    }

    public void removeCondition() {
        int id = view.getConditionRowIndex();
        //удаляем из модели
        newRule.removeCondition(id);
        //удаляем из вьюшки
        view.removeRowFromConditions(id);
    }
}
