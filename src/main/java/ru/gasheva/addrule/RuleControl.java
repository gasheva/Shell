package ru.gasheva.addrule;

import ru.gasheva.mainform.ControlInterface;
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
        if(!view.isTblInfoSelectRow()){
            view.showMessage("Выберите правило!");
            return;
        }
        String ruleName = view.getSelectedRowFirstColumnValue();
        Rule selectedRule = ruleModel.getRule(ruleName);
        editRuleControl = new EditRuleControl(variableModel,domainModel, ruleModel, selectedRule);
        Rule newRule = editRuleControl.getResult();
        if (newRule==null) return;
        //обновляем модель
        ruleModel.setRule(ruleModel.getRule(ruleName), newRule);

        //обновляем вьюшку
        String[] string = new String[2];
        string[0] = newRule.getName();
        string[1] = newRule.getRuleToString();
        int selectedRowIndex = view.getSelectedRowIndex();
        view.ChangeRowInTable(selectedRowIndex, string);

        String conditions ="";
        String conclusions ="";
        for(int i=0; i<newRule.conditionsSize();i++){
            conditions+=" " + newRule.getCondition(i).toString()+"\n";
        }
        for(int i=0; i<newRule.conclusionsSize();i++){
            conclusions+=" " + newRule.getConclusion(i).toString()+"\n";
        }

        view.setTfTopText(conditions);
        view.setTfBottomText(conclusions);
        view.setExplanationText(newRule.getExplanation());
    }

    @Override
    public void remove() {
        if(!view.isTblInfoSelectRow()){
            view.showMessage("Выберите правило!");
            return;
        }
        String id = view.getSelectedRowFirstColumnValue();
        //удаляем из модели
        ruleModel.remove(id);
        view.removeRow(view.getSelectedRowIndex());
        view.setTfTopText("");
        view.setTfBottomText("");
        view.setExplanationText("");
    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Правило", "Описание"});
        view.fillTable(ruleModel);
        view.setTableModel();
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);
        view.setExplanationPanelVisible(true);
        view.changePrepPanelText("Посылка");
        view.changeConclusionPanelText("Заключение");
        view.setTfTopText("");
        view.setTfBottomText("");
        view.setExplanationText("");
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
        view.setExplanationText(r.getExplanation());
    }
}
