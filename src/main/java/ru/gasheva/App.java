package ru.gasheva;

import ru.gasheva.mainform.MainControl;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.jsonhandler.JsonHandler;
import ru.gasheva.models.jsonhandler.Message;

public class App {
    public static void main(String[] args) {
          // MainControl control = new MainControl();
        Message message = new Message();
        JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);
//        JsonHandler<Rule[]> jsonHandler = new JsonHandler<Rule[]>(Rule[].class);
//        Rule[] rules = new Rule[2];
//        Rule r1 = new Rule();
//        r1.setName("r1");
//        // rules[0] = r1;
//        //jsonHandler.writeInFile("output.json",rules);
//        rules = jsonHandler.readFromFile("output.json",rules);

    }
}
