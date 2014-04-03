/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru._12kb.mesagelib;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
/**
   * <p> Общение на основе сообщений. Сначала класс регистрирует 
   * себе слушателей, на разные события. Когда событие происходит
   * класс информирует подписчиков.</p
   * @author sirotinin
   * Можно применять как включая в состав переменных класса, так и наследуясь от него.
   * Первый вариант предпочтительнее.
*/
public class Speaker {
    
    HashMap<String, ArrayList<Listener>> words;
    
    /**
     * Конструктор.
     * @param _words команды. Команды задаются один раз, при создании объекта.
     * Изменить их в последствии нельзя, ибо нефиг.
     */
    Speaker(String [] _words){
        
        words = new HashMap<>();
        
        if ( (_words == null) || (_words.length == 0) ) {
            throw new java.lang.IllegalArgumentException("Неверный аргумент передан в конструктор класса Speaker");
        }
        
        for (String word : _words){
            words.put(word, new ArrayList<Listener>());
        }
    }
    
    /**
     * Регистрация подписчика.
     * @param ear - подписчик.
     * @param word - имя события на которое подписывается подписчик.
     * Чтобы подписаться на все используется ALL.
     * @return при удачной подписке возвращает true
     */
    public boolean regListener(Listener ear, String word){
        if ((word == null) || (ear == null)) {
            System.err.println("Speaker.regListener(): Переданы неверные аргументы.");
            return false;
        }
        
        if (!words.containsKey(word)){
            System.err.println("Ошибка. Speaker.regListener(): команда не зарегистрирована в рассылке.");
            return false;
        }
        
        if (words.get(word).indexOf(ear) != -1){
            System.err.println("Ошибка. Speaker.regListener(): слушатель уже зарегистрирован на команду: "+word+".");
            return false;
        }
        
        words.get(word).add(ear);
        return true;
    }
    
    /**
     * Удаление подписчика.
     * @param ear - подписчик.
     * @param word - имя события с которого нужно снять подписчика.
     * Чтобы отписаться от всего используется ALL.
     * @return при удачной подписке возвращает true.
     */
    public boolean rmListener(Listener ear, String word){
        if ((word == null) || (ear == null)) {
            System.err.println("Speaker.rmListener(): Переданы неверные аргументы.");
            return false;
        }
        
        if (!words.containsKey(word)){
            System.err.println("Ошибка. Speaker.rmListener(): команда не зарегистрирована в рассылке.");
            return false;
        }
        
        ArrayList<Listener> cmdListeners = words.get(word);
        int index = cmdListeners.indexOf(ear);
        if (index == -1){
            System.err.println("Ошибка. Speaker.rmListener(): слушатель не зарегистрирован на команду: "+word+".");
            return false;
        }
        
        cmdListeners.remove(index);
        return true;
    }
    
    
    /**
     * Передача сообщения.
     * @param word: Все подписчики команды word уведомляются.
     * ALL уведомляет вообще всех
     */
    public boolean say(String word) throws NoSuchCommandException{     
        if (word == null){
            System.err.println("Ошибка. Speaker.say(): пустая ссылка передана в качестве аргумента.");
            return false;
        }
        
        ArrayList<Listener> cmdListeners = words.get(word);
        if (cmdListeners == null){
            System.err.println("Ошибка. Speaker.say(): команда: "+word+" не зарегистрирована.");
            return false;
        }
        
        if (cmdListeners.size() == 0){
            System.err.println("Ошибка. Speaker.say(): нет слушателей на эту команду.");
            return false;
        }
        
        for (Listener lr : cmdListeners){
            lr.react(word);
        }
        
        return true;
    }
}