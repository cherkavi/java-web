package xml_robot.report;

import java.util.ArrayList;
import xml_robot.report.core.Report11;

/** управл€ющий всеми отчетами в системе  */
public class ReportManager {
	private static ArrayList<Report> listOfReportInSystem=new ArrayList<Report>();
	static{
		// INFO место прибавлени€ всех запросов к списку параметров 
		listOfReportInSystem.add(new Report11());
	}
	
	/** получить отчет по уникальному номеру отчета  
	 * @param reportNumber - уникальный номер отчета, который запрашиваетс€
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - отчет не найден</li>
	 * 	<li><b>Report</b> - уникальный номер отчета </li>
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
	
	/** получить отчет по указанному индексу (последовательному индексу){@link #getReportCount()}  
	 * @param index - номер отчета 
	 * */
	public static Report getReportBySequenceIndex(int index){
		if(index<=listOfReportInSystem.size()){
			return listOfReportInSystem.get(index);
		}else{
			return null;
		}
	}
	
	/** получить общее кол-во отчетов в системе */
	public static int getReportCount(){
		return listOfReportInSystem.size();
	}
	
	/** получить полное им€ отчета  
	 * @param index - полный индекс отчета 
	 * */
	public static String getReportName(int index){
		if(listOfReportInSystem.size()>index){
			return listOfReportInSystem.get(index).getName();
		}else{
			return null;
		}
	}
	
	/** получить номер отчета   
	 * @param index - полный индекс отчета 
	 * */
	public static Integer getReportNumber(int index){
		if(listOfReportInSystem.size()>index){
			return listOfReportInSystem.get(index).getUniqueReportNumber();
		}else{
			return null;
		}
	}

	/** создать отчет на основании уникального номера  
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
