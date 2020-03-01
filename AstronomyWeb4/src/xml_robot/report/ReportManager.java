package xml_robot.report;

import java.util.ArrayList;
import xml_robot.report.core.Report11;

/** ����������� ����� �������� � �������  */
public class ReportManager {
	private static ArrayList<Report> listOfReportInSystem=new ArrayList<Report>();
	static{
		// INFO ����� ����������� ���� �������� � ������ ���������� 
		listOfReportInSystem.add(new Report11());
	}
	
	/** �������� ����� �� ����������� ������ ������  
	 * @param reportNumber - ���������� ����� ������, ������� �������������
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - ����� �� ������</li>
	 * 	<li><b>Report</b> - ���������� ����� ������ </li>
	 * </ul>
	 * */
	public static Report getReportByNumber(Integer reportNumber){
		Report returnValue=null;
		if(reportNumber!=null)
		for(int counter=0;counter<listOfReportInSystem.size();counter++){
			if(listOfReportInSystem.get(counter).getUniqueReportNumber()==reportNumber){
				returnValue=listOfReportInSystem.get(counter);
				break;
			}
		}
		return returnValue;
	}
	
	/** �������� ����� �� ���������� ������� (����������������� �������){@link #getReportCount()}  
	 * @param index - ����� ������ 
	 * */
	public static Report getReportBySequenceIndex(int index){
		if(index<=listOfReportInSystem.size()){
			return listOfReportInSystem.get(index);
		}else{
			return null;
		}
	}
	
	/** �������� ����� ���-�� ������� � ������� */
	public static int getReportCount(){
		return listOfReportInSystem.size();
	}
	
	/** �������� ������ ��� ������  
	 * @param index - ������ ������ ������ 
	 * */
	public static String getReportName(int index){
		if(listOfReportInSystem.size()>index){
			return listOfReportInSystem.get(index).getName();
		}else{
			return null;
		}
	}
	
	/** �������� ����� ������   
	 * @param index - ������ ������ ������ 
	 * */
	public static Integer getReportNumber(int index){
		if(listOfReportInSystem.size()>index){
			return listOfReportInSystem.get(index).getUniqueReportNumber();
		}else{
			return null;
		}
	}

	/** ������� ����� �� ��������� ����������� ������  
	 * @param reportNumber
	 * */
	public static Report createReportByNumber(int reportNumber) {
		Report returnValue=null;
		try{
			Class<?> clazz=Class.forName("xml_robot.report.core.Report"+reportNumber);
			returnValue=(Report)clazz.newInstance();
		}catch(Exception ex){
			System.err.println("#createReportByNumber Exception:"+ex.getMessage());
			returnValue=null;
		}
		return returnValue;
	}
	
}
