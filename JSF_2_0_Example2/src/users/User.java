/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package users;

import java.io.Serializable;

/**
 * ���������� ������, ������� �������� ������������
 */
public class User implements Serializable{
	private final static long serialVersionUID=1L;
	
    /** ���������� ������������� ������������ � �������  */
    private int id;

    public User(int id){
        this.id=id;
    }

    /** �������� ���������� ����� �� ���� ������ �� ������� ������� */
    public int getId(){
        return this.id;
    }

    /** ���������� ���������� ����� �� ���� ������ �� ������� ������� */
    public void setId(int id){
        this.id=id;
    }
}
