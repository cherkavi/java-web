/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.actions;

import database.Connector;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

@ManagedBean(name="action")
@RequestScoped
public class Action{

    private ArrayList<ActionElement> list=null;
    /** получить список всех необходимых элементов для отображения по данному Action */
    public ArrayList<ActionElement> getList(){
        FacesContext context=FacesContext.getCurrentInstance();
        ActionListElement element=(ActionListElement)context.getApplication().evaluateExpressionGet(context, "#{data_for_page}", ActionListElement.class);
        if(element==null){
            return null;
        }
        if(list!=null)return list;
        Connection connection=Connector.getConnection();
        try{
            StringBuffer query=new StringBuffer();
            query.append("select	 \n");
            query.append("current_action.id id,	 \n");
            query.append("shop_list.start_page,	 \n");
            query.append("parse_session.id session_id,	 \n");
            query.append("parse_session.parse_begin,	 \n");
            query.append("parse_result.name result_name	 \n");
            query.append("from current_action	 \n");
            query.append("inner join parse_session on parse_session.id=current_action.id_session	 \n");
            query.append("inner join shop_list on shop_list.id=parse_session.id_shop	 \n");
            query.append("inner join parse_result on parse_result.id=parse_session.id_parse_result	 \n");
            query.append("where current_action.id_actions="+element.getId());
            ResultSet rs=connection.createStatement().executeQuery(query.toString());
            ArrayList<ActionElement> returnValue=new ArrayList<ActionElement>();
            while(rs.next()){
                returnValue.add(new ActionElement(rs.getInt("id"),rs.getString("start_page"),rs.getInt("session_id"),rs.getString("parse_begin"), rs.getString("result_name")));
            }
            // System.out.println("List size: "+returnValue.size());
            this.list=returnValue;
            return this.list;
        }catch(Exception ex){
            System.err.println("Action#list Exception:"+ex.getMessage());
            return null;
        }finally{
            try{
                connection.close();
            }catch(Exception ex){};
        }
    }

    /** переход назад к списку всех Action */
    public String linkBack(){
        return "/actions/ActionList";
    }

    /** переход на описание данной сессии */
    public String linkDetail(){
        System.out.println("Action: Goto Detail");
        return "/actions/ParserSession";
    }

}
