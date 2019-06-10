package JavaScript;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** 
 * �����, ������� �������� ������������ ������� JavaScript ������� �� �������
 * ��������� � ������ sendToServer �������� � ��������� ����������� ������ ��� �������� � ������ 
 */
public class GetBody {
	private static Logger field_logger;
	static{
		field_logger=Logger.getLogger("JavaScript.GetBody");
		field_logger.setLevel(Level.DEBUG);
		BasicConfigurator.configure();
	}
	/** ���� � �������� DisplayApplet*/
	private String path_to_display_applet="http://192.168.15.119:8080/DisplayApplet/DisplayForApplet";
	
	
	/** �������� ������ �� ������� � ������������� �� ���� <br> 
	 *  ������ �������� ������ ������� �� �������� (JavaScript) � ��������� DWR �� �������� ��� ������� ��� �����
	 *  @param fragment_from_client - ������ �������� �������� ������ ����������, ������� �������� ������ ������� ��� ��������� 
	 */
	public Fragment sendToServer(JavaScript.Fragment fragment_from_client){
		// CONNECTION:6
		// TODO SubCommand.FOR_DISPLAY (SubCommand.CommandName())������ �������, ������� ����� ���������� �� ������� � HTML  
		Fragment fragment=null;
		try{
			field_logger.debug("CONNECTION:6  function:"+fragment_from_client.getFunctionName());
			fragment=sendHttpRequest(fragment_from_client);
			field_logger.debug("CONNECTION:9 ");

/*			// �������� �� ������ ������� SHOWDEVICES 
			if(fragment_from_client.getFunctionName().equals("SHOWDEVICES")){
				field_logger.debug("CONNECTION:6  function: SHOWDEVICES");
				try{
					// ��������� CONNECTION:7->    �������� CONNECTION:8<-
					fragment=sendHttpRequest(fragment_from_client);
				}catch(Exception ex){
					field_logger.error("sendToServer exception:"+ex.getMessage());
				}
				field_logger.debug("CONNECTION:9 ");
			}else{
				field_logger.debug("Fragment.functionName is unknown"+fragment_from_client.getFunctionName());
			}
			if(fragment_from_client)
			// ��������, ���� ������ �� ��������� �� ����� ��������
			if(fragment==null){
				fragment=fragment_from_client;
			}
*/			
		}catch(Exception ex){
			field_logger.error("sendToServer Exception:"+ex.getMessage());
		}
		// CONNECTION:9
		return fragment;
	}
	
	/** ������� ������ �� ��������, ������� ������� �� ����������� �������� � ����������� ������� 
	 * @param first_value - ������ �������� � ������
	 * @param array_for_add - ������ �� �������� 
	 * */
	private String[] addStringToStringArray(String first_value,String[] array_for_add){
		ArrayList<String> temp=new ArrayList<String>();
		temp.add(first_value);
		if(array_for_add!=null){
			for(int counter=0;counter<array_for_add.length;counter++){
				temp.add(array_for_add[counter]);
			}
		}
		return temp.toArray(new String[]{});
	}
	
	/** 
	 * ������� CONNECTION:7     �������� CONNECTION:8
	 * @param fragment - ���������� CONNECTION:6 �� JavaScript(DWR) 
	 * @return
	 */
	private Fragment sendHttpRequest(Fragment fragment_6){
		Fragment fragment_9=new Fragment();
		try{
			fragment_9.setFunctionName("innerHTML");
			fragment_9.setInformationValues(new String[]{readUrlByParameter(addStringToStringArray("FUNCTIONNAME",fragment_6.getInformationKeys()),
														 					addStringToStringArray(fragment_6.getFunctionName(),fragment_6.getInformationValues())
														 					)
														 }
										    );
			fragment_9.setInformationKeys(new String[]{"MAINFRAME"});
		}catch(Exception ex){
			fragment_9.setFunctionName("ERROR");
			fragment_9.setInformationValues(new String[]{"Error DisplayForApplet"});
			fragment_9.setInformationKeys(new String[]{"MESSAGE"});
		}
		return fragment_9;
	}

	/** ��������� � �������� DisplayApplet, <br> 
	 * ���� ��������� @param keys <br>
	 * ���� �������� ���������� @param values<br>
	 * @throw ���� ��������� ������ �� ����� ��������� ������ �� �������� 
	 */
	private String readUrlByParameter(String[] keys, String[] values) throws Exception{
		String return_value="";
		return_value=Utility.UrlResource.get_http_text(path_to_display_applet, keys, values);
		return return_value;
	}
	
}
