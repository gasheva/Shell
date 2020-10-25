package ru.gasheva.models.classes;

import java.util.LinkedList;
import java.util.List;

public class ObserverManager<T> {
    List<T> subscribers = new LinkedList<>();
    private Observable observable;

    public ObserverManager(Observable observable) {
        this.observable = observable;
    }

    public void subscribe(T o){
        subscribers.add(o);
    }
    public void unsubscribe(T o){
//        for(int i=0; i<subscribers.size();i++){
//            if (subscribers.get(i)==o){
//                subscribers.remove(i);
//                break;
//            }
//        }
        subscribers.remove(o);
    }

    public T getSubscriber(int index){
        return subscribers.get(index);
    }
    public int size(){
        return subscribers.size();
    }
}
