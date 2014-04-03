/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru._12kb.mesagelib;

/**
 * Интерфейс слушателя. Чтобы использовать нужно от него наследоваться.
 * @author sirotinin
 */
public interface Listener {
    public abstract void react(String word);
}