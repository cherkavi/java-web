package algorithms.out.database;

import java.sql.PreparedStatement;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;

import database.ConnectWrap;
import database.wrap_mysql.*;

import algorithms.out.IRithmOutput;
import algorithms.utils.Weight;

/** сохранение в базе данных указанной пары значений  */
public class RithmSave implements IRithmOutput{
	/** уникальный идентификатор  */
	private int id;
	/** подготовленный запрос для сохранения данных */
	private PreparedStatement ps=null;
	
	/** сохранение в базе данных указанной пары значений
	 * @param connection - соединение с базой данных   
	 * @param value - уникальный идентификатор для данного партнера  
	 * */
	public int begin(ConnectWrap connector, String userId){
		int returnValue=0;
		
		try{
			A_USER_ID newUserRecord=new A_USER_ID();
			newUserRecord.setUserIdentifier(userId);
			Session session=connector.getSession();
			session.beginTransaction();
			session.save(newUserRecord);
			session.getTransaction().commit();
			returnValue=newUserRecord.getId();
			this.id=returnValue;
			this.ps=connector.getConnection().prepareStatement("insert into a_calculation_rithm (id_user, date_value, aspect, kpd, risha) values (?,?,?,?,?)");
		}catch(Exception ex){
			System.err.println("RithmSave#start Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	/** вывод/сохранение данных алгоритма Rithm
	 * {@inheritDoc}
	 * <br />
	 * <b>дополнительный поиск аспектов из представленных координат</b>  
	 */
	@Override
	public void execute(Date date, 
						double angle1, 
						double angle2) {
		int[] result=Weight.getAspectBetween(angle1, angle2);
		if(result!=null){
			float aspect=Weight.array[result[0]][0]; 
			float kpd=Weight.array[result[0]][result[1]]; 
			int risha=Weight.aspectWeight[result[0]];
			try{
				this.ps.setInt(1, this.id);
				this.ps.setTimestamp(2, new Timestamp(date.getTime()));
				this.ps.setFloat(3, aspect);
				this.ps.setFloat(4, kpd);
				this.ps.setInt(5, risha);
				this.ps.executeUpdate();
				this.ps.getConnection().commit();
			}catch(Exception ex){
				System.err.println("RithmSave#execute Exception:"+ex.getMessage());
			}
		}
	}
	
}
