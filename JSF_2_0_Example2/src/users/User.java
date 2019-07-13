/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package users;

import java.io.Serializable;

/**
 * Уникальный объект, который содержит пользователя
 */
public class User implements Serializable{
	private final static long serialVersionUID=1L;
	
    /** уникальный идентификатор пользователя в системе  */
    private int id;

    public User(int id){
        this.id=id;
    }

    /** получить уникальный номер из базы данных по данному клиенту */
    public int getId(){
        return this.id;
    }

    /** установить уникальный номер из базы данных по данному клиенту */
    public void setId(int id){
        this.id=id;
    }
}
