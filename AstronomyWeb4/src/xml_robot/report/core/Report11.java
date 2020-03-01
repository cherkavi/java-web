package xml_robot.report.core;


import database.ConnectWrap;
import database.StaticConnector;
import xml_robot.exception.EngineException;
import xml_robot.report.Report;

/** сделки и договора */
public class Report11 extends Report{
	public Report11(){
		// TODO создать параметры 
	}
	
	@Override
	public String getName() {
		return "—делки и договора";
	}

	@Override
	public int getUniqueReportNumber() {
		return 11;
	}

	@Override
	public void algorithmExecute(int partnerKod, int userKod) throws EngineException {
		// выполнить отчет
		// алгоритм Ўј√. ”ран клиента (стоит) ћеркурий (двигаетс€) - период расчЄта: 9 мес€цев, с сегодн€шнего дн€. 3 мес€ца назад - 6 мес€цев вперед (шаг 1 день) 
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
		/*
			// генераци€ уникального кода приложени€
			A_USER_ID user=(A_USER_ID)connector.getSession().get(A_USER_ID.class,userKod);
			A_CITY cityBirthday=(A_CITY)connector.getSession().get(A_CITY.class, user.getBirthdayIdCity());
			// получить дату рождени€
			Date birthDayDate=user.getBirthday();
			// получить значение первой планеты 
			RightAlgorithm_FindAngle algorithmAngle=new RightAlgorithm_FindAngle();
			double firstAngle=0;
			firstAngle=algorithmAngle.getAngle(cityBirthday.getLongitude(), 
					cityBirthday.getLatitude(), 
					cityBirthday.getGmt(), 
					birthDayDate, 
					PlanetName.Uranus);
			RithmSave saver=new RithmSave();
			saver.begin(connector, uniqueId);
			// генераци€ данных
			new RightAlgorithm_Shag().executeShag(
					  this.getDateBegin(), 
					  this.getDateEnd(),
					  1,
					  city.getLongitude(),
					  city.getLatitude(),
					  city.getGmt(),
					  firstAngle,
					  PlanetName.Mercury,
					  saver
					   );
				 
			// создать уникальный код отчета
			reportIdentifier=application.generateUniqueId();
			fullPathToGeneratedReport.setObject(null);
			try{
				// присоединить слушатель
				application.getReporter().addReportListener(this);
				application.getReporter().addReportForExecute(new ReportQueueElement(reportIdentifier, 
																					 PatternReportList.reportRithmWeek,
																					 "—делки и договора ( "+sdf.format(this.getDateBegin())+".."+sdf.format(this.getDateEnd())+") ",
																					 new Query(uniqueId)));
				// застыть до по€влени€ сигнала от слушател€
				synchronized(this.signalObject){
					this.signalObject.wait(application.getMaxWaitReportTime());
				}
			}catch(Exception waitException){
				System.err.println("Section11#onButtonExecute Exception:"+waitException.getMessage());
			}finally{
				application.getReporter().removeReportListener(this);
			}
			// System.out.println("OutputFile:"+this.fullPathToGeneratedReport);
			this.reportLink.setVisible(true);
			target.addComponent(this.formMain);
*/			
		}catch(Exception ex){
			System.err.println("Section11#onButtonExecute Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		
	}

	@Override
	public void clearParameters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setParameter(int number, String value) {
		// TODO Auto-generated method stub
		return false;
	}
}
