package ru.gasheva.controls;

import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.ModelInterface;

public class RuleControl implements ControlInterface {
    ModelInterface model;
    MainForm view;
    //AddForm addView;
    //AddControl addControl;

    public RuleControl(ModelInterface model, MainForm view) {
        this.model = model;
        this.view = view;
        model.init();
    }

    //вставка
    private int rowIndex;
    @Override
    public void add(int rowIndex) {
        //addControl = new AddRuleControl();    -
        //addView = new AddForm(this, model);
    }
    public void updateModel(String[] values){
        model.add(rowIndex, values);
    }

    @Override
    public void edit(String id, String[] values) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Правило", "Описание"});
        view.setTableModel();
        view.changePrepPanelText("Посылка");
        view.changeConclusionPanelText("Заключение");
        view.setPrepPanelVisible(false);
        view.setConclusionPanelVisible(true);
    }
}
