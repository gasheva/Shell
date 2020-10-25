package ru.gasheva.mainform;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;
import ru.gasheva.models.jsonhandler.JsonHandler;
import ru.gasheva.models.jsonhandler.Message;

import java.awt.TextArea;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoSave extends TimerTask {
    RuleModel ruleModel;
    VariableModel variableModel;
    DomainModel domainModel;

    public AutoSave(RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel) {
        this.ruleModel = ruleModel;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
    }

    @Override
    public void run() {
        if (!ruleModel.isEmpy() || !variableModel.isEmpty() || !domainModel.isEmpty()) {
            System.out.println("Autosaving...");
            Message message = new Message(ruleModel.getRules(), variableModel.getVariables(), domainModel.getDomains());
            JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);
            message.createMessageToWrite();
            if (!jsonHandler.writeInFile("backup.json", message)) System.out.println("Данные не сохранились");
        }
    }

//    @Override
//    public void run() {
//
//        while(true) {
//            try {
//                System.out.println("Thread going to sleep");
//                Thread.sleep(60000/2);     //300000
//            } catch (InterruptedException e) {
//                System.out.println(" Thread has been interrupted.");
//                return;
//            }
//            if (!ruleModel.isEmpy() || !variableModel.isEmpty() || !domainModel.isEmpty()) {
//                Message message = new Message(ruleModel.getRules().toArray(new Rule[0]), variableModel.getVariables().toArray(new Variable[0]), domainModel.getDomains().toArray(new Domain[0]));
//                JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);
//                jsonHandler.writeInFile("backup.json", message);
//            }
//
//        }


   // }

}