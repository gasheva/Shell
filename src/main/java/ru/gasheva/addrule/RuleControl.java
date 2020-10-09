package ru.gasheva.addrule;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;

public class RuleControl implements ControlInterface {
    RuleModel ruleModel;
    MainForm view;
    DomainModel domainModel;
    VariableModel variableModel;
    ManagerRuleAbstractClass addRuleControl;
    ManagerRuleAbstractClass editRuleControl;

    public RuleControl(RuleModel model, MainForm view, DomainModel domainModel, VariableModel variableModel) {
        this.ruleModel = model;
        this.view = view;
        this.domainModel = domainModel;
        this.variableModel = variableModel;
    }

    @Override
    public void add() {
        addRuleControl = new AddRuleControl(variableModel, domainModel, ruleModel);
        Rule newRule = addRuleControl.getResult();
        if (newRule==null) return;
        String[] ruleString = new String[2];
        ruleString[0] = newRule.getName();
        ruleString[1] = newRule.getRuleToString();

        //добавление в конец
        if (view.getSelectedRowIndex()==-1){
            ruleModel.add(newRule);
            view.AddInTable(ruleString);
        }
        //вставка
        else {
            ruleModel.add(view.getSelectedRowIndex(), newRule);
            view.InsertInTable(view.getSelectedRowIndex(), ruleString);
        }

    }
    @Override
    public void edit() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Правило", "Описание"});
        view.fillTable(ruleModel);
        view.setTableModel();
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);
        view.changePrepPanelText("Посылка");
        view.changeConclusionPanelText("Заключение");
        view.setTfTopText("");
        view.setTfBottomText("");
    }

    @Override
    public void rowReorder(int from, int to) {
        ruleModel.swapRules(from, to);
    }

    @Override
    public void tableSelectionValueChanged() {
        String[] values = view.getRowValues(view.getSelectedRowIndex());
        if (values == null) return;
        Rule r = ruleModel.getRule(values[0]);
        String conditions ="";
        String conclusions ="";
        for(int i=0; i<r.conditionsSize();i++){
            conditions+=" " + r.getCondition(i).toString()+"\n";
        }
        for(int i=0; i<r.conclusionsSize();i++){
            conclusions+=" " + r.getConclusion(i).toString()+"\n";
        }

        view.setTfTopText(conditions);
        view.setTfBottomText(conclusions);

    }
}
