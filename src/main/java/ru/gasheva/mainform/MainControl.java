package ru.gasheva.mainform;

import ru.gasheva.adddomain.DomainControl;
import ru.gasheva.addrule.RuleControl;
import ru.gasheva.addvariable.VariableControl;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.Variable;
import ru.gasheva.models.jsonhandler.JsonHandler;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.jsonhandler.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainControl{
    RuleModel ruleModel;
    DomainModel domainModel;
    VariableModel variableModel;
    MainForm view;
    ControlInterface variableControl;
    ControlInterface domainControl;
    ControlInterface currentControl;
    ControlInterface ruleControl;

    public MainControl() {
        ruleModel = new RuleModel();
        variableModel = new VariableModel();
        domainModel = new DomainModel();

        view = new MainForm(this);
        view.createView();
        view.createControls();

        ruleControl = new RuleControl(ruleModel, view, domainModel, variableModel);
        domainControl = new DomainControl(domainModel, view);
        variableControl = new VariableControl(view, domainModel, variableModel);

        currentControl = ruleControl;

    }

    public void add() {
        currentControl.add();
    }


    public void edit() {
        currentControl.edit();
    }

    public void remove() {
        currentControl.remove();
    }


    public void changeControl(int index) {
        switch (index){
            case 0: currentControl = ruleControl; break;
            case 1: currentControl = variableControl; break;
            case 2: currentControl = domainControl; break;
        }
        currentControl.redraw();
    }

    public void rowReorder(int from, int to) {
        currentControl.rowReorder(from, to);
    }

    public void tableSelectionValueChanged() {
        currentControl.tableSelectionValueChanged();
    }

    public void loadData() {
        String path = view.getFileToOpen();
        //Message message = new Message(ruleModel.getRules().toArray(new Rule[1]), variableModel.getVariables().toArray(new Variable[1]), domainModel.getDomains().toArray(new Domain[1]));
        Message message;
        JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);

        message = jsonHandler.readFromFile(path);
        if (message==null) {view.showMessage("Не удалось загрузить данные");return;}
        ruleModel.setRules(Arrays.asList(message.getRules()));
        Variable[] v = message.getVariables();
        if (v!=null) variableModel.setVariables(Arrays.asList(v));
        Domain[] d = message.getDomains();
        if (d!=null) domainModel.setDomains(Arrays.asList(d));

        currentControl.redraw();
    }

    public void saveInFile() {
        String path = view.getFileToWrite();
        if (path==null) return;
        Message message = new Message(ruleModel.getRules().toArray(new Rule[0]), variableModel.getVariables().toArray(new Variable[0]), domainModel.getDomains().toArray(new Domain[0]));
        JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);
        if (!jsonHandler.writeInFile(path, message)) view.showMessage("Не сохранить данные");
    }
}
