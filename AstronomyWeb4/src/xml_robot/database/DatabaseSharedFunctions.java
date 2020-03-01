package xml_robot.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import database.ConnectWrap;

/** общие функции для базы данных  */
public class DatabaseSharedFunctions {
	private Logger logger=Logger.getLogger(this.getClass());
	/** существует ли пользователь по указанному партнеру  
	 * @param connector - соединение с базой данных 
	 * @param partnerSecretId
	 * @param userKod
	 * @return
	 */
	public boolean isUserExists(ConnectWrap connector, 
							    String partnerSecretId,
							    int userKod) {
		boolean returnValue=false;
		PreparedStatement ps=null;
		try{
			// получить код партнера
			ps=connector.getConnection().prepareStatement("SELECT kod FROM a_partner where a_partner.secret_kod=?");
			ps.setString(1, partnerSecretId);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				int partnerKod=rs.getInt("KOD");
				// получить код USER и сравнить код партнера по данному  
				rs=connector.getConnection().createStatement().executeQuery("select * from a_user_id where id_partner="+partnerKod+" and id="+userKod);
				returnValue=rs.next();
			}else{
				returnValue=false;
			}
			
			returnValue=true;
		}catch(Exception ex){
			logger.error("DatabaseSharedFunctions#isUserExists: Exception:"+ex.getMessage());
			returnValue=false;
		}finally{
			try{
				ps.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	 
}
