package xml_robot.report;

import java.util.ArrayList;

import xml_robot.exception.EngineException;

/** ����� ��� ��������� ��������� ������������� */
public abstract class Report {
	/** ������ ����������� ����������  */
	protected ArrayList<ReportParameter> listOfParameters=new ArrayList<ReportParameter>();
	
	/** �������� ���������� ����� ������ � �������� ���������� */
	public abstract int getUniqueReportNumber();
	/** �������� ��� ������ */
	public abstract String getName();
	
	/** �������� ����������� ������� ��� ������ */
	public ArrayList<ReportParameter> getReportParameters() {
		return this.listOfParameters;
	}
	
	/** �������� ���������  */
	public abstract void clearParameters();
	/** ���������� ��������  */
	public abstract boolean setParameter(int number, String value);
	
	/** ���������� ��������� ������ 
	 * @param partnerKod - ���������� ��� �������� 
	 * @param userKod - ���������� ��� ������������ 
	 * @param parameters - ��������� 
	 * @throws EngineException - ������ ��������� 
	 */
	public void execute(int partnerKod, int userKod, ReportParameter ... parameters) throws EngineException{
		if(this.listOfParameters.size()>0){
			// �������� ��� ��������� 
			for(ReportParameter parameter:parameters){
				parameter.clear();
			}
			// ��������� ��� ���������  
			for(int counter=0;counter<parameters.length;counter++){
				int index=this.listOfParameters.indexOf(parameters[counter]);
				if(index>=0){
					this.listOfParameters.get(index).copyValueFromAnotherParameter(parameters[counter]);
				}
			}
			// ��������� �� ���������, ������� �� ���������
			for(ReportParameter parameter:parameters){
				if((parameter.isRequired()==true)&&(parameter.isNotFill()==false)){
					throw new EngineException(411, parameter.getName());
				}
			}
		}
		// �� ������ ����� ��� ���������� ���������, ���������� �������
		algorithmExecute(partnerKod, userKod);
	}
	
	/** ����������� ���������, ��� ���������� ��������� � ����������� � ��������� ���� {@link Report#listOfParameters} */
	public abstract void algorithmExecute(int partnerKod, int userKod) throws EngineException;
}
