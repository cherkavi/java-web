/*
 * database_hibernate.java
 *
 * Created on 22 липня 2008, 10:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.hsqldb.jdbcDriver;
import org.hsqldb.jdbc.*;
/**
 *
 * @author Technik
 */
public class database_hibernate implements database{
    /** trace flag*/
    private boolean FLAG_DEBUG=false;
    /** name of database*/
    private final String field_database_name="task_db";
    /** database connection*/
    private Connection field_connection=null;
    
    /** trace method */
    private void out(String information){
        if(this.FLAG_DEBUG==true){
            System.out.println("database_hibernate#"+information);
        }
    }
    
    /** Creates a new instance of database_hibernate */
    public database_hibernate() {
        
    }

    public boolean connecting_to_database(String database_name) {
        return this.connecting_to_database();
    }

    public boolean connecting_to_database() {
        boolean return_value=false;
        try{
            jdbcDataSource dataSource=new jdbcDataSource();
            dataSource.setDatabase("jdbc:hsqldb:task_db");
            this.field_connection=dataSource.getConnection("sa","");
            this.field_connection.setAutoCommit(false);
            return_value=true;
        }catch(SQLException ex){
            out("connecting to database: SQL Exception:"+ex.getMessage());
        }catch(Exception e){
            out("connecting to database: Exception:"+e.getMessage());
        }
        return return_value;
    }

    public synchronized boolean commit() {
        boolean return_value=false;
        try{
            this.field_connection.commit();
            return_value=true;
        }catch(SQLException ex){
            out("commit SQLexception:"+ex.getMessage());
        }
        return return_value;
    }

    public synchronized boolean rollback() {
        boolean return_value=false;
        try{
            this.field_connection.rollback();
            return_value=true;
        }catch(SQLException ex){
            out("commit SQLexception:"+ex.getMessage());
        }
        return return_value;
    }

    public void attempt_close_statement(Statement statement) {
        try{
            if(statement!=null){
                statement.close();
            }
        }catch(SQLException ex){
            out("attempt_close_statement (SQLException):"+ex.getMessage());
        }catch(Exception e){
            out("attempt_close_statement (SQLException):"+e.getMessage());
        }
    }

    public boolean shutdown_database() {
        boolean return_value=false;
        try{
            Statement statement=this.field_connection.createStatement();
            statement.execute("SHUTDOWN");
            this.field_connection.close();    // if there are no other open connection
            return_value=true;
        }catch(SQLException ex){
            out("shutdown database:"+ex.getMessage());
        }
        return return_value;
    }

    public synchronized boolean table_exists(String table_name) {
        String query="SELECT COUNT(*) FROM "+table_name;
        boolean return_value=false;
        try{
            this.open_query(query).getStatement().close();
            return_value=true;
        }catch(Exception ex){
            return_value=false;
        }
        return return_value;
    }

    public synchronized ResultSet open_query(String query) throws SQLException {
        Statement statement = null;
        ResultSet return_value;
        statement = this.field_connection.createStatement();         
        return_value = statement.executeQuery(query);    
        return return_value;
    }

    public synchronized int update_query(String query) throws SQLException {
        int return_value=(-1);
        Statement statement = null;
        statement = this.field_connection.createStatement();
        return_value=statement.executeUpdate(query);
        statement.close();
        return return_value;
    }


    public synchronized List<String> get_field_from_table(String table, String field, String order) {
        ArrayList<String> return_value=new ArrayList();
        try{
            out("get_field_from_table:try select");
            String query="SELECT "+field+" FROM "+table+" ORDER BY "+order;
            ResultSet resultset=this.open_query(query);
            out("get_firld_from_table:select ok");
            while(resultset.next()){
                //out("Select:"+resultset.getString(1));
                return_value.add(resultset.getString(1));
            }
        }catch(SQLException ex){
            // exception
            out("get_field_from_table: (SQLException)"+ex.getMessage());
        }
        return return_value;
    }

