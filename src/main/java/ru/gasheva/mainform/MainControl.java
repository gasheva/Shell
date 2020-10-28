package ru.gasheva.mainform;

import ru.gasheva.adddomain.DomainControl;
import ru.gasheva.addrule.RuleControl;
import ru.gasheva.addvariable.VariableControl;
import ru.gasheva.consultation.ConsultationControl;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.Variable;
import ru.gasheva.models.jsonhandler.JsonHandler;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.jsonhandler.Message;
import ru.gasheva.models.oldjsonhandler.OldMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class MainControl{
    final String BACKUP_PATH = "backup.json";
    RuleModel ruleModel;
    DomainModel domainModel;
    VariableModel variableModel;
    MainForm view;
    ControlInterface variableControl;
    ControlInterface domainControl;
    ControlInterface currentControl;
    ControlInterface ruleControl;
    ConsultationControl consultationControl;
    AutoSave autoSave;

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

        if (view.doesLoadAutosave())
            loadData(BACKUP_PATH);

        autoSave = new AutoSave(ruleModel, variableModel, domainModel);
        Timer timer = new Timer();
        timer.schedule(autoSave, 0, 1000*60*1);     //каждые 5 минут
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

    public void loadData(String path) {
        if (path==null) return;
        Message message;
        JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);

        message = jsonHandler.readFromFile(path);
        if (message==null) {view.showMessage("Не удалось загрузить данные");return;}
        message.setUsages(ruleModel, variableModel, domainModel);

        ArrayList<Rule> r = message.getRules();
        if (r!=null) ruleModel.setRules(r);

        ArrayList<Variable> v = message.getVariables();
        if (v!=null) variableModel.setVariables(v);

        ArrayList<Domain> d = message.getDomains();
        if (d!=null) domainModel.setDomains(d);

        currentControl.redraw();
    }

    public void loadData2() {
        String path = view.getFileToOpen();
        if (path==null) return;
        OldMessage message;
        JsonHandler<OldMessage> jsonHandler = new JsonHandler<OldMessage>(OldMessage.class);

        message = jsonHandler.readFromFile(path);
        if (message==null) {view.showMessage("Не удалось загрузить данные");return;}
        ruleModel.setRules(new ArrayList<>(Arrays.asList(message.getRules())));
        Variable[] v = message.getVariables();
        if (v!=null) variableModel.setVariables(new ArrayList<>(Arrays.asList(v)));
        Domain[] d = message.getDomains();
        if (d!=null) domainModel.setDomains(new ArrayList<>(Arrays.asList(d)));

        currentControl.redraw();
    }

    public void saveInFile() {
        String path = view.getFileToWrite();
        if (path==null) return;
        path = path.trim();
        if (path.trim().length()==0) return;
        if (path.lastIndexOf(".json")!=path.length()-".json".length())
            path+=".json";
        Message message = new Message(ruleModel.getRules(), variableModel.getVariables(), domainModel.getDomains());
        JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);
        message.createMessageToWrite();
        if (!jsonHandler.writeInFile(path, message)) view.showMessage("Данные не сохранились");
        else view.showMessage("Данные успешно сохранены");
    }

    public void beginConsultation() {
        consultationControl = new ConsultationControl(ruleModel, variableModel, domainModel);
    }

    public void newES() {
        ruleModel.clear();
        variableModel.clear();
        domainModel.clear();
        currentControl.redraw();
    }

    public String getPathToAutosafe(){
        return BACKUP_PATH;
    }
    public void exit() {
        autoSave.cancel();
        view.Dispose();
    }
}
