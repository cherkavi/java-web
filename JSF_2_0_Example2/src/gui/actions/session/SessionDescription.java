/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions.session;

/**
 * ������, ���������� �������� ��������� ������
 */
public class SessionDescription {
    private String shop;
    private String date;
    private String result;
    private int id;
    
    /**
     * ������, ���������� �������� ��������� ������
     * @param id - ���������� ������������� PARSE_SESSION
     * @param shop - ��� ��������
     * @param date - ����
     * @param result - ��������� 
     */
    public SessionDescription(int id, String shop, String date, String result){
        this.id=id;
        this.shop=shop;
        this.date=date;
        this.result=result;
    }

    public int getId() {
        return id;
    }

    public String getShop() {
        return shop;
    }

    public String getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }
}
