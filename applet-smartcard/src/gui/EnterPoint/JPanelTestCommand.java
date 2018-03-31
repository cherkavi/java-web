package gui.EnterPoint;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import manager.ReaderExchange;
import manager.ServerExchange;
import manager.display.JInternalFrameDisplay;
import manager.transport.Transport;

import com.linuxnet.jpcsc_client.BonCard;


/*import be.godot.sc.exceptions.NoReadersAvailable;
import be.godot.sc.exceptions.SmartCardReaderException;
import be.godot.sc.exceptions.UnknownCardException;
import be.godot.sc.util.*;
import be.godot.sc.engine.SmartCardReader;
import be.godot.sc.engine.UkrCos.*;
*/

@SuppressWarnings("serial")
public class JPanelTestCommand extends JPanel{
	/** ����, ������� �������� ������ �� ���� � ����� ��� ���������� �������������� �������� BonCard*/
	private String field_path_to_storage_bon_card="c:\\bon_card.bin";
	BonCard field_BonCard=null;
	
	private void debug(String information){
		System.out.println("JPanelTestCommand: "+information);
	}
	private void error(String information){
		System.out.println("JPanelTestCommand: ERROR:  "+information);
	}
	
	/** <b>�������� ������ � ����</b> 
	 * @param path_to_file - ���� � �����, ���� ����� �������� ������ ������
	 * @param parent - ������, ������� ������ ���� ������� 
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
	
	/** <b>��������� ������ �� �����</b>
	 * @param path_to_file - ���� � �����, � ������� ������� ������
	 * @return ����������� ������ 
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
	
	/** @return �������� ������ ���� � �����, ��� ��������� ������ BonCard �� �����*/
	private String getPathToStorageBonCard(){
		return this.field_path_to_storage_bon_card;
	}
	
	/** 
	 * ��������� BonCard �� �����
	 */
	private boolean saveBonCardToDisk(BonCard object){
		return this.writeObject_to_file(this.getPathToStorageBonCard(), object);
	}
	
	/** 
	 * �������� BonCard c ����� ��� null � ������ ������
	 */
	private BonCard readBonCardFromDisk(){
		Object return_value=this.readObject_from_file(this.getPathToStorageBonCard());
		if(return_value!=null){
			BonCard return_bon_card=null;
			try{
				return_bon_card=(BonCard)return_value;
			}catch(Exception ex){
				// return null;
			}
			return return_bon_card;
		}else{
			return null;
		}
	}

	/** �������� ������ BonCard */
	private BonCard getBonCard(){
		this.field_BonCard=this.readBonCardFromDisk();
		if(this.field_BonCard==null){
			this.field_BonCard=new BonCard();
		}
		return this.field_BonCard;
	}
	
	/** ��������� ������� BonCard*/
	private boolean setBonCard(){
		return this.saveBonCardToDisk(this.field_BonCard);
	}
	
	/** �������� BonCard*/
	private void clearBonCard(){
		this.field_BonCard=null;
		try{
			File f=new File(this.getPathToStorageBonCard());
			f.delete();
		}catch(Exception ex){};
	}
	
	/** ������� �������� */
	//BonCard field_current_card=new BonCard();
	/** ������ ��������� ������ ���� ��������� ��������� */
	JButton field_button_reader_list;
	/** ������ ���������� � ����������� */
	JButton field_button_connect;
	/** ������ ������������ �� ���������� */
	JButton field_button_disconnect;
	/** ������ ������ ������ �� ���������� */
	JButton field_button_read;
	/** ������ ������ ����������� � ����������� */
	JPanel field_panel_reader_exchange;
	/** ������ ��� ������ ���� */
	JTextArea field_log;
	
	private JComboBox field_readers;
	public JPanelTestCommand(JTextArea log){
		super();
		this.field_log=log;
		CreateComponents();
	}
	
	/**
	 * ���������� �������� Enagled ��� ���� �������� ��������
	 */
	private void setEnabledIntoPanel(JPanel panel, boolean value){
		for(int counter=0;counter<panel.getComponentCount();counter++){
			panel.getComponent(counter).setEnabled(value);
		}
	}
	
