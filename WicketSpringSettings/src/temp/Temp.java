package temp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bonpay.partner.database.Connector;
import bonpay.partner.database.wrap.Satellite;

import java.util.List;

public class Temp extends WebPage{
	
	public Temp(){
		System.out.println("constructor create");
		initComponents();
	}
	
	private void initComponents(){
		System.out.println("InitComponents:");
		Label label=new Label("information",getLabel());
		this.add(label);
	}

	
	private String getLabel(){
		String returnValue=null;
		Session session=null;
		try{
			Connector connection=((TempApplication)(this.getApplication())).getHibernateConnection();
			session=connection.openSession();
			@SuppressWarnings("unchecked")
			List<Satellite> list=(List<Satellite>)session.createCriteria(Satellite.class).add(Restrictions.eq("id", new Integer(1))).list();
			if(list.size()>0){
				returnValue=list.get(0).getName();
			}else{
				returnValue="satellite is not found ";
			}
		}catch(Exception ex){
			System.err.println("initComponents:"+ex.getMessage());
			returnValue="exception:"+ex.getMessage();
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
}
