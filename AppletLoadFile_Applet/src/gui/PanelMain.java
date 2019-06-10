package gui;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.*;

import javax.swing.*;

/**
 * @author First
 *
 */
public class PanelMain extends JPanel{
	/** button for send file to server */
	private JButton field_button_send;
	/** button for get path from GUI */
	private JButton field_button_path;
	/** area for logging data */
	private JTextArea field_area_log;
	/** path for file */
	private JTextField field_text_path;
	
	public PanelMain(){
		super();
		initComponents();
	}

	private static void debug(String information){
		System.out.print("JFrameMain ");
		System.out.print("DEBUG: ");
		System.out.println(information);
	}
	private static void error(String information){
		System.out.print("JFrameMain ");
		System.out.print("ERROR: ");
		System.out.println(information);
	}
	
	/**
	 * 
	 */
	private void initComponents(){
		// create element's
		field_button_send=new JButton("SEND");
		field_button_path=new JButton("...");
		field_area_log=new JTextArea();
		field_text_path=new JTextField();
		JLabel label_path=new JLabel("Path to file:");
		// add listener's
		field_button_path.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_path_click();
			}
		});
		field_button_send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				on_button_send_click();
			}
		});
		// placing element's
		/** panel for governing */
		JPanel panel_manager=new JPanel();
		GroupLayout group_layout=new GroupLayout(panel_manager);
		panel_manager.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_vertical=group_layout.createSequentialGroup();
		group_layout.setHorizontalGroup(group_layout_horizontal);
		group_layout.setVerticalGroup(group_layout_vertical);
		group_layout_horizontal.addGroup(group_layout.createParallelGroup()
										 .addComponent(label_path,GroupLayout.Alignment.CENTER)
				 						 .addGroup(group_layout.createSequentialGroup()
				 								   .addComponent(field_text_path)
				 								   .addComponent(field_button_path)
				 						 			)
				 						 .addComponent(field_button_send,GroupLayout.PREFERRED_SIZE,Byte.MAX_VALUE,Byte.MAX_VALUE));
		
		group_layout_vertical.addComponent(label_path);
		group_layout_vertical.addGroup(group_layout.createParallelGroup()
									   .addComponent(field_text_path)
									   .addComponent(field_button_path)
									   );
		group_layout_vertical.addComponent(field_button_send);
		this.setLayout(new BorderLayout());
		this.add(panel_manager,BorderLayout.NORTH);
		this.add(field_area_log,BorderLayout.CENTER);
	}
	
	/** reaction on striking button "SendIt"*/
	private void on_button_path_click(){
		JFileChooser file_chooser=new JFileChooser();
		if(file_chooser.showDialog(this, "Send it")==JFileChooser.APPROVE_OPTION){
			this.field_text_path.setText(file_chooser.getSelectedFile().getAbsolutePath());
		}else{
			debug("user click Cancel");
		}
	}
	/** reaction on striking button send*/
	private void on_button_send_click(){
		try{
			put_file_to_servlet("http://localhost:8080/AppletLoadFile_Server/GetFile",new File(this.field_text_path.getText()));
			JOptionPane.showMessageDialog(this, "Sended");
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error");
		}
	}
	
	/**send file to servlet and get response
	 * @param url - full path to servlet
	 * @param file - full path to file
	 * @throws when connection broken or terminated
	 */
	private void put_file_to_servlet(String string_url, 
									 File file) throws Exception{
		try{
	        // создание переменных запроса, метод POST в теле запроса, мы в теле запроса передаем данные, пробуем GET
			String[] param_name=new String[]{"FILE_NAME","FILE_SIZE"};
			String[] param_value=new String[]{file.getAbsolutePath(),new Long(file.length()).toString()};
	        StringBuffer data=new StringBuffer();
	        for(int counter=0;counter<param_name.length;counter++){
	            if(counter!=0){
	                data.append("&"+URLEncoder.encode(param_name[counter],"UTF-8")+"="+URLEncoder.encode(param_value[counter],"UTF-8"));
	            }else{
	                data.append(URLEncoder.encode(param_name[counter],"UTF-8")+"="+URLEncoder.encode(param_value[counter],"UTF-8"));
	            }
	        }
			put_file_to_url(new URL(string_url+"?"+data.toString()), file);
		}catch(Exception ex){
			error("put_file_to_servlet:"+ex.getMessage());
			throw ex;
		}
	}
	
	private void put_file_to_url(URL url, File file) throws Exception{
		debug("create connection");
		HttpURLConnection connection=(HttpURLConnection)url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		// если не установить заголовки - нужно "ловить" данное соединение в service, иначе в POST или GET
		// HTTP header:begin
		/*
		connection.setRequestMethod("GET");
		connection.addRequestProperty("Host", url.getHost());
		connection.addRequestProperty("User-Agent", "Mozilla/5.0");
		connection.addRequestProperty("Accept", "image/png");
		connection.addRequestProperty("Keep-Alive", "300");
		connection.addRequestProperty("Connection", "keep-alive");
		
		connection.addRequestProperty("Content-Length", new Long(file.length()).toString());
		*/
		// HTTP header:end
		//connection.setRequestProperty("File", "For send");
		debug("get OutputStream");
		OutputStream output_stream=connection.getOutputStream();
		debug("Copy from path to stream");
		copy_from_path_to_stream(file,output_stream);
		debug("OutputStream close");
		output_stream.close();
		debug("Connection.connect");
		connection.connect();
		// IMPORTANT this, operator is nothing, but not send Request to Server
		InputStream input_stream=connection.getInputStream();
/*			while(input_stream.read()>=0){
		}
*/
		
		connection.disconnect();
	}
	/**
	 * copy File from path to OutputStream
	 * @param file
	 * @param output_stream
	 * @throws IOException - when read from file are terminated
	 */
	private void copy_from_path_to_stream(File file, OutputStream output_stream) throws IOException{
		byte[] buffer=new byte[1024];
		int readed_bytes=0;
		FileInputStream fis=new FileInputStream(file);
		while((readed_bytes=fis.read(buffer))>=0){
			output_stream.write(buffer,0,readed_bytes);
		}
		fis.close();
	}
}
