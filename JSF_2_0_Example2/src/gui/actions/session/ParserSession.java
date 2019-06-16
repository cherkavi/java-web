/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions.session;

import database.Connector;
import gui.actions.ActionElement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Technik
 */
@RequestScoped
@ManagedBean(name="parser_session")
public class ParserSession {
    /** описательный элемент */
    private SessionDescription description;

    private ArrayList<LoggerElement> list;

    public ArrayList<LoggerElement> getList(){
        if(list!=null){
            return list;
        }else{
            if(description==null){
                this.loadModel();
            }
            Connection connection=Connector.getConnection();
            try{
                StringBuffer query=new StringBuffer();
                query.append("select first 100 skip 0 logger_level.name, logger.logger_message\n");
                query.append("from logger\n");
                query.append("inner join logger_level on logger_level.id=logger.id_logger_level\n");
                query.append("where logger.id_session="+this.description.getId()+"\n");
                query.append("order by logger.id desc\n");
                ResultSet rs=connection.createStatement().executeQuery(query.toString());
                ArrayList<LoggerElement> returnValue=new ArrayList<LoggerElement>();
                while(rs.next()){
                    returnValue.add(new LoggerElement(rs.getString("name"), rs.getString("logger_message")));
                }
                this.list=returnValue;
            }catch(Exception ex){
                System.err.println("ParserSession#getList: "+ex.getMessage());
            }finally{
                try{
                    connection.close();
                }catch(Exception ex){};
            }
            return this.list;
        }
    }

    /** получить магазин, по которому производится парсинг  */
    public String getShop(){
        if(this.description==null){
            this.loadModel();
        }
        return this.description.getShop();
    }

    /** получить дату парсинга */
    public String getDate(){
        if(this.description==null){
            this.loadModel();
        }
        return this.description.getDate();
    }

    /** получить результат парсинга */
    public String getResult(){
        if(this.description==null){
            this.loadModel();
        }
        return this.description.getResult();
    }

    /** прочесть данные и загрузить в модель  */
    private void loadModel(){
        FacesContext context=FacesContext.getCurrentInstance();
        ActionElement element=context.getApplication().evaluateExpressionGet(context, "#{data_for_page}", ActionElement.class);
        int sessionId=element.getSessionId();
        Connection connection=Connector.getConnection();
        try{
            StringBuffer query=new StringBuffer();
            query.append("select parse_session.id, shop_list.name shop_name, parse_session.parse_begin,  parse_result.name parse_result\n");
            query.append("from parse_session\n");
            query.append("inner join shop_list on shop_list.id=parse_session.id\n");
            query.append("left join parse_result on parse_result.id=parse_session.id_parse_result\n");
            query.append("where id="+sessionId+"\n");
            ResultSet rs=connection.createStatement().executeQuery(query.toString());
            if(rs.next()){
                description=new SessionDescription(rs.getInt("id"), rs.getString("shop_name"), rs.getString("parse_begin"), rs.getString("parse_result"));
            }else{
                description=new SessionDescription(0, null, null, null);
            }
        }catch(Exception ex){
            System.err.println("loadModel Exception:"+ex.getMessage());
        }finally{
            try{
                connection.close();
            }catch(Exception ex2){};
        }
    }
}
