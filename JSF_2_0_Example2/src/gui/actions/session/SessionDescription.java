/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions.session;

/**
 * объект, содержащий описание указанной сессии
 */
public class SessionDescription {
    private String shop;
    private String date;
    private String result;
    private int id;
    
    /**
     * объект, содержащий описание указанной сессии
     * @param id - уникальный идентификатор PARSE_SESSION
     * @param shop - имя магазина
     * @param date - дата
     * @param result - результат 
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
