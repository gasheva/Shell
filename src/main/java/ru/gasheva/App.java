package ru.gasheva;

import ru.gasheva.controls.MainControl;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.ModelInterface;
import ru.gasheva.models.RuleModel;

public class App {
    public static void main(String[] args) {
        ModelInterface ruleModel = new RuleModel();
        MainControl control = new MainControl(ruleModel);
    }
}
