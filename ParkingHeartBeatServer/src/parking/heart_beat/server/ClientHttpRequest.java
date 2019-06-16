package parking.heart_beat.server;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/** ������, ������� "������" �� ������� � ���� ������ ����������  */
public class ClientHttpRequest {
	private ArrayList<ClientHttpParameter> parameters=new ArrayList<ClientHttpParameter>();
	
	/** ������, ������� "������" �� ������� � ���� ������ ����������  */
	public ClientHttpRequest(){
	}
	
	/** ������, ������� "������" �� ������� � ���� ������ ����������  */
	public ClientHttpRequest(HttpServletRequest request){
		this.readFromRequest(request);
	}
	
	/** �������� ��� ��������� �� Http ������� */
	public void readFromRequest(HttpServletRequest request){
		parameters.clear();
		Enumeration<?> names=request.getParameterNames();
		String key;
		String value;
		while(names.hasMoreElements()){
			key=(String)names.nextElement();
			value=request.getParameter(key);
			this.addParameter(new ClientHttpParameter(key, value));
		}
	}
	
	/** �������� �������� (������� �� ������� )� ������� ������� */
	public void addParameter(ClientHttpParameter clientParameter){
		this.parameters.add(clientParameter);
	}

	/** �������� ���-�� ����������, ������� ���� ��������� */
	public int getParameterCount(){
		return this.parameters.size();
	}
	
	/** �������� �������� �� ���������� ������ 
	 * @param index - ������ �������� ��������� 
	 * @return 
	 * <li>������</li>   
	 * <li>null - ���� ������ ��� ��������� </li>   
	 */
	public ClientHttpParameter getParameter(int index){
		if((index<this.parameters.size())&&(index>=0)){
			return this.parameters.get(index);
		}else{
			return null;
		}
	}
	
	/** �������� �������� �� ������ �� ����� */
	public ClientHttpParameter getParameterByName(String parameterName){
		ClientHttpParameter returnValue=null;
		for(int counter=0;counter<this.parameters.size();counter++){
			if(parameters.get(counter).getParamName().equals(parameterName)){
				returnValue=parameters.get(counter);
				break;
			}
		}
		return returnValue;
	}
}
