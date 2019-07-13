/*
 * database_table_category.java
 *
 * Created on 22 липня 2008, 10:08
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
@Table(name="CATEGORY")
public class database_table_category implements Serializable{
    private Integer kod;
    private String field_name;
    
    @Column(name="KOD",unique=true,scale=0)
    public Integer getKod(){
        return this.kod;
    }
    public void setKod(Integer value){
        this.kod=value;
    }
    
    @Column(name="FIELD_NAME",scale=100)
    public String getField_name(){
        return this.field_name;
    }
    public void setField_name(String value){
        this.field_name=value;
    }
}
