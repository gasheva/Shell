package ru.gasheva.models.jsonhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.encoding.RootOnlyCodec;
import ru.gasheva.models.classes.Rule;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonHandler<T> {
    private Class<T> type;      // Ex. new Foo<Integer>(Integer.class);
    public JsonHandler(Class<T> type) {
        this.type = type;
    }

    private String parser(T t){
        Gson gson = new Gson();
        return gson.toJson(t);
    }
    public boolean writeInFile(String path, T t){
        File file = new File(path);
        try (FileWriter fw = new FileWriter(file)){
            file.createNewFile();
            fw.write(parser(t));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public T readFromFile(String path, T t){
        Gson gson = new GsonBuilder().create();
        Path _path = new File(path).toPath();
        try(Reader reader = Files.newBufferedReader(_path, StandardCharsets.UTF_8)){
            t = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
