package ru.gasheva;

import ru.gasheva.mainform.MainControl;
import ru.gasheva.models.classes.*;
import ru.gasheva.models.jsonhandler.JsonHandler;
import ru.gasheva.models.jsonhandler.Message;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
          MainControl control = new MainControl();
//        Map<Variable, DomainValue> curVariableValues = new HashMap<Variable, DomainValue>();
//        Variable v1 = new Variable("v1");
//        Variable v2 = new Variable("v1");
//        Rule r1 = new Rule("r1");
//        Rule r2 = new Rule("r2");
//        r1.addCondition(new Fact(v1, new DomainValue("d1")));
//        r2.addCondition(new Fact(v2, new DomainValue("d1")));
//        Variable v11 = r1.getCondition(0).getVariable();
//        Variable v21 = r2.getCondition(0).getVariable();
//        System.out.println(v11==v1);
//        System.out.println(v11==v21);
//        System.out.println(v11.equals(v21));
//
//        System.out.println("Equal? "+v1.equals(v2));
//        //curVariableValues.put(new Variable("v1"), new DomainValue("d1"));
//        curVariableValues.put(v2, new DomainValue("d1"));
//        System.out.println("Contain? "+curVariableValues.containsKey(v1));

//        Message message = new Message();
//        JsonHandler<Message> jsonHandler = new JsonHandler<Message>(Message.class);
//        Rule[] rules = new Rule[1];
//        Rule r1 = new Rule("r1");
//        Variable v1 = new Variable("v1");
//        Domain d1 = new Domain("d1");
//        d1.add(new DomainValue("dv1"));
//        d1.add(new DomainValue("dv2"));
//        v1.setDomain(d1);
//        Fact f1 = new Fact();
//        f1.setVariable(v1);
//        f1.setDomainValue(new DomainValue("dv1"));
//        r1.addCondition(f1);
//        r1.addCondition(f1);
//        r1.addConclusion(f1);
//        rules[0] = r1;
//        message.setRules(rules);
//        message.setDomains(new Domain[0]);
//        message.setVariables(new Variable[0]);
//        jsonHandler.writeInFile("output.json", message);
//        message = new Message();
//        message = jsonHandler.readFromFile("output.json");
//        System.out.println(message.getRules()[0].getName());

//        JsonHandler<Rule[]> jsonHandler = new JsonHandler<Rule[]>(Rule[].class);
//        Rule[] rules = new Rule[2];
//        Rule r1 = new Rule();
//        r1.setName("r1");
//        // rules[0] = r1;
//        //jsonHandler.writeInFile("output.json",rules);
//        rules = jsonHandler.readFromFile("output.json",rules);

    }
}
