package xml_robot.report;

import java.util.ArrayList;

import xml_robot.exception.EngineException;

/** отчет для генерации удаленным пользователем */
public abstract class Report {
	/** список необходимых параметров  */
	protected ArrayList<ReportParameter> listOfParameters=new ArrayList<ReportParameter>();
	
	/** получить уникальный номер отчета в масштабе приложения */
	public abstract int getUniqueReportNumber();
	/** получить имя отчета */
	public abstract String getName();
	
	/** получить необходимые функции для отчета */
	public ArrayList<ReportParameter> getReportParameters() {
		return this.listOfParameters;
	}
	
	/** очистить параметры  */
	public abstract void clearParameters();
	/** установить параметр  */
	public abstract boolean setParameter(int number, String value);
	
	/** выполнение заданного отчета 
	 * @param partnerKod - уникальный код партнера 
	 * @param userKod - уникальный код пользователя 
	 * @param parameters - параметры 
	 * @throws EngineException - ошибка двигателя 
	 */
	public void execute(int partnerKod, int userKod, ReportParameter ... parameters) throws EngineException{
		if(this.listOfParameters.size()>0){
			// очистить все параметры 
			for(ReportParameter parameter:parameters){
				parameter.clear();
			}
			// заполнить все параметры  
			for(int counter=0;counter<parameters.length;counter++){
				int index=this.listOfParameters.indexOf(parameters[counter]);
				if(index>=0){
					this.listOfParameters.get(index).copyValueFromAnotherParameter(parameters[counter]);
				}
			}
			// проверить на параметры, которые не заполнены
			for(ReportParameter parameter:parameters){
				if((parameter.isRequired()==true)&&(parameter.isNotFill()==false)){
					throw new EngineException(411, parameter.getName());
				}
			}
		}
		// на данном этапе все переменные проверены, выполнение функции
		algorithmExecute(partnerKod, userKod);
	}
	
	/** выполенение алгоритма, все переменные проверены и скопированы в локальный лист {@link Report#listOfParameters} */
	public abstract void algorithmExecute(int partnerKod, int userKod) throws EngineException;
}
