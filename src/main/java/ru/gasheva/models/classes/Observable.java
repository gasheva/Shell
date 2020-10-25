package ru.gasheva.models.classes;

public interface Observable<T> {
    void subscribe(T subscriber);
    void unsubscribe(T subscriber);
    int subscribersNumber();
    T getSubscriber(int index);
}
