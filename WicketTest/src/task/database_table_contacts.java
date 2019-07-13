/*
 * database_table_contacts.java
 *
 * Created on 22 липня 2008, 9:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;
import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author Technik
 */
@Table(name="CONTACTS")
public class database_table_contacts implements Serializable{
    private Integer kod;
    private String field_name;
    private String field_phone;
    private Integer field_kod_category;
    
    @Id
    @Column(name="KOD",unique=true,scale=0)
    public Integer getKod(){
        return this.kod;
    }
    public void setKod(Integer kod){
        this.kod=kod;
    }
    
    @Column(name="FIELD_NAME",scale=100)
    public String getField_name(){
        return this.field_name;
    }
    public void setField_name(String name){
        this.field_name=name;
    }

    @Column(name="FIELD_PHONE",scale=100)
    public String getField_phone(){
        return this.field_phone;
    }
    public void setField_phone(String phone){
        this.field_phone=phone;
    }

    @Column(name="FIELD_KOD_CATEGORY",scale=0)
    public Integer getField_kod_category(){
        return this.field_kod_category;
    }
    public void setField_kod_category(Integer kod_category){
        this.field_kod_category=kod_category;
    }
}
