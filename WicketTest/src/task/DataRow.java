/*
 * DataRow.java
 *
 * Created on 17 липня 2008, 16:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

import java.io.Serializable;

/**
 *
 * @author Technik
 */
    class DataRow implements Serializable{
        String field_kod="";
        String field_column_1="";
        String field_column_2="";
        String field_column_3="";

        public DataRow(){
        }
        public DataRow(String column_1,String column_2,String column_3){
            this.setColumn_1(column_1);
            this.setColumn_2(column_2);
            this.setColumn_3(column_3);
        }
        
        public String getKod(){
            return this.field_kod;
        }
        public void setKod(String value){
            this.field_kod=value;
        }
        
        public String getColumn_1(){
            return this.field_column_1;
        }
        public void setColumn_1(String value){
            this.field_column_1=value;
        }
        

        public String getColumn_2(){
            return this.field_column_2;
        }
        public void setColumn_2(String value){
            this.field_column_2=value;
        }

        
        public String getColumn_3(){
            return this.field_column_3;
        }
        
        public void setColumn_3(String value){
            this.field_column_3=value;
        }

    }
