package com.dreamer.loggerstandartutil;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "mainClass")
@SessionScoped
public class MainClass implements Serializable {
    private String name;
    private final static Logger logs=Logger.getLogger(MainClass.class.getName());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public MainClass() {}
    
    public void Start() throws IOException{
        //срасываю диспетчер журнала, что бы избавиться от любых обработчиков, которые есть у маршрута
        LogManager.getLogManager().reset();
        
        /*
        SEVERE
        WARNING
        INFO
        CONFIG
        FINE
        FINEST
        */
        //какие уровни пропускаю для логирования
        logs.setLevel(Level.ALL);
        
        //Создаю ссылку на обработчика консоли
        ConsoleHandler ch=new ConsoleHandler();
        //задаю уровень логирования для обработчика консоли
        ch.setLevel(Level.SEVERE);
        //запускаю логер в фоновом режиме, что бы его не увидел пользователь
        logs.addHandler(ch);
        
        //создаю обработчик файлов для записи логирования
        FileHandler fh=new FileHandler("/usr/local/tomcat/logs/logging.log");
        //настраиваю форматирование текста в файле (можно создавать свой вместо SimpleFormatter)
        fh.setFormatter(new SimpleFormatter());
        //задаю уровень записи логов
        fh.setLevel(Level.FINE);
        //подключаю обработчик файлов для записи логов к логеру
        logs.addHandler(fh);
        
        logs.log(Level.SEVERE,"Тест сообщения");
        logs.log(Level.SEVERE,"Тест сообщения","Exception");
        FacesContext.getCurrentInstance().getExternalContext().redirect("result.xhtml");
    }
}
