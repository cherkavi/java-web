package manager;

import com.linuxnet.jpcsc_client.BonCard;
import com.linuxnet.jpcsc_wrap.util.TextUtils;

import manager.transport.SubCommand;

/**
 * Класс, который отвечает за обработку SubCommand, которые имеют функции по работе с карточкой  
 * @author cherkashinv
 */
public class ReaderExchange {
	private BonCard field_bon_card=null;

    /** 
     * метод для отладки информации
     */
    protected static void debug(String information){
        System.out.print("ReaderExchange ");
        System.out.print("DEBUG: ");
        System.out.println(information);
    }
    protected static void error(String information){
    	System.out.print("ReaderExchange ");
    	System.out.print("ERROR: ");
    	System.out.println(information);
    }
	
	/**
	 * 
	 * @param bon_card - контекст текущего соединения с карточкой
	 */
	public ReaderExchange(BonCard bon_card){
		this.field_bon_card=bon_card;
	}
	
	/** 
	 * метод, который должен отработать действие, которое заложено в SubCommand 
	 */
	public void doSubCommand(SubCommand sub_command){
		// команды по общению со SmartCardReader-ом
		debug("doSubCommand interceptor's begin:");
			// TODO SubCommand.FOR_READER: Applet Interceptors (SubCommand.CommandName()) место, где необходимо проводить проверку на дополнительные 
				// byte[]  SENDCOMMAND(byte[])
			if(sub_command.getCommand().equalsIgnoreCase("SENDCOMMAND")){
				debug("doSubCommand: get command SENDCOMMAND");
				try{
					debug("SENDCOMMAND >>>>:"+TextUtils.hexDump(sub_command.getParameter()));
					sub_command.setParameter(field_bon_card.sendCommand(sub_command.getParameter()));
					debug("SENDCOMMAND <<<<:"+TextUtils.hexDump(sub_command.getParameter()));
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
				// byte[] FETCHATR()
			if(sub_command.getCommand().equalsIgnoreCase("FETCHATR")){
				debug("doSubCommand: get command FETCHATR");
				try{
					sub_command.setParameter(field_bon_card.fetchATR());
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
				// byte[] GETCARDDATA(int)
			if(sub_command.getCommand().equalsIgnoreCase("GETCARDDATA")){
				debug("doSubCommand: get command GETCARDDATA");
				try{
					sub_command.setParameter(field_bon_card.getCardData(sub_command.getParameterInt()));
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
				// byte[] READBINARY(int)
			if(sub_command.getCommand().equalsIgnoreCase("READBINARY")){
				debug("doSubCommand: get command READBINARY");
				try{
					sub_command.setParameter(field_bon_card.readBinary(sub_command.getParameterInt()));
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
				// byte[] READRECORD(int)
			if(sub_command.getCommand().equalsIgnoreCase("READRECORD")){
				debug("doSubCommand: get command READRECORD");
				try{
					sub_command.setParameter(field_bon_card.readRecord(sub_command.getParameterInt()));
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
				// byte[] RESETREADER
			if(sub_command.getCommand().equalsIgnoreCase("RESETREADER")){
				debug("doSubCommand: get command RESETREADER");
				try{
					sub_command.setParameter(field_bon_card.resetReader());
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
				// byte[] READBINARYFILE(byte[])
			if(sub_command.getCommand().equalsIgnoreCase("READBINARYFILE")){
				debug("doSubCommand: get command READBINARYFILE");
				try{
					sub_command.setParameter(field_bon_card.readBinaryFile(sub_command.getParameter()));
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
			
				// byte[] SELECTFILE(int,String)
			if(sub_command.getCommand().equalsIgnoreCase("SELECTFILE")){
				debug("doSubCommand: get command SELECTFILE");
				try{
					sub_command.setParameter(field_bon_card.selectFile(sub_command.getParameterInt(), sub_command.getParameterString()));
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			};
			
				// int, String[] GETALLDEVICES
			if(sub_command.getCommand().equalsIgnoreCase("GETALLDEVICES")){
				debug("doSubCommand: get command GETALLDEVICES");
				try{
					String[] devices=field_bon_card.getReaders();
					sub_command.setParameterInt(devices.length);
					sub_command.clearInforamtion();
					for(int counter=0;counter<devices.length;counter++){
						sub_command.putInformation((new Integer(counter)).toString(), devices[counter]);
						//debug((new Integer(counter)).toString()+"===>"+devices[counter]);
					}
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			}
			if(sub_command.getCommand().equalsIgnoreCase("CONNECTTO")){
				debug("doSubCommand: get command CONNECTTO");
				try{
					if(field_bon_card.connectToDeviceByName(sub_command.getParameterString())){
						//sub_command.setParameterString(sub_command.getParameterString());
					}else{
						sub_command.setParameterString("");
					}
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			}
			if(sub_command.getCommand().equalsIgnoreCase("DISCONNECT")){
				debug("doSubCommand: get command DISCONNECT");
				try{
					field_bon_card.disconnect();
					sub_command.setParameterString("");
					sub_command.setDataDescription(SubCommand.DATA_FOR_RESPONSE);
				}catch(Exception ex){
					// данные не отработаны - передача пакета с DATA=null, а флагом состояния - DATA_ORIGINAL
					sub_command.setDataDescription(SubCommand.DATA_ORIGINAL);
				}
			}
			
		debug("doSubCommand interceptor's end:");
		
	}
}
