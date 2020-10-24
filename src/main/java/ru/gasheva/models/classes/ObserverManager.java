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
        observable.setUsed(true);
        subscribers.add(o);
    }
    public void unsubscribe(T o){
        subscribers.remove(o);
        if (subscribers.size()==0) observable.setUsed(false);
    }

    public T getSubscriber(int index){
        return subscribers.get(index);
    }
    public int size(){
        return subscribers.size();
    }
}
