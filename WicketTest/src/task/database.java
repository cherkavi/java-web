/*
 * database.java
 *
 * Created on 22 липня 2008, 8:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Technik
 */
public interface database {
    public boolean connecting_to_database(String database_name);
    /**
     * @Override
     */
    public boolean connecting_to_database();
    /**
     * commit change
     */
    public boolean commit();
    
    /**
     * rollback change
     */
    public boolean rollback();
    
    /**
     * close Statement
     */
    public void attempt_close_statement(Statement statement);
    /**
     * shutdown DataBase
     */
    public boolean shutdown_database();
    /**
     * Open Query
     * @param query - SQL Query
     * @return ResultSet or throw SQLException
     */
    public  ResultSet open_query(String query) throws SQLException;
        
    /**
     * Update Query
     * @param query - SQL Query
     * @return number of update Record
     */
    public int update_query(String query) throws SQLException;
    
    /**   check for exist table into database*/
    public boolean table_exists(String table_name);
    
    /** return List from "SELECT <field> FROM <table> ORDER BY <order>"
     * @param field <field>
     * @param table <table>
     * @param order <order>
     */
    public List<String> get_field_from_table(String table, String field, String order);
    /**
     * @return List<String[]> with data
     * 
     */
    public  List<String[]> get_query_result(String[] fields,String table_main,database_join[] joins, String where_string,String[] order);
    
    /** 
     * Get another field from record
     * @param table 
     * @param source_field
     * @param source_value
     * @param destination_field
     */
    public String get_another_field(String table, String source_field, String source_value, String destination_field);
    /**
     * INSERT record
     */
    public boolean insert(String table, 
                                       String[] field_name, 
                                       Object[] field_values);
    /**
     * UPDATE
     * @param table table name
     * @param field_name fields for update
     * @param field_values values for update
     * @param control_name fields for find rows
     * @param control_values values for find rows
     */ 
    public boolean update(String table, 
                                       String[] field_name, 
                                       Object[] field_values,
                                       String[] control_name, 
                                       Object[] control_values);
   
}
