package window.main;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;

import database.ConnectWrap;

import wicket_extension.UserApplication;
import wicket_extension.action.ActionExecutor;
import window.BasePage;
import window.commons.TitlePanel;

public class Main extends BasePage implements ActionExecutor{
	
	public Main(){
		initComponents();
	}
	
	private void initComponents(){
		ConnectWrap connector=((UserApplication)this.getApplication()).getConnector();
		if(connector.getConnection()==null){
			this.add(new TitlePanel("panel_main", this.getString("panel_main_caption")+" connection is NULL"));
		}else{
			this.add(new TitlePanel("panel_main", this.getString("panel_main_caption")+this.getValues(connector.getConnection())));
		}
		connector.close();
	}

	private String getValues(Connection connection){
		try{
			StringBuffer returnValue=new StringBuffer();
			ResultSet rs=connection.createStatement().executeQuery("select * from user_admin");
			int columnCount=rs.getMetaData().getColumnCount();
			while(rs.next()){
				for(int counter=1;counter<=columnCount;counter++)
				returnValue.append(counter+": "+rs.getString(counter)+"    ");
			}
			return returnValue.toString();
		}catch(Exception ex){
			return ex.getMessage();
		}
	}
	
	@Override
	public void action(String actionName, Serializable object) {
		while(true){
			break;
		}
	}

}
