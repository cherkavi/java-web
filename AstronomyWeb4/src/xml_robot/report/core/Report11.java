package xml_robot.report.core;


import database.ConnectWrap;
import database.StaticConnector;
import xml_robot.exception.EngineException;
import xml_robot.report.Report;

/** ������ � �������� */
public class Report11 extends Report{
	public Report11(){
		// TODO ������� ��������� 
	}
	
	@Override
	public String getName() {
		return "������ � ��������";
	}

	@Override
	public int getUniqueReportNumber() {
		return 11;
	}

	@Override
	public void algorithmExecute(int partnerKod, int userKod) throws EngineException {
		// ��������� �����
		// �������� ���. ���� ������� (�����) �������� (���������) - ������ �������: 9 �������, � ������������ ���. 3 ������ ����� - 6 ������� ������ (��� 1 ����) 
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
		/*
			// ��������� ����������� ���� ����������
			A_USER_ID user=(A_USER_ID)connector.getSession().get(A_USER_ID.class,userKod);
			A_CITY cityBirthday=(A_CITY)connector.getSession().get(A_CITY.class, user.getBirthdayIdCity());
			// �������� ���� ��������
			Date birthDayDate=user.getBirthday();
			// �������� �������� ������ ������� 
			RightAlgorithm_FindAngle algorithmAngle=new RightAlgorithm_FindAngle();
			double firstAngle=0;
			firstAngle=algorithmAngle.getAngle(cityBirthday.getLongitude(), 
					cityBirthday.getLatitude(), 
					cityBirthday.getGmt(), 
					birthDayDate, 
					PlanetName.Uranus);
			RithmSave saver=new RithmSave();
			saver.begin(connector, uniqueId);
			// ��������� ������
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
				 
			// ������� ���������� ��� ������
			reportIdentifier=application.generateUniqueId();
			fullPathToGeneratedReport.setObject(null);
			try{
				// ������������ ���������
				application.getReporter().addReportListener(this);
				application.getReporter().addReportForExecute(new ReportQueueElement(reportIdentifier, 
																					 PatternReportList.reportRithmWeek,
																					 "������ � �������� ( "+sdf.format(this.getDateBegin())+".."+sdf.format(this.getDateEnd())+") ",
																					 new Query(uniqueId)));
				// ������� �� ��������� ������� �� ���������
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
