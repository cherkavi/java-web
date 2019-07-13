/*
 * database_join.java
 *
 * Created on 11 липня 2008, 10:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

/**
 *
 * @author Technik
 */
    /**
     * return List<String[]> from "SELECT <field> FROM <table> INNER JOIN <inner_table>"
     */
public class database_join{
        public static final int JOIN_LEFT=1;
        public static final int JOIN_INNER=0;
        public int join=0;
        public String table_main;
        public String table_foreign;
        public String field_main;
        public String field_foreign;
        
        /**
         * @param table_main - table from block FROM
         * @param field_main - field from table from block FROM
         * @param table_foreign - joined table
         * @param field_foreign - joined field from joined table
         */
        public database_join(int join_value,
                    String table_main,
                    String field_main,
                    String table_foreign, 
                    String field_foreign){
            this.setJoin(join_value);
            this.setField_foreign(field_foreign);
            this.setField_main(field_main);
            this.setTable_main(table_main);
            this.setTable_foreign(table_foreign);
        }
        // getter's and setter's
        public void setJoin(int value){
            this.join=value;
        }
        public void setTable_main(String value){
            this.table_main=value;
        }
        public void setTable_foreign(String value){
            this.table_foreign=value;
        }
        public void setField_main(String value){
            this.field_main=value;
        }
        public void setField_foreign(String value){
            this.field_foreign=value;
        }
        public String getTable_main(){
            return this.table_main;
        }
        public String getTable_foreign(){
            return this.table_foreign;
        }
        public String getField_main(){
            return this.field_main;
        }
        public String getField_foreign(){
            return this.field_foreign;
        }
        /**
         * @Override
         */
        public String toString(){
            String return_value="";
            if(this.join==this.JOIN_INNER){
                return_value="INNER JOIN "+this.getTable_foreign()+" ON "+this.getTable_foreign()+"."+this.getField_foreign()+"="+this.getTable_main()+"."+this.getField_main();
            };
            if(this.join==this.JOIN_LEFT){
                return_value="LEFT JOIN "+this.getTable_foreign()+" ON "+this.getTable_foreign()+"."+this.getField_foreign()+"="+this.getTable_main()+"."+this.getField_main();
            }
            return return_value;
        }
}
