package manager.transport;

import java.io.Serializable;
import java.util.ArrayList;


/** ������ ����� �������� ��������, ������� ������ ��������� �������, ���� ���� �������� �� ����������*/
public class SubCommand  implements Serializable{
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/** command name 
	 * ���������� ������������� ������� 
	 */
	private String field_command;
	/** �������� (in/out) byte[] ��� ��������� ������*/
	private byte[] field_parameter=null;
	/** �������� (in/out) int ��� ��������� ������ - ���� ��� �������� ������� ����������� ������, ����� �������� � getParameter()*/
	private int field_parameter_int;
	/** �������� (in/out) String ��� ��������� ������*/
	private String field_parameter_string;
	
	/** �������� ����� �������������� ���������� */
	private ArrayList<String> field_parameter_keys=null;
	/** �������� �������� �������������� ���������� */
	private ArrayList<String> field_parameter_values=null;
	
	/** ����, ������� ������� � ���, ��� ������ � ������� ���� �������� <br>
	 * ���������� ����� ������ ���� �������, � ������ ������ ����� � ��� �� ������,
	 * �� � ������ ��������� ��������� ������ ������ ����
	 */
	private int field_data_description=DATA_ORIGINAL;
	
	/** ����, ������� ������� � ��� ��� � ������� ��������� ������������ ������ */
	public static int DATA_ORIGINAL=0;
	/** ����, ������� ������� � ���, ��� � ������� ��������� ��� ���������� ������ */
	public static int DATA_FOR_RESPONSE=1;

	/** ����, ������� ������� � �������������� ������� � ������� � CardReader*/
	public static int FOR_READER=0;
	/** ����, ������� ������� � �������������� ������� ��� ����������� ������ ������������*/
	public static int FOR_DISPLAY=1;
	/** ���������� ����� ������� ��� ������ ������������� ������� � ������ ActionStep �� ������� �������*/
	private int field_unique_index=(-1);
	
	/** ����, ������� ������� � �������������� ������� :
	 * <li> ������� � Reader-�� </li>, 
	 * <li> ��� ���������� ������ �������</li> 
	 */
	private int field_command_for=FOR_READER;
	
	/**
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * @param command_name
	 * @param field_parameter ��������, ������� ����� �������� � ���������� 
	 */
	public SubCommand(String command_name,byte[] parameter){
		this.setCommand(command_name);
		this.setParameter(parameter);
	}
	
	/** 
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * @param unique_index ���������� ������ � ������ ActionStep  
	 * @param command_name
	 * @param field_parameter ��������, ������� ����� �������� � ���������� 
	 */
	public SubCommand(int unique_index,String command_name,byte[] parameter){
		this(command_name,parameter);
		this.setUniqueIndex(unique_index);
	}

	/**
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * @param command_name ��� �������
	 * @param field_parameter ��������, ������� ����� �������� � ����������
	 * @param flag_for - ����, ��� ���� ������������� ������ ������� - ��� ������� � Reader-��, ���� �� ��� ������� � ����������� ������������ 
	 */
	public SubCommand(String command_name,byte[] parameter,int flag_for){
		this.setCommand(command_name);
		this.setParameter(parameter);
		this.setCommandFor(flag_for);
	}
	/** 
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * @param unique_index ���������� ������ � ������ ActionStep  
	 * @param command_name ��� �������
	 * @param field_parameter ��������, ������� ����� �������� � ����������
	 * @param flag_for - ����, ��� ���� ������������� ������ ������� - ��� ������� � Reader-��, ���� �� ��� ������� � ����������� ������������ 
	 */
	public SubCommand(int unique_index,String command_name,byte[] parameter,int flag_for){
		this.setCommand(command_name);
		this.setParameter(parameter);
		this.setCommandFor(flag_for);
		this.setUniqueIndex(unique_index);
	}
	 

	/**
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * @param command_name ��� �������
	 * @param field_parameter_string ��������� �������� ��� �������
	 * @param flag_for - ����, ��� ���� ������������� ������ ������� - ��� ������� � Reader-��, ���� �� ��� ������� � ����������� ������������ 
	 */
	public SubCommand(String command_name,String parameter_string,int flag_for){
		this.setCommand(command_name);
		this.setParameterString(parameter_string);
		this.setCommandFor(flag_for);
	}

	/**
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * @param unique_index ���������� ������ � ����� ActionStep  
	 * @param command_name ��� �������
	 * @param field_parameter_string ��������� �������� ��� �������
	 * @param flag_for - ����, ��� ���� ������������� ������ ������� - ��� ������� � Reader-��, ���� �� ��� ������� � ����������� ������������ 
	 */
	public SubCommand(int unique_index,String command_name,String parameter_string,int flag_for){
		this.setCommand(command_name);
		this.setParameterString(parameter_string);
		this.setCommandFor(flag_for);
		this.setUniqueIndex(unique_index);
	}
	
