/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions.session;

/**
 * Ёлемент дл€ отображени€ логгера
 */
public class LoggerElement {
    private String level;
    private String message;

    public LoggerElement(){
    }

    /**
     * Ёлемент дл€ отображени€ логгера
     * @param level - уровень
     * @param message - сообщение
     */
    public LoggerElement(String level, String message){
        this.level=level;
        this.message=message;
    }

    /** уровень */
    public String getLevel() {
        return level;
    }

    /** сообщение */
    public String getMessage() {
        return message;
    }
    
}
