package ru.gasheva.models.classes;

public enum  VarType {
    ASK,
    RESOLVE,
    ASK_RESOLVE;

    @Override
    public String toString() {
        switch (this){
            case ASK: return "Запрашиваемая";
            case ASK_RESOLVE: return "Запрашиваемо-выводимая";
            case RESOLVE: return "Выводимая";
        }
        return "";
    }
};