	/**
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * �����������, ������� ������������� ������������ ��������� ���������  
	 * @param command_name ��� �������
	 * @param flag_for - ����, ��� ���� ������������� ������ ������� - ��� ������� � Reader-��, ���� �� ��� ������� � ����������� ������������ 
	 */
	public SubCommand(String command_name,int flag_for){
		this.setCommand(command_name);
		this.setCommandFor(flag_for);
	}
	/**
	 * ����������� �������, ������� ��������� �������� �� ������� �������,
	 * � ���������� ������ �� �������� �� ������� �������
	 * �����������, ������� ������������� ������������ ��������� ���������
	 * @param unique_index ���������� ������ � ����� ActionStep  
	 * @param command_name ��� �������
	 * @param flag_for - ����, ��� ���� ������������� ������ ������� - ��� ������� � Reader-��, ���� �� ��� ������� � ����������� ������������ 
	 */
	public SubCommand(int unique_index,String command_name,int flag_for){
		this.setCommand(command_name);
		this.setCommandFor(flag_for);
		this.setUniqueIndex(unique_index);
	}
	
	/** ���������� ���������� ������ � ������ Action*/
	public void setUniqueIndex(int value){
		this.field_unique_index=value;
	}
	/** �������� ���������� ������ � ������ Action*/
	public int getUniqueIndex(){
		return this.field_unique_index;
	}
	
	/** ���������� ��� ���� ������������� ������ ������� (Reader ��� Display)*/
	public void setCommandFor(int value){
		this.field_command_for=value;
	}
	/** �������� ��� ���� ������������� ������ ������� (Reader ��� Display)*/	
	public int getCommandFor(){
		return this.field_command_for;
	}
	
	/** ���������� �������� String*/
	public void setParameterString(String value){
		this.field_parameter_string=value;
	}
	/** �������� �������� String */
	public String getParameterString(){
		return this.field_parameter_string;
	}
	
	/** ���������� �������� int*/
	public void setParameterInt(int value){
		this.field_parameter_int=value;
	}
	/** ��������� �������� int*/
	public int getParameterInt(){
		return this.field_parameter_int;
	}
	
	/** ���������� ���� ��� �������� ������ � ������ */
	public void setDataDescription(int value){
		this.field_data_description=value;
	}
	/** �������� ���� ��� �������� ������ � ������ */
	public int getDataDescription(){
		return this.field_data_description;
	}
	
	/** ���������� �������� ������� */
	public void setParameter(byte[] value){
		this.field_parameter=value;
	}
	/** �������� �������� ������� */
	public byte[] getParameter(){
		return this.field_parameter;
	}
	
	/** ���������� ���������� ��� ��� ������� */
	public void setCommand(String value){
		this.field_command=value;
	}
	
	/** �������� ���������� ��� ��� ������� */
	public String getCommand(){
		return this.field_command;
	}

	/** �������� ���-�� ������� � �������������� ���������� */
	public int getInformationCount(){
		if(this.field_parameter_keys!=null){
			return this.field_parameter_keys.size();
		}else{
			return 0;
		}
	}
	
	/** �������� ��� ����� */
	public String[] getInformationKeys(){
		if(this.field_parameter_keys!=null){
			return this.field_parameter_keys.toArray(new String[]{});
		}else{
			return new String[]{};
		}
	}
	/** �������� ��� �������� */
	public String[] getInformationValues(){
		if(this.field_parameter_values!=null){
			return this.field_parameter_values.toArray(new String[]{});
		}else{
			return new String[]{};
		}
	}
	
	/** �������� ���� �� ����������� �����*/
	public String getInformationKey(int index){
		if(this.field_parameter_keys!=null){
			if(index<this.field_parameter_keys.size()){
				return this.field_parameter_keys.get(index);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	/** �������� �������� �� ����������� ������*/
	public String getInformationValue(int index){
		if(this.field_parameter_values!=null){
			if(index<this.field_parameter_values.size()){
				return this.field_parameter_values.get(index);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	/** �������� �������� �� ����� ����� */
	public String getInformationValueByKey(String key){
		if(this.field_parameter_keys!=null){
			int index=this.field_parameter_keys.indexOf(key);
			if(index>=0){
				return this.field_parameter_values.get(index);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	/** �������� �������� � ��������� ���������� */
	public void putInformation(String key, String value){
		// �������, ���� ��� �� ������� - �� ��������� ��� �������� ������� � ����� � ��������� ��� ������� 
		if(this.field_parameter_keys==null){
			this.field_parameter_keys=new ArrayList<String>();
			this.field_parameter_values=new ArrayList<String>();
		};
		// ����������� �� ������� ������� �����
		int index=this.field_parameter_keys.indexOf(key);
		if(index>=0){
			// ��������
			this.field_parameter_values.set(index, value);
		}else{
			// �������� ��� �����
			this.field_parameter_keys.add(key);
			this.field_parameter_values.add(value);
		}
	}
	
	/** �������� ��� �������� */
	public void clearInforamtion(){
		if(this.field_parameter_keys!=null){
			this.field_parameter_keys.clear();
			this.field_parameter_values.clear();
		}
	}
}