	public void finalize(){
		try{
			this.getBonCard().disconnect();
			this.clearBonCard();
		}catch(Throwable e){
			
		}
	}
	
	
	private void CreateComponents(){
		field_button_reader_list=new JButton("List Reader's");
		field_button_connect=new JButton("Connect");
		field_button_disconnect=new JButton("Disconnect");
		field_button_disconnect.setEnabled(false);
		field_button_read=new JButton("Read Data");field_button_read.setEnabled(false);
		JButton field_button_1=new JButton("Get Serial Number");
		JButton field_button_2=new JButton("command 2");
		JButton field_button_3=new JButton("command 3");
		JButton field_button_4=new JButton("command 4");
		JButton field_button_5=new JButton("command 5");
		JButton field_button_6=new JButton("command 6");
		JButton field_button_7=new JButton("command 7");
		JButton field_button_8=new JButton("command 8");
		
		
		field_button_1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_get_serial();
			}
		});
		field_button_2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_test_command();
			}
		});
		field_button_3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_3();
			}
		});
		field_button_4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_4();
			}
		});
		field_button_5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_5();
			}
		});
		field_button_6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_6();
			}
		});
		field_button_7.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_7();
			}
		});
		field_button_8.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				on_button_8();
			}
		});
		
		field_button_reader_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_reader_list();
			}
		});
		JButton button_readerGroup_list=new JButton("List Reader's Group");
		button_readerGroup_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				on_button_readerGroup_list();
			}
		});
		field_button_connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				on_button_connect_click();
			}
		});
		field_button_disconnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				on_button_disconnect_click();
			}
			
		});
		field_button_read.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				on_button_read_click();
			}
		});
		
		this.field_readers=new JComboBox();
		    /** ������ ����������� � Reader-�*/
		JPanel panel_readers_wrap=new JPanel(new GridLayout(3,1));
		panel_readers_wrap.setBorder(javax.swing.BorderFactory.createTitledBorder("Devices"));
		panel_readers_wrap.add(this.field_readers);
		panel_readers_wrap.add(field_button_connect);
		panel_readers_wrap.add(field_button_disconnect);
			/** ������ ������� ����������� � Reader-�� */
		field_panel_reader_exchange=new JPanel();
		field_panel_reader_exchange.setEnabled(false);
		field_panel_reader_exchange.setBorder(javax.swing.BorderFactory.createTitledBorder("���������� �����������"));
		field_panel_reader_exchange.setLayout(new GridLayout(8,1));
		field_panel_reader_exchange.add(field_button_1);
		field_panel_reader_exchange.add(field_button_2);
		field_panel_reader_exchange.add(field_button_3);
		field_panel_reader_exchange.add(field_button_4);
		field_panel_reader_exchange.add(field_button_5);
		field_panel_reader_exchange.add(field_button_6);
		field_panel_reader_exchange.add(field_button_7);
		field_panel_reader_exchange.add(field_button_8);
		
		// placing component's
		GroupLayout group_layout=new GroupLayout(this);
		this.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
		group_layout.setVerticalGroup(group_layout_vertical);
		group_layout.setHorizontalGroup(group_layout_horizontal);
		
		group_layout_horizontal.addGap(20);
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
										 .addComponent(field_button_reader_list)
										 );
		group_layout_horizontal.addGap(20);
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
				 						 .addComponent(button_readerGroup_list)
				 						 );
		group_layout_horizontal.addGap(20);
		group_layout_horizontal.addComponent(panel_readers_wrap,200,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		group_layout_horizontal.addGap(20);
		group_layout_horizontal.addComponent(field_panel_reader_exchange,200,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		
		group_layout_vertical.addGroup(group_layout.createParallelGroup()
									   .addGroup(group_layout.createSequentialGroup()
											   .addGap(15)
											   .addComponent(field_button_reader_list)
									   )
									   .addGroup(group_layout.createSequentialGroup()
											   .addGap(15)
											   .addComponent(button_readerGroup_list)
									   )
									   .addComponent(panel_readers_wrap,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   .addComponent(field_panel_reader_exchange,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
									   );
		this.setEnabledIntoPanel(field_panel_reader_exchange, false);
	}

	/**
	 * reaction on striking button read
	 */
	private void on_button_read_click(){
		debug("button_read.click");
		try{
			
		}catch(Exception ex){
			error("read data Exception:"+ex.getMessage());
		}
	}
	/**
	 * reaction on striking button Connect
	 * ���������� � ��������� ����� ����������
	 */
	private void on_button_connect_click(){
		debug("button_connect.click:  "+(String)this.field_readers.getSelectedItem());
		try{
			debug("������� ����������� � Reader-� �� ���������� ����� ");
			if(this.getBonCard().connectToDeviceByName((String)this.field_readers.getSelectedItem())==true){
				debug("on_button_connect_click: ���������� ������� ");
				field_readers.setEnabled(false);
				field_button_reader_list.setEnabled(false);
				field_button_connect.setEnabled(false);
				field_button_disconnect.setEnabled(true);
				this.setEnabledIntoPanel(field_panel_reader_exchange, true);
				field_button_read.setEnabled(true);
			}else{
				error("on_button_connect_click: ���������� �� �������");
			}
		}catch(Exception ex){
			error("on_button_connect_click: "+ex.getMessage());
		}
		this.setBonCard();
	}

/** ���������� � ��������� ����� �������� */
/*
	private void on_button_connect_click(){
		debug("button_connect.click");
		try{
			Context field_context=new Context();
			field_context.EstablishContext(PCSC.SCOPE_SYSTEM, null, null);
			field_context.Connect((String)this.field_readers.getSelectedItem());

			field_readers.setEnabled(false);
			field_button_reader_list.setEnabled(false);
			field_button_connect.setEnabled(false);
			field_button_read.setEnabled(true);
		}catch(Exception ex){
			error("on_button_connect_click: "+ex.getMessage());
		}
	}
*/	
	
	/** 
	 * reaction on striking button Disconnect
	 */
	private void on_button_disconnect_click(){
		debug("button_disconnect.click");
		try{
			this.getBonCard().disconnect();
			this.clearBonCard();
			// visual component's
			field_readers.setEnabled(true);
			field_button_reader_list.setEnabled(true);
			field_button_connect.setEnabled(true);
			field_button_disconnect.setEnabled(false);
			this.setEnabledIntoPanel(field_panel_reader_exchange, false);
			field_button_read.setEnabled(false);
		}catch(Exception ex){
			error("on_button_connect_click: "+ex.getMessage());
		}
	}
	
	/**
	 * reaction on striking button Get List Reader's
	 * ��������� ���� ������������� Reader-�� � �������
	 */
	private void on_button_reader_list(){
		debug("Get List of reader:");
		try{
			String[] readers=this.getBonCard().getReaders();
			for(int counter=0;counter<readers.length;counter++){
				debug(counter+":"+readers[counter]);
			}
			this.field_readers.setModel(new DefaultComboBoxModel(readers));
		}catch(Exception ex){
			debug("JPanelConnect.on_button_connect_click Exception:"+ex.getMessage());
		}
		this.setBonCard();
	}
	
	/**
	 * reaction on striking button Get List Reader's
	 * ��������� ����� �� ���� ������������� Reader-�� � �������
	 */
	private void on_button_readerGroup_list(){
		debug("Get List of reader:");
		try{
			String[] groups=this.getBonCard().getReadersGroup();
			for(int counter=0;counter<groups.length;counter++){
				debug(counter+":"+groups[counter]);
			}
		}catch(Exception ex){
			debug("JPanelConnect.on_button_connect_click Exception:"+ex.getMessage());
		}
		this.setBonCard();
	}

	/**
	 * reaction on striking button Get Serial number
	 */
	private void on_button_get_serial(){
		System.out.println("������� ��������� ��������� ������");
		try{
			ServerExchange.setURL("http://localhost:8080/Card_Reader_Server_1/CardServer");
			String return_value="";
			debug("getSerialNumber: send package to server ");
			Transport transport=new Transport();
			transport.setActionName("GetSerialNumber");
			transport.setDirection(Transport.TYPE_REQUEST);
			
			debug("trying read BonCard from disk");
			try{
				transport=ServerExchange.exchange(new ReaderExchange(this.getBonCard()),
												  new JInternalFrameDisplay(this,this.field_log),
												  transport);
				debug("getSerialNumber: Action is Done");
			}catch(Exception ex){
				error("getSerialNumber: Error in Exchange with server "+ex.getMessage());
				JOptionPane.showMessageDialog(this, "������ ��� �������� ����������");
			}
			debug("trying saving BonCard to disk");
			this.setBonCard();
			debug("Server Responce:"+return_value);
		}catch(Exception ex){
			error("on_button_get_serial error:"+ex.getMessage());
		}
	}
	/** reaction on striking test button */
	private void on_button_test_command(){
		System.out.println("������� ��������� Action");
		try{
			ServerExchange.setURL("http://localhost:8080/Card_Reader_Server_1/CardServer");
			String return_value="";
			debug("getSerialNumber: send package to server ");
			Transport transport=new Transport();
			transport.setActionName("TestAction");
			transport.setDirection(Transport.TYPE_REQUEST);
			/*debug("getSerialNumber: server sended transport");
			debug("getSerialNumber:transport: Action:"+transport.getActionName());
			debug("getSerialNumber:transport: Direction:"+transport.getDirection());
			debug("getSerialNumber:transport: Status:"+transport.getStatus());
			debug("getSerialNumber:transport: SubCommandCount:"+transport.getSubCommandCount());
			*/
			try{
				transport=ServerExchange.exchange(new ReaderExchange(this.getBonCard()),
												  new JInternalFrameDisplay(this,this.field_log),
												  transport);
				debug("getSerialNumber: Action is Done");
				//return_value=transport.getInformationTextByIndex(0);
			}catch(Exception ex){
				error("getSerialNumber: Error in Exchange with server "+ex.getMessage());
				JOptionPane.showMessageDialog(this, "������ ��� �������� ����������");
			}
			//String server_response=ServerExchange.getSerialNumber(this.field_boncard);
			this.setBonCard();
			debug("Server Responce:"+return_value);
		}catch(Exception ex){
			error("on_button_get_serial error:"+ex.getMessage());
		}
	}
	
	/** reaction on striking button_3*/
	private void on_button_3(){
		System.out.println("������� ��������� ��������� ������");
		try{
			ServerExchange.setURL("http://localhost:8080/Card_Reader_Server_1/CardServer");
			String return_value="";
			debug("getSerialNumber: send package to server ");
			Transport transport=new Transport();
			transport.setActionName("GetSerialNumber_1");
			transport.setDirection(Transport.TYPE_REQUEST);
			try{
				transport=ServerExchange.exchange(new ReaderExchange(this.getBonCard()),
												  new JInternalFrameDisplay(this,this.field_log),
												  transport);
				debug("getSerialNumber: Action is Done");
			}catch(Exception ex){
				error("getSerialNumber: Error in Exchange with server "+ex.getMessage());
				JOptionPane.showMessageDialog(this, "������ ��� �������� ����������");
			}
			this.setBonCard();
			debug("Server Responce:"+return_value);
		}catch(Exception ex){
			error("on_button_get_serial error:"+ex.getMessage());
		}
	}
	/** reaction on striking button_4*/
	private void on_button_4(){
		
	}
	/** reaction on striking button_5*/
	private void on_button_5(){
		
	}
	/** reaction on striking button_6*/
	private void on_button_6(){
		
	}
	/** reaction on striking button_7*/
	private void on_button_7(){
		
	}
	/** reaction on striking button_8*/
	private void on_button_8(){
		
	}
}
