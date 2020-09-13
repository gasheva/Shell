package ru.gasheva.controls;

import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.ModelInterface;

public class RuleControl implements ControlInterface{
    ModelInterface ruleModel;
    MainForm view;
    ControlInterface valuesControl;
    ControlInterface domainControl;

    @Override
    public void add() {

    }

    @Override
    public void update(String[] rowValues) {

    }

    @Override
    public void remove() {

    }

    @Override
    public void changeContol(int index) {
        switch (index){
            case 0: break;
            case 1: break;
            case 2: break;
        }
    }
}
