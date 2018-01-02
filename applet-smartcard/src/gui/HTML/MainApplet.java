package gui.HTML;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;


import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import manager.transport.SubCommand;
import manager.transport.Transport;

import com.linuxnet.jpcsc_client.BonCard;
import com.linuxnet.jpcsc_wrap.util.LoadLibraryFromJar;

import manager.ReaderExchange;
import manager.ServerExchange;

public class MainApplet extends JApplet{
	
	/** файл, который содержит ссылку на путь к файлу для сохранения промежуточного значения BonCard*/
	private String field_path_to_storage_bon_card="tempvalue.bin";
	BonCard field_BonCard=null;
	private static PrintWriter log;
	static{
		try{
			log=new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("c:\\log_applet.txt"))));
		}catch(Exception ex){}
	}
	
	
	/** <b>записать объект в файл</b> 
	 * @param path_to_file - путь к файлу, куда нужно записать данный объект
	 * @param parent - объект, который должен быть записан 
	 * */
	private boolean writeObject_to_file(String path_to_file,Object object){
		boolean return_value=false;
		try{
			System.out.println("writeObject: create file");
			File f=new File(path_to_file);
			System.out.println("writeObject: create FileOutputStream");
			FileOutputStream fos=new FileOutputStream(f);
			System.out.println("writeObject: create ObjectOutputStream");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			System.out.println("writeObject: write Object");
			oos.writeObject(object);
			System.out.println("writeObject: ObjectOutputStream close");
			oos.close();
			System.out.println("writeObject: FileOuputStream close");
			fos.close();
			return_value=true;
		}catch(Exception ex){
			System.out.println("writeObject: Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** <b>прочитать объект из файла</b>
	 * @param path_to_file - путь к файлу, в котором записан объект
	 * @return прочитанный объект 
	 * */
	private Object readObject_from_file(String path_to_file){
		Object return_value=null;
		try{
			System.out.println("readObject: create file");
			File f=new File(path_to_file);
			System.out.println("readObject: create FileInputStream");
			FileInputStream fos=new FileInputStream(f);
			System.out.println("readObject: create ObjectInputStream");
			ObjectInputStream oos=new ObjectInputStream(fos);
			System.out.println("readObject: read Object");
			return_value=oos.readObject();
			System.out.println("readObject: ObjectInputStream close");
			oos.close();
			System.out.println("readObject: FileInputStream close");
			fos.close();
		}catch(Exception ex){
			System.out.println("readObject: Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	/** @return получить полный путь к месту, где храниться объект BonCard на диске*/
	private String getPathToStorageBonCard(){
		debug("getPathToStorageBonCard:"+this.field_path_to_storage_bon_card);
		return System.getProperty("java.io.tmpdir")+this.field_path_to_storage_bon_card;
	}
	
	/** 
	 * сохранить BonCard на диске
	 */
	private boolean saveBonCardToDisk(BonCard object){
		return this.writeObject_to_file(this.getPathToStorageBonCard(), object);
	}
	
	/** 
	 * получить BonCard c диска или null в случае ошибки
	 */
	private BonCard readBonCardFromDisk(){
		Object return_value=this.readObject_from_file(this.getPathToStorageBonCard());
		if(return_value!=null){
			debug("readBonCardFromDisk is Object");
			BonCard return_bon_card=null;
			try{
				return_bon_card=(BonCard)return_value;
				error("readBonCradFromDisk is OK");
			}catch(Exception ex){
				// return null;
				error("readBonCardFromDisk is not set in BonCard ");
			}
			return return_bon_card;
		}else{
			debug("readBonCardFromDisk is null");
			return null;
		}
	}

	/** получить текщую BonCard */
	private BonCard getBonCard(){
		debug("getBonCard");
		//this.field_BonCard=this.readBonCardFromDisk();
		if(this.field_BonCard==null){
			debug("BonCard is null - create it");
			this.field_BonCard=new BonCard();
		}
		return this.field_BonCard;
	}
	
	/** сохранить текущую BonCard*/
	private boolean setBonCard(){
		debug("save BonCard into Disk");
		return true; // this.saveBonCardToDisk(this.field_BonCard);
	}
	
	/** очистить BonCard*/
	private void clearBonCard(){
		debug("clear BonCard");
		this.field_BonCard=null;
		try{
			//File f=new File(this.getPathToStorageBonCard());
			//f.delete();
		}catch(Exception ex){};
	}
	
	/** текущая BonCard (карта в SmartReader-е)*/
	//static BonCard field_boncard;
	static boolean flag_is_loaded=false;
	static{
		//ServerExchange.setURL("http://127.0.0.1:8080/BonusClub/CardServer");
		ServerExchange.setURL("http://127.0.0.1:8080/Card_Reader_Server_1/CardServer");
		//field_boncard=new BonCard();
		//debug("STATIC: "+field_boncard);
	}
	
	private static void load_library(){
		debug("load_library: trying load:begin");
		if(flag_is_loaded==false){
			//flag_is_loaded=LoadLibraryFromJar.loadRemoteDriver("http://127.0.0.1:8080/BonusClub/applet/");
			flag_is_loaded=LoadLibraryFromJar.loadRemoteDriver("http://127.0.0.1:8080/DisplayApplet/");
			debug("load_library is load:"+flag_is_loaded);
		}
		//debug("load_library: BonCard:"+field_boncard);
		debug("load_library: trying load:end");
	}
	
	private static void debug(String information){
		System.out.print("MainApplet: ");
		System.out.print("  DEBUG: ");
		System.out.println(information);
		if(log!=null){
			log.print("MainApplet: ");
			log.print("  DEBUG: ");
			log.println(information);
			log.flush();
		}
		
	}
	private static void error(String information){
		System.out.print("MainApplet: ");
		System.out.print("  ERROR: ");
		System.out.println(information);
		if(log!=null){
			log.print("MainApplet: ");
			log.print("  ERROR: ");
			log.println(information);
			log.flush();
		}
	}
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private javax.swing.JTextArea field_log=new JTextArea();
	
	public void init(){
		debug("init:begin");
		//super.init();
		//TODO: вставить для динамической загрузки библиотеки
		load_library();
		initComponents();
		debug("init:end");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run(){
				try{
					File library_file_again=new File(LoadLibraryFromJar.libraryPath);
					if(library_file_again.delete()){
						debug("ShutdownHook: file is deleted");
					}else{
						debug("ShutdownHook: file NOT deleted");
					}
				}catch(Exception ex){}
			}
		}));
		
	}
	
	private void initComponents(){
		this.setLayout(new BorderLayout());
		this.add(field_log,BorderLayout.CENTER);
		JButton button_connect=new JButton("connect");
		//this.add(button_connect,BorderLayout.NORTH);
		button_connect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				connect();
			}
		});
		this.field_log.append("AppletStarted\n");
		this.field_log.append(LoadLibraryFromJar.libraryPath+"\n");
		this.field_log.append(LoadLibraryFromJar.getUniqueChar(5));
		JOptionPane.showMessageDialog(this, "started");
	}
	
	private void connect(){
		//debug("action: Create BonCard");
		//field_boncard=new BonCard();
		debug("action: try to connect to Reader: "+this.getReaderList()[0]);
		this.connectToReaderByName(this.getReaderList()[0]);
	}
	
	// получение всех установленных Reader-ов в системе
	private String[] getReaderList(){
		debug("getReaderList: begin");
		//return this.field_boncard.getReaders();
		return this.getBonCard().getReaders();
	}
	
	//
	private boolean connectToReaderByName(String reader_name){
		boolean return_value=false;
		try{
			debug("Попытка подключения к Reader-у по выбранному имени ");
			if(this.getBonCard().connectToDeviceByName(reader_name)==true){
				debug("Connection is OK");
			}else{
				error("Error in connection:"+reader_name);
			}
		}catch(Exception ex){
			error("connectToReaderByName:"+ex.getMessage());
		}
		return return_value;
	}
	
	
	

	
	/** отключить BonCard (Card+SmartCardReader)*/
	private void disconnect(){
		try{
			this.getBonCard().disconnect();
			this.clearBonCard();
		}catch(Exception ex){
			error("disconnect ERROR:"+ex.getMessage());
		}
	}
	
	public void destroy(){
		disconnect();
		System.gc();
		try{
			File library=new File(LoadLibraryFromJar.libraryPath);
			if(library.exists()){
				if(library.delete()){
					// delete is true
					debug("library is DELETED");
				}else{
					debug("library delete is false");
					library.deleteOnExit();
				}
			}else{
				debug("file not found");
			}
		}catch(Exception ex){
		}
		
		try{
			if(log!=null){
				log.flush();
				log.close();
			}
		}catch(Exception ex){}

		//this.setBonCard();
	}
	
	private ArrayList<String> field_parameter_key=new ArrayList<String>();
	private ArrayList<String> field_parameter_value=new ArrayList<String>();
	
	/** очистка параметров */
	public void action_clear_parameter(){
		field_parameter_key.clear();
		field_parameter_value.clear();
	}
	/** добавить <key,value> в блок параметров */
	public void action_add_parameter(String key, String value){
		debug("action_add_parameter:  key:"+key+"  value: "+value);
		field_parameter_key.add(key);
		field_parameter_value.add(value);
	}
	
	/** получить запрос на Action <br> CONNECTION:2 <br> 
	 * метод, который может быть вызван JavaScript для инициализации обработки
	 * @param action_name - уникальное имя Action
	 * @param parameter - параметры, которые необходимы для данного соединения 
	 */
	public void action(String   action_name){
		// точка входа в апплет из JavaScript
		debug("action: started parameter's");
		
		debug("ActionName:"+action_name);
/*		{
			try{
				for(int counter=0;counter<field_parameter_key.size();counter++){
					debug(counter+"   <key:>"+field_parameter_key.get(counter)+"   <value:>"+field_parameter_value.get(counter));
				}
			}catch(Exception ex){
				debug("parameter_key is null");
			}
		}
*/
		debug("action: send package to server ");
		Transport transport=new Transport();
		// fill Transport.actionName
		transport.setActionName(action_name);
		try{
			// TODO: IMPORTANT параметры первого соединения лежат в Transport.Information
			// fill Transport.information
			for(int counter=0;counter<field_parameter_key.size();counter++){
				transport.addInformation(field_parameter_key.get(counter), field_parameter_value.get(counter));
			}
		}catch(Exception ex){
			error("action put parameter to transport ERROR"+ex.getMessage());
		}
		
		transport.setDirection(Transport.TYPE_REQUEST);
		try{
			debug("CONNECTION:3");
			transport=ServerExchange.exchange(new ReaderExchange(this.getBonCard()),
											  null,
											  transport);
			debug("CONNECTION:4");
			// обработка transport
			if(transport!=null){
				debug("Transport.Status:"+transport.getStatus()+"   Transport.SubCommand:"+transport.getSubCommandCount());
				debug("получен пакет с положительным результатом выполнения заданного Action");
				// CONNECTION:5
				actionResponseProcess(transport);
			}else{
				debug("критическая ошибка выполнения SubCommand");
				actionResponseError(transport);
			}
			
			debug("getSerialNumber: Action is Done");
		}catch(Exception ex){
			error("getSerialNumber: Error in Exchange with server "+ex.getMessage());
			//JOptionPane.showMessageDialog(this, "Ошибка при получени результата");
			Alert("Error in getting result ");
		}
		this.setBonCard();
	}
	
	/** 
	 * CONNECTION:5
	 * обработка полученных результатов во время информационного обмена
	 * @param transport полученный окончательный ответ от  
	 */
	private void actionResponseProcess(Transport transport){
		// место в апплете для анализа полученных от сервера результатов
		// getAnswer - локальная обработка на JavaScript
		// Alert - выдача сообщения об ошибке
		for(int counter=0;counter<transport.getSubCommandCount();counter++){
			if(transport.getSubCommand(counter).getCommandFor()==SubCommand.FOR_DISPLAY){
				// передача дальнейшей обработки на JavaScript
				// CONNECTION:5
				debug("Show responce to JavaScript:");
				debug("CommandName:"+transport.getSubCommand(counter).getCommand());
				debug("ParameterInteger:"+new Integer(transport.getSubCommand(counter).getParameterInt()).toString());
				debug("ParameterString:"+transport.getSubCommand(counter).getParameterString());
				debug("Parameter length:"+transport.getSubCommand(counter).getInformationKeys().length);
				getAnswer(transport.getSubCommand(counter).getCommand(),
						  new Integer(transport.getSubCommand(counter).getParameterInt()).toString(),
						  transport.getSubCommand(counter).getParameterString(),
						  transport.getSubCommand(counter).getInformationKeys(),
						  transport.getSubCommand(counter).getInformationValues());
			}else if(transport.getSubCommand(counter).getCommandFor()==SubCommand.FOR_READER){
				// для Reader
				// не может быть, т.к. все команды по работе с SmartCard+SmartReader должны иметь подтверждение о выполнении,
				// а в данном блоке происходит обработка команд, которые предназначены для отображения пользователю
				error("STATUS_DONE,    FOR_READER");
			}else{
				error("command not recognized");
			}
		}
	}
	
	/** 
	 * CONNECTION:5
	 * обработка полученного ERROR ответа
	 * @param - транспорт, у которого находится информация об ошибке
	 */
	private void actionResponseError(Transport transport, String error_information){
		
		if(error_information!=null){
			// есть информация для отображения об ошибке
			this.Alert("Error: "+error_information);
		}else{
			// нет дополнительной информации об ошибке
			this.Alert("Action not making");
		}
	}
	/** 
	 * обработка критического ERROR
	 */
	private void actionResponseError(Transport transport){
		actionResponseError(transport,null);
	}
	
	
	/** Выдача ответа на JavaScript для его локальной обработки
	 * @param actionName имя Action, для понимания принадлежности параметров
	 * @param actionParamInt - числовое значение параметра
	 * @param actionParamString - текстовое значение параметра
	 * @param parameterKey - ключи для параметров 
	 * @param parameterValue - значения для параметров
	 * */
	private void getAnswer(String   actionName,
						   String actionParamInt,
						   String actionParamString,
						   String[] parameterKey, 
						   String[] parameterValue){
		debug("getAnswer:begin");
		try{
			
			//getAppletContext().showDocument(new URL("javascript:frm_data.getAnswer(\""+actionName+"\",\""+actionParamInt+"\",\""+actionParamString+"\","+Utility.getJavaScriptArrayFromArray(parameterKey)+","+Utility.getJavaScriptArrayFromArray(parameterValue)+")"));
			getAppletContext().showDocument(new URL("javascript:getAnswer(\""+actionName+"\",\""+actionParamInt+"\",\""+actionParamString+"\","+Utility.getJavaScriptArrayFromArray(parameterKey)+","+Utility.getJavaScriptArrayFromArray(parameterValue)+")"));
			// plugin.jar
			//JSObject.getWindow(this).call("getAnswer", new Object[]{actionName, actionParamInt, actionParamString,parameterKey, parameterValue});
		}catch (Exception ex) {
			error("getAnswer: responseToJavaScript:"+ex.getMessage());
		}
		debug("getAnswer:end");
	}

	
	
	/** метод, который вызывает JavaScript.Alert
	 * для отображения невозможности данного действия
	 * @param message - сообщение, которое должно быть отображено пользователю 
	 */
	private void Alert(String message){
		try{
			//getAppletContext().showDocument(new URL("javascript:frm_data.alert(\""+message+"\")"));
			getAppletContext().showDocument(new URL("javascript:alert(\""+message+"\")"));
			//JSObject.getWindow(this).call("replaceHtml", new String[]{message});
		}catch (Exception ex) { 
			error("responseToJavaScript:"+ex.getMessage());
		}
	}
	public void finalize(){
		debug("finalize: applet finalize");
		// попытка удаления файла
		try{
			File library=new File(LoadLibraryFromJar.libraryPath);
			if(library.exists()){
				if(library.delete()){
					// delete is true
					debug("finalize: library is DELETED");
				}else{
					debug("finalize: library delete is false");
					library.deleteOnExit();
				}
			}else{
				debug("finalize: file not found ");
			}
		}catch(Exception ex){
			debug("finalize: delete file ERROR:"+ex.getMessage());
		}
	}

}