    public synchronized List<String[]> get_query_result(String[] fields, String table_main, database_join[] joins, String where_string, String[] order) {
        ArrayList<String[]> return_value=new ArrayList();
        try{
            Statement statement=this.field_connection.createStatement();
            // create query:begin
            StringBuffer query=new StringBuffer();
            String break_string="\n";
            String determinate_string=",";
            query.append("SELECT ");
            for(int field_counter=0;field_counter<fields.length;field_counter++){
                query.append(fields[field_counter]);
                if(field_counter!=(fields.length-1)){
                    query.append(determinate_string);
                }
                query.append(break_string);
            }
            query.append(" FROM "+table_main+break_string);
            for(int join_counter=0;join_counter<joins.length;join_counter++){
                query.append(joins[join_counter]);
                query.append(break_string);
            }
            if((where_string!=null)&&(!where_string.trim().equals(""))){
                query.append(" WHERE ");
                query.append(where_string);
                query.append(break_string);
            }
            query.append(" ORDER BY ");
            for(int order_counter=0;order_counter<order.length;order_counter++){
                query.append(order[order_counter]);
                if(order_counter!=(order.length-1)){
                    query.append(determinate_string);
                }
                query.append(break_string);
            }
            // create query:end            
            // execute query
            out(" query:"+query.toString());
            ResultSet resultset=statement.executeQuery(query.toString());
            int column_counter=resultset.getMetaData().getColumnCount();
            int counter=0;
            // fill return_value
            while(resultset.next()){
                String[] row=new String[column_counter];
                for(counter=0;counter<column_counter;counter++){
                    row[counter]=resultset.getString(counter+1);
                }
                return_value.add(row);
            }
            this.attempt_close_statement(statement);
        }catch(SQLException ex){
            out("get_query_result (SQLException)"+ex.getMessage());
        }catch(Exception e){
            out("get_query_result (Exception)");
        }
        return return_value;
    }

    public synchronized String get_another_field(String table, String source_field, String source_value, String destination_field) {
        String return_value="";
        try{
            PreparedStatement statement=this.field_connection.prepareStatement("SELECT "+destination_field+" FROM "+table+" WHERE "+source_field+" = ?");
            statement.setString(1,source_value);
            ResultSet resultset=statement.executeQuery();
            if(resultset.next()){
                return_value=resultset.getString(1);
            }
            statement.close();
        }catch(SQLException ex){
            out("get_field_from_table:"+ex.getMessage());
        }
        return return_value;
    }

    public synchronized boolean insert(String table, String[] field_name, Object[] field_values) {
        boolean return_value=false;
        try{
            if(field_name.length==0){
                throw new Exception("empty field's name");
            };
            if(field_name.length!=field_values.length){
                throw new Exception("field's not match values");
            }
            StringBuffer query=new StringBuffer();
            query.append("INSERT INTO "+table+"(\n");
            for(int counter=0;counter<field_name.length;counter++){
                query.append(field_name[counter]);
                if(counter!=(field_name.length-1)){
                    query.append(", \n");
                }
            }
            query.append(") VALUES(\n");
            for(int counter=0;counter<field_name.length;counter++){
                query.append("?");
                if(counter!=(field_name.length-1)){
                    query.append(", \n");
                }
            }
            query.append(") ");
            PreparedStatement statement=this.field_connection.prepareStatement(query.toString());
            for(int counter=0;counter<field_name.length;counter++){
                statement.setObject(counter+1,field_values[counter]);
            }
            out("insert query:"+query.toString());
            if(statement.executeUpdate()>0){
                return_value=true;
            }
            
        }catch(SQLException ex){
            out("insert Exception:"+ex.getMessage());
        }catch(Exception e){
            out("insert Exception:"+e.getMessage());
        }
        return return_value;
    }

    public synchronized boolean update(String table, String[] field_name, Object[] field_values, String[] control_name, Object[] control_values) {
        boolean return_value=false;
        try{
            if((field_name.length==0)||(control_name.length==0)){
                throw new Exception("empty field's name");
            };
            if((field_name.length!=field_values.length)||(control_name.length!=control_values.length)){
                throw new Exception("field's not match values");
            }
            StringBuffer query=new StringBuffer();
            query.append("UPDATE "+table+" SET \n");
            for(int counter=0;counter<field_name.length;counter++){
                query.append(field_name[counter]);
                query.append("=?");
                if(counter!=(field_name.length-1)){
                    query.append(", \n");
                }
            }
            query.append(" WHERE \n");
            for(int counter=0;counter<control_name.length;counter++){
                query.append(control_name[counter]);
                query.append("=?");
                if(counter!=(control_name.length-1)){
                    query.append(", \n");
                }
            }
            PreparedStatement statement=this.field_connection.prepareStatement(query.toString());
            for(int counter=0;counter<field_name.length;counter++){
                statement.setObject(counter+1,field_values[counter]);
            }
            for(int counter=0;counter<control_name.length;counter++){
                statement.setObject(field_name.length+counter+1,control_values[counter]);
            }
            out("update query:"+query.toString());
            if(statement.executeUpdate()>0){
                return_value=true;
            }
            
        }catch(SQLException ex){
            out("update Exception:"+ex.getMessage());
        }catch(Exception e){
            out("update Exception:"+e.getMessage());
        }
        return return_value;
    }
    
}
