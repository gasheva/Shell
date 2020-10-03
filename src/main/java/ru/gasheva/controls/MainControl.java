package ru.gasheva.controls;

import ru.gasheva.adddomain.DomainControl;
import ru.gasheva.addrule.RuleControl;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.ValueModel;

public class MainControl{
    RuleModel ruleModel;
    DomainModel domainModel;
    ValueModel valueModel;
    MainForm view;
    ControlInterface valuesControl;
    ControlInterface domainControl;
    ControlInterface currentControl;
    ControlInterface ruleControl;

    public MainControl() {
        ruleModel = new RuleModel();
        valueModel = new ValueModel();
        domainModel = new DomainModel();

        view = new MainForm(this);
        view.createView();
        view.createControls();

        ruleControl = new RuleControl(ruleModel, view);
        domainControl = new DomainControl(domainModel, view);

        //valuesControl = new
        currentControl = ruleControl;

    }

    public void add() {
        currentControl.add();
    }


    public void edit() {
        currentControl.edit();
    }

    //или по индексу (номеру строки)?
    public void remove() {
        currentControl.remove();
    }


    public void changeControl(int index) {
        switch (index){
            case 0: currentControl = ruleControl; break;
            case 1: currentControl = ruleControl; break;//currentControl = valuesControl; break;
            case 2: currentControl = domainControl; break;
        }
        currentControl.redraw();
    }

    public void rowReorder(int from, int to) {
        currentControl.rowReorder(from, to);
    }
}
