package task;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import java.io.*;
/**
 *
 */
public class Start extends WebApplication
{    
    /** trace flag*/
    private boolean FLAG_DEBUG=false;
    /** DataBase name*/
    private final String field_database_name="task_db";
    /** Database Wrap object*/
    public static database field_database;
    /** flag_connection */
    private boolean field_flag_connection=false;

    /**
     * Constructor
     */
    public Start(){
        try{
            //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("c:\\out.debug.tomcat.txt"), 128), true));
        }catch(Exception ex){
        }
        out("Start create database");
        //this.field_database=(database)new database_classic(this.field_database_name);        
        this.field_database=(database)new database_hibernate();        
        out("Start connecting");
        this.field_flag_connection=this.field_database.connecting_to_database();
        out("Check_data");
        check_data();
        //Application.get(Application.CONFIGURATION).getDebugSettings().setAjaxDebugModeEnabled(false);
    }
    
    /** for trace*/
    private void out(String information){
        if(this.FLAG_DEBUG==true){
            System.out.println("Start#"+information);
        }
    }
    
    /**
     * check for empty data into database
     * when empty - filling it
     */
    private void check_data(){
        // create table 
        if(!this.field_database.table_exists("CONTACTS")){
            // table not exists - create it
            String create_contacts="CREATE TABLE CONTACTS (KOD INTEGER IDENTITY, FIELD_NAME VARCHAR(100), FIELD_PHONE VARCHAR (100), FIELD_KOD_CATEGORY INTEGER)";
            try{
                out("check_data:create table CONTACTS");
                if(this.field_database.update_query(create_contacts)>=0){
                    // adding data
                    out("check_data - CONTACTS begin create row");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('55644655','55587654',1)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('A ABC DEF','1111',3)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('aaa ','bbb ',2)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('asdf ','asdf ',2)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('Bla ','12412424124',3)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME) VALUES('dd ')");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_KOD_CATEGORY) VALUES('DLD ',1)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('DLD ','418-658-4224 ',3)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE) VALUES('fff ','111')");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('gustavo ','55273222-2222',2)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('Ivanov ','3333333333333',1)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME) VALUES('l ')");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE) VALUES('Price, Peter - CFO ','765765765 ')");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('woot ','ar ',2)");
                    this.field_database.update_query("INSERT INTO CONTACTS(FIELD_NAME,FIELD_PHONE,FIELD_KOD_CATEGORY) VALUES('wwww ','wwww ',3)");
                    out("check_data - CONTACTS end create row");
                    this.field_database.commit();
                    
                }else{
                    throw new Exception("Error in create tabel CONTACTS");
                };
            }catch(Exception ex){
                out("check_data(Exception)"+ex.getMessage());
            }
        };
        if(!this.field_database.table_exists("CATEGORY")){
            // table not exists - create it
            String create_category="CREATE TABLE CATEGORY (KOD INTEGER IDENTITY, FIELD_NAME VARCHAR(100))";
            try{
                out("check_data:create table CATEGORY");
                if(this.field_database.update_query(create_category)>=0){
                    out("check_data - CATEGORY begin create row");
                    this.field_database.update_query("INSERT INTO CATEGORY (KOD,FIELD_NAME) VALUES (1,'Countryman')");
                    this.field_database.update_query("INSERT INTO CATEGORY (KOD,FIELD_NAME) VALUES (2,'Friend')");
                    this.field_database.update_query("INSERT INTO CATEGORY (KOD,FIELD_NAME) VALUES (3,'Roman')");
                    out("check_data - CATEGORY end create row");
                    this.field_database.commit();
                }
            }catch(Exception ex){
                out("check_data(Exception)"+ex.getMessage());
            }
        };
        
        // fill data
    }
    
    /**
     * return startup class
     */
    public Class getHomePage(){
        if(this.field_flag_connection==true){
            // connecting successful
            return Page_Main.class;
        }else{
            // connecting error
            return Page_Error.class;
        }
        
    }
    /**
     * @Override for delete "Ajax Development Window"
     */
    public String getConfigurationType(){
        return Application.DEPLOYMENT;
    }
    
}
