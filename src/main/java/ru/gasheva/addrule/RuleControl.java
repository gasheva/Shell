package ru.gasheva.addrule;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.RuleModel;

public class RuleControl implements ControlInterface {
    RuleModel model;
    MainForm view;
    AddRuleForm addView;

    public RuleControl(RuleModel model, MainForm view) {
        this.model = model;
        this.view = view;
        model.init();
    }

    //вставка
    private int rowIndex;
    //region Methods from MainForm
    @Override
    public void add() {
        //addRuleControl = new AddRuleControl(ruleModel, valueModel, domainModel);
        //addView = new AddRuleForm(this);
        //view.createView();
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
        view.setTableModel();
        view.changePrepPanelText("Посылка");
        view.changeConclusionPanelText("Заключение");
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);
    }
    //endregion

//    public void ok(String[] values){
//
//        model.add(rowIndex, values);
//    }
//
//    public void cancel() {
//        view.Dispose();
//    }

}
