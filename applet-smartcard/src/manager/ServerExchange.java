package manager;

import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import manager.display.Display;
import manager.transport.SubCommand;
import manager.transport.Transport;

/** �����, ������� �������� � �������� ����������� ��������-������ ������� Transport <br> 
 * <b>Singltone</b> 
 */
public class ServerExchange {
	/** ������ ���� � �������� */
	private static String field_full_url;
	
	/** Logger DEBUG */
	private static void debug(String information){
		System.out.println("ServerExchange DEBUG:"+information);
	}
	/** Logger ERROR */
	private static void error(String information){
		System.out.println("ServerExchange ERROR:"+information);
	}

	/** ���������� ������ ���� � �������� */
	public static void setURL(String url){
		field_full_url=url;
	}
	
	/** �������� �� ������ � ��������� ������ �� ������� */
	private static Transport Send(Transport transport){
		Transport return_value=new Transport();
		try{
			URL url=new URL(field_full_url);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//debug("Send:connection");
			OutputStream outputstream=connection.getOutputStream();
			//debug("Send:create object output");
			ObjectOutputStream object_output=new ObjectOutputStream(outputstream);
			//debug("Send:write object");
			object_output.writeObject(transport);
			object_output.flush();
			//debug("Send:object_output close");
			object_output.close();
			//debug("Send:outputstream close");
			outputstream.close();
			//debug("Send:connection to URL");
			connection.connect();
			//debug("Send:get input stream");
			InputStream inputstream=connection.getInputStream();
			//debug("Send:get object input stream");
			ObjectInput object_input=new ObjectInputStream(inputstream);
			//debug("Send:read object");
			return_value=(Transport)object_input.readObject();
			inputstream.close();
		}catch(Exception ex){
			error("Send:Server Exchange exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** ��������, ������� ������������ ���� ������� SubCommand
	 * @param reader - ������� ����� ����� � BonCard
	 * @param display - ���������, �������� ����� �������� ������� �� ��������� ������ � �������� 
	 * @param sub_command - �������, ������� ������ ���� ���������� 
	 * <li> ������� �� ������� �� SmartCardReader <li>
	 * <li> ������� �� ������� � ������������� (��������� ������� � Action)</li>	 
	 * */
	private static SubCommand doSubCommand(ReaderExchange reader, 
										   Display display,
										   SubCommand sub_command){
		if(sub_command.getCommandFor()==SubCommand.FOR_READER){
			debug("doSubCommand: Command for Reader:"+sub_command.getCommand());
			reader.doSubCommand(sub_command);
		}else if(sub_command.getCommandFor()==SubCommand.FOR_DISPLAY){
			debug("doSubCommand: Command for Display");
			if(display!=null){
				display.doSubCommand(sub_command);
				//sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
			}
		}else {
			error("doSubCommand: command not detect");
		}
		return sub_command;
	}
	
	/** ��������, ������� �������� ��������� ���� SubCommand � ������ Transport<br>
	 * ������� ������� ����� �� ���� ��� ��������� �� ����������� ������ �� �������
	 * @param reader - �����, � ������� �� �������� �������� � ������ ������
	 * @param display - ���������, �������� ����� �������� ������� �� ��������� ������ � ��������
	 * @param request - ���������� �� ������� ����� � ���������
	 * @return ���������� ������������ ������ ��� ������� �� ������ 
	 */
	private static Transport doSubCommandPackage(ReaderExchange reader,
												 Display display,
												 Transport request){
		Transport return_value=request;
		try{
			return_value.setDirection(Transport.TYPE_REQUEST);
			debug("doSubCommandPackage: Package consist "+return_value.getSubCommandCount()+" SubCommand");
			for(int counter=0;counter<return_value.getSubCommandCount();counter++){
				debug("do Command #"+counter);
				doSubCommand(reader,display,request.getSubCommand(counter));
			}
			debug("doSubCommandPackage OK");
			return_value.setStatus(Transport.STATUS_OK);
		}catch(Exception ex){
			error("doSubCommandPackage Exception:"+ex.getMessage());
			return_value.setStatus(Transport.STATUS_ERROR);
		}
		return return_value;
	}
	
	/** 
	 * �������, ������� �������� �������������� ����� � ��������, <br> 
	 * � ��������� ������� � ���������� ���������, <br>
	 * ������ �� ����������� Transport.status <br>
	 * <li> STATUS_OK - �������� ������� �� ����������</li>
	 * <li> STATUS_ERROR - �������� ������ ���������� </li>
	 * <li> STATUS_DONE - ���� �� ��������� ��������������� ������ </li>
	 * @param reader - ������� ���������� �� SmartCard
	 * @param display - ���������, �������� ����� �������� ������� �� ��������� ������ � ��������
	 * @param transport_request - ������, ������� ���������� �������������� ����� � ��������
	 * @throws Exception �����������, � ������ ��������� ��������� �� ����� �������, <br> 
	 * ���� �� ������ ������� STATUS_ERROR
	 */
	public static Transport exchange(ReaderExchange reader,
									 Display display,
									 Transport transport_request) throws Exception{
		debug("exchange: Send to Server action name:"+transport_request.getActionName());
		/** ������, ������� �������� ������ ������� */
		Transport transport_response;
		
		transport_response=Send(transport_request);
		debug("exchange:First response: action:"+transport_response.getActionName());
		debug("exchange:First response: Direction:"+transport_response.getDirection());
		debug("exchange:First response: Status:"+transport_response.getStatus());
		debug("exchange:First response: SubCommandCount:"+transport_response.getSubCommandCount());

		int iteration_count=0;
		// �������������� ����� � ��������, ���� �������� - ���������� ��� ������, ������� ���� ������
		while(  (transport_response.getDirection()==Transport.TYPE_RESPONSE)
			  &&(transport_response.getStatus()==Transport.STATUS_OK)
			  ){
			debug("exchange: Iteration number:"+iteration_count);
			debug("exchange: doSubCommandPackage");
			debug("exchange: SubCommandCount:"+transport_response.getSubCommandCount());
			debug("exchange: SubCommand getCommand:"+transport_response.getSubCommand(0).getCommand());
			debug("exchange: SubCommand getParameter:"+transport_response.getSubCommand(0).getParameter());
			debug("exchange: doSubCommandPackage");
			transport_request=doSubCommandPackage(reader,display, transport_response);
			transport_request.setDirection(Transport.TYPE_REQUEST);
			debug("exchange: Send to server:"+iteration_count);
			transport_response=Send(transport_request);
			iteration_count++;
			
			/*if(iteration_count>3){
				break;
			}*/
			debug("goto next iteration");
		}
		// �������������� ����� � �������� ��������
		if(transport_response.getStatus()==Transport.STATUS_DONE){
			debug("exchange: DONE ");
			// ����� ��� ������� ������, ������� ���������� ������� ������������ � ��������� ���� Action 
			transport_request=doSubCommandPackage(reader,display, transport_response);
			return transport_response;
		}else if(transport_response.getStatus()==Transport.STATUS_ERROR){
			error("exchange: ERROR Server answer Error");
			throw new Exception("exchange: ERROR Server answer Error");
		}else {
			error("exchange: UNKNOWN Server response");
			throw new Exception("exchange: UNKNOWN Server response");
		}
	}
	
	
	/** ��������������� �������, ������� �������� �������� ����� �� ������� <br> 
	 * <b>�������� �����������: </b><br>
	 * <table border=1>
	 * <tr><td><center><b>Client</b></center></td> <td><center><b>Server</b></center></td></tr>
	 * <tr><td>Transport.TYPE_REQUEST<br>� ����� �������� (new SubCommand("GET_SERIAL_NUMBER",null)) </td> 
	 *                     <td>Transport.STATUS_OK&&Transport.TYPE_RESPONSE<br>���� ������, ������� ����� ���������</td></tr>
	 * <tr><td>Transport.TYPE_REQUEST<br>c ������������� ��������� SubCommand.DATA_FOR_RESPONSE <br> ��� �������� SubCommand.DATA_ORIGINAL and SubCommand.parameter==null</td>
	 *                     <td> Transport.STATUS_OK&&Transport.TYPE_RESPONSE<br>Transport.information - �����</td></tr>
	 *</table>
	 */
/*	
 * public static String getSerialNumber(BonCard context){
		String return_value=null;
		debug("getSerialNumber: send package to server ");
		Transport transport=new Transport();
		transport.setActionName("GETSERIALNUMBER");
		transport.setDirection(Transport.TYPE_REQUEST);
		debug("getSerialNumber: server sended transport");
		debug("getSerialNumber:transport: Action:"+transport.getActionName());
		debug("getSerialNumber:transport: Direction:"+transport.getDirection());
		debug("getSerialNumber:transport: Status:"+transport.getStatus());
		debug("getSerialNumber:transport: SubCommandCount:"+transport.getSubCommandCount());
		
		try{
			transport=exchange(context,transport);
			debug("getSerialNumber: Action is Done");
			return transport.getInformationTextByIndex(0);
		}catch(Exception ex){
			error("getSerialNumber: Error in Exchange with server "+ex.getMessage());
		}
		return return_value;
	}
*/
/*
		Transport request=Send(transport);
		debug("getSerialNumber: server sended request");
		debug("getSerialNumber:request: Direction:"+request.getDirection());
		debug("getSerialNumber:request: Status:"+request.getStatus());
		debug("getSerialNumber:request: SubCommandCount:"+request.getSubCommandCount());
		debug("getSerialNumber:request: SubCommand getCommand:"+request.getSubCommand(0).getCommand());
		debug("getSerialNumber:request: SubCommand getParameter:"+request.getSubCommand(0).getParameter());
		if( (request.getDirection()==Transport.TYPE_RESPONSE)
		  &&(request.getStatus()==Transport.STATUS_OK)){
			debug("getSerialNumber: working task"); 
			transport=doSubCommandPackage(context,request);
			debug("getSerialNumber: send package to server");
			transport.setDirection(Transport.TYPE_REQUEST);
			debug("getSerialNumber: server sended transport");
			debug("getSerialNumber:transport: Direction:"+transport.getDirection());
			debug("getSerialNumber:transport: Status:"+transport.getStatus());
			debug("getSerialNumber:transport: SubCommandCount:"+transport.getSubCommandCount());
			debug("getSerialNumber:transport: SubCommand getCommand:"+transport.getSubCommand(0).getCommand());
			debug("getSerialNumber:transport: SubCommand getParameter:"+transport.getSubCommand(0).getParameter());
			
			request=Send(transport);
			debug("getSerialNumber:request: Direction:"+request.getDirection());
			debug("getSerialNumber:request: Status:"+request.getStatus());
			debug("getSerialNumber:request: SubCommandCount:"+request.getSubCommandCount());
			debug("getSerialNumber:request: SubCommand getCommand:"+request.getSubCommand(0).getCommand());
			debug("getSerialNumber:request: SubCommand getParameter:"+request.getSubCommand(0).getParameter());
			
			if( (request.getDirection()==Transport.TYPE_RESPONSE)
			   &&(request.getStatus()==Transport.STATUS_OK)){
				debug("getSerialNumber: Server response is STATUS_OK");
				if(request.getInformationCount()>0){
					debug("getSerialNumber:���� ���������� ��� ����������� �� ��������");
					return_value=request.getInformationTextByIndex(0);
				}else{
					debug("getSerialNumber:��� ���������� ��� �����������");
					return_value="";
				}
			}else{
				debug("getSerialNumber:������ ������ ���� ������ �� ������ �������� ");
				error("getSerialNumber: Transport.STATUS_ERROR");
				if(request.getInformationCount()>0){
					return_value=request.getInformationTextByIndex(0);
				}else{
					return_value="";
				}
			}
		}else{
			debug("getSerialNumber: ������ ������ ���� ������ �� ������ �������� ");
			error("getSerialNumber: Transport.STATUS_ERROR");
			if(request.getInformationCount()>0){
				return_value=request.getInformationTextByIndex(0);
			}else{
				return_value="";
			}
		}
*/			
}
