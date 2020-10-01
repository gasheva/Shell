package ru.gasheva.controls;

import ru.gasheva.addrule.RuleControl;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.ModelInterface;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.ValueModel;

public class MainControl {
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
        //domainControl = new DomainControl
        //valuesControl = new
        currentControl = ruleControl;

    }

    public void add(int rowIndex) {
        currentControl.add(rowIndex);
    }


    public void update(String id, String[] values) {
        currentControl.edit(id, values);
    }

    //или по индексу (номеру строки)?
    public void remove(String id) {
        currentControl.remove(id);
    }


    public void changeControl(int index) {
        switch (index){
            case 0: currentControl = ruleControl; break;
            case 1: currentControl = ruleControl; break;//currentControl = valuesControl; break;
            case 2: currentControl = ruleControl; break;//currentControl = domainControl; break;
        }
        currentControl.redraw();
    }
}
