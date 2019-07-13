/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions.session;

/**
 * ������� ��� ����������� �������
 */
public class LoggerElement {
    private String level;
    private String message;

    public LoggerElement(){
    }

    /**
     * ������� ��� ����������� �������
     * @param level - �������
     * @param message - ���������
     */
    public LoggerElement(String level, String message){
        this.level=level;
        this.message=message;
    }

    /** ������� */
    public String getLevel() {
        return level;
    }

    /** ��������� */
    public String getMessage() {
        return message;
    }
    
}
