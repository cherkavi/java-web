/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

/**
 * один элемент, который отображает элемент из таблицы Action
 */
public class ActionListElement {
    private int id;
    private String date;
    private String state;

    /**
     * один элемент, который отображает элемент из таблицы Action
     * @param id - уникальный номер из базы данных
     * @param date  - дата парсинга
     * @param state - состояние парсинга
     */
    public ActionListElement(int id, String date, String state){
        this.id=id;
        this.date=date;
        this.state=state;
    }

    public String getDate(){
        return date;
    }

    public String getState(){
        return state;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }
}
