package manager.transport;

import java.io.Serializable;
import java.util.ArrayList;

/** �����, ������� �������� SubCommand ������� ����� ��������� ��� ������� ���� ���������,
 * � ����������� �� ����������� (Direction)
 */
public class Transport implements Serializable{
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 3L;
	/** type of command - REQUEST ����� �� ������� */
	public static int TYPE_REQUEST=0;
	/** type of command - RESPONSE ����� �� ������� */
	public static int TYPE_RESPONSE=1;
	/** ����������� ������� - ��� �������� ������� - �������� ��� ������� */
	private int field_direction=0;

	/** ������ ����������� ������ OK - ����� ���������� �����*/
	public static int STATUS_OK=0;
	/** ������ ����������� ������ ERROR - ����������� ��������������� ������*/
	public static int STATUS_ERROR=1;
	/** ������ ����������� ����� DONE - ��� ����������� �������� �� Action ��������� <br> 
	 * (������ ��� ������� - ���� �� ��������� ������ � �������� - ����������� ���������� ����������)
	 */
	public static int STATUS_DONE=2;
	/** ������ �������� ������ */
	private int field_status=0;
	/** ���������� ��� ��� �������� ACTION*/
	private String field_action="";
	/** ���������� ��� ��� ������� ������ */
	private String field_session_id=null;
	/** SubCommand Store*/
	private ArrayList<SubCommand> field_store=new ArrayList<SubCommand>();

	/** Information Name ������ �������������� �������� - ���� ���������� */
	private ArrayList<String> field_information_key=new ArrayList<String>();
	/** Information Description ������ �������������� �������� - ���������� ���������� */
	private ArrayList<String> field_information_text=new ArrayList<String>();
	
	/** �������� ���������� ������������� ���������� ������� � ������� - SessionID*/
	public String getSessionId(){
		return this.field_session_id;
	}
	/** ���������� ���������� ������������� ���������� ������� � ������� - SessionID*/
	public void setSessionId(String value){
		this.field_session_id=value;
	}
	/** �������� �����, ���� �� � ������ ������� ���������� ������������� ������ ?*/
	public boolean isSessionIdPresent(){
		boolean return_value=true;
		if(this.field_session_id==null){
			return false;
		};
		if(this.field_session_id.equals("")){
			return false;
		}
		return return_value;
	}
	
	/** �������� SubCommand � �������  
	 * append command to store*/
	public void addSubCommand(SubCommand value){
		this.field_store.add(value);
	}
	/** 
	 * �������� ��� �������� Action, ������� ����������� � ������ ������  
	 */
	public String getActionName(){
		return this.field_action;
	}
	/**
	 * ���������� ��� �������� Action
	 */
	public void setActionName(String action){
		this.field_action=action;
	}
	
	/** �������� SubCommand �� �������
	 * @param value SubCommand by index
	 * @return null, if index is of bounds 
	 */
	public SubCommand getSubCommand(int value){
		if(value<this.getSubCommandCount()){
			return this.field_store.get(value);
		}else{
			return null;
		}
	}
	/** 
	 * �������� SubCommand �� ����������� ������� � ������ ActionStep
	 * @param unique_index ���������� ������ � ������ ActionStep
	 * @return null, ���� �� ���������� ������� �� ������ SubCommand
	 */
	public SubCommand getSubCommandByUniqueIndex(int unique_index){
		SubCommand return_value=null;
		for(int counter=0;counter<this.getSubCommandCount();counter++){
			if(this.getSubCommand(counter).getUniqueIndex()==unique_index){
				return_value=this.getSubCommand(counter);
				break;
			}
		}
		return return_value;
	}
	
	/** @return command count from store*/
	public int getSubCommandCount(){
		return this.field_store.size();
	}
	
	/** set direction */
	public void setDirection(int value){
		this.field_direction=value;
	}
	/** get direction*/
	public int getDirection(){
		return this.field_direction;
	}
	/** set status */
	public void setStatus(int status){
		this.field_status=status;
	}
	/** get status*/
	public int getStatus(){
		return this.field_status;
	}
	
	/** �������� ����� ���������� � ����, ������� �������� ���������� �� ������� 
	 * @param key ���� ��� ������
	 * @param value ����� ������
	 */
	public void addInformation(String key, String value){
		this.field_information_key.add(key);
		this.field_information_text.add(value);
	}
	/** ������� ���������� � ����, ������� �������� ���������� �� �������
	 * @param key
	 */
	public boolean removeInformationByKey(String key){
		boolean return_value=false;
		int index_for_remove=this.field_information_key.indexOf(key);
		if(index_for_remove>=0){
			this.field_information_key.remove(index_for_remove);
			this.field_information_text.remove(index_for_remove);
			return_value=true;
		}
		return return_value;
	}
	/** ������� ���������� � ����, ������� �������� ���������� �� �������
	 * @param index
	 */
	public boolean removeInformationByIndex(int index){
		boolean return_value=false;
		if((index>=0)&&(index<this.getInformationCount())){
			this.field_information_key.remove(index);
			this.field_information_text.remove(index);
			return_value=true;
		}
		return return_value;
	}
	
	/** �������� ���������� ������� � ���� ���������� */
	public int getInformationCount(){
		return this.field_information_key.size();
	}
	/** �������� ��� ���������� */
	public void clearInformation(){
		this.field_information_text.clear();
		this.field_information_key.clear();
	}
	/** �������� ���������� �� ����� ����� 
	 * @param key ��� �����
	 */
	public String getInformationTextByKey(String key){
		String return_value=null;
		int index=this.field_information_key.indexOf(key);
		if(index>=0){
			return_value=this.field_information_text.get(index);
		}
		return return_value;
	}
	/** �������� Description ���������� �� ����������� ������ � ������ */
	public String getInformationTextByIndex(int index){
		return this.field_information_text.get(index);
	}
	/** �������� Key ���������� �� ����������� ����� */ 
	public String getInformationKeyByIndex(int index){
		try{
			return this.field_information_key.get(index);
		}catch(Exception ex){
			return "";
		}
		
	}
	
	/** ���������� ��������� � �������������� ��������� <br> 
	 *  ����� � �������������� ��������� <br>
	 *  ������� ������� ������
	 */
	public void reset(){
		this.field_direction=0;
		this.field_status=0;
		this.field_store.clear();
		this.clearInformation();
	}
	
	/** �������� ��� ������� ������ */
	public void clearSubCommand(){
		this.field_store.clear();
	}
	
	/** �������� ��� ����� ��� ���� Information � ���� String[]*/
	public String[] getInformationKeys(){
		return this.field_information_key.toArray(new String[]{});
	}
	/** �������� ��� ��������  ��� ���� Information � ���� String[]*/
	public String[] getInformationValues(){
		return this.field_information_text.toArray(new String[]{});
	}
}
