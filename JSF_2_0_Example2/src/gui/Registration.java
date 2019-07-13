package gui;

import database.Connector;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import users.User;
import users.UserHolder;

@ManagedBean(name="registration")
public class Registration implements Serializable {
    private final static long serialVersionUID=1L;
    
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String submit(){
        FacesContext context=FacesContext.getCurrentInstance();
        UserHolder userHolder=(UserHolder)context.getApplication().evaluateExpressionGet(context,  "#{user_holder}", UserHolder.class);
        if((userHolder!=null)){
            Connection connection=Connector.getConnection();
            try{
                PreparedStatement ps=connection.prepareStatement("select * from users where user_login=? and user_password=? ");
                ps.setString(1, login);
                ps.setString(2, password);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    userHolder.setUser(new User(rs.getInt("id")));
                }else{
                    context.addMessage("command_button_message", new FacesMessage("user not found"));
                    return null;
                }
            }catch(Exception ex){
                System.err.println("ParsingList#getMessage Exception:"+ex.getMessage());
                return "user not found";
            }finally{
                try{
                    connection.close();
                }catch(Exception ex){};
            }
        }else{
            System.err.println("объект не найден в сессии ");
        }
        return "/actions/ActionList";
    }
}
