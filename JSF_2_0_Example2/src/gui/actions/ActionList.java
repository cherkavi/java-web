/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import database.Connector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Technik
 */
@ManagedBean(name="action_list")
public class ActionList{

    /** получить список доступного парсинга
     * @return список из значений
     */
    public ArrayList<ActionListElement> getList(){
        Connection connection=Connector.getConnection();
        try{
            StringBuffer query=new StringBuffer();
            query.append("select first 10 skip 0\n");
            query.append("    actions.id,\n");
            query.append("    actions.date_write,\n");
            query.append("    action_state.name\n");
            query.append("from actions\n");
            query.append("inner join action_state on action_state.id=actions.id_action_state\n");
            query.append("order by actions.id desc \n");
            ResultSet rs=connection.createStatement().executeQuery(query.toString());
            ArrayList<ActionListElement> list=new ArrayList<ActionListElement>();
            while(rs.next()){
                list.add(new ActionListElement(rs.getInt("id"), rs.getString("date_write"),rs.getString("name")));
            }
            return list;
        }catch(Exception ex){
            System.err.println("getList Exception: "+ex.getMessage());
            return null;
        }finally{
            try{
                connection.close();
            }catch(Exception ex){};
        }
    }

    
    public String linkClick()  {
        // нажата ссылка на одном из элементов
        // получить элемент
        FacesContext context=FacesContext.getCurrentInstance();
        ActionListElement element=(ActionListElement)context.getApplication().evaluateExpressionGet(context, "#{list_element}", ActionListElement.class);
        if(element!=null){
            // отобразить страницу с более детальной информацией
            //System.out.println("Date: "+element.getDate()+"   State: "+element.getState());
            ELContext elContext=context.getELContext();
            ValueExpression ve=context.getApplication().getExpressionFactory().createValueExpression(elContext, "#{data_for_page}",ActionListElement.class);
            ve.setValue(elContext, element);
            return "/actions/Action";
        }else{
            System.err.println("Value does not recognized");
            return null;
        }
    }


    /** создать новый Action и послать команду старта  */
    public void createNewAndStartIt(){
        System.out.println("create List and start it");
        // TODO запретить создание, если в текущий момент есть действующие парсеры 
        Connection connection=Connector.getConnection();
        try{
            connection.createStatement().executeUpdate("insert into ACTIONS(ID_ACTION_STATE) VALUES(0)");
            connection.commit();
        }catch(Exception ex){
            System.err.println("ActionList#createListAndStartId Exception:"+ex.getMessage());
        }finally{
            try{
                connection.close();
            }catch(Exception ex){};
        }
    }
}
