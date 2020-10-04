package ru.gasheva.models;

public interface ModelInterface {
    void init();
    int size();
    //куда вставляем, что вставляем
    void add(int index, String[] values);
    void update(String id, String newValue);
    void remove(String id);
}
