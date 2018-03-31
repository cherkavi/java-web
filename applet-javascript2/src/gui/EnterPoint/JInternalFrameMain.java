package gui.EnterPoint;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import BonCard.applet.JavaScriptExchange;

/**
 * класс, который является главным внутренним фреймом для приложения
 * @author cherkashinv
 */
public class JInternalFrameMain extends JInternalFrame 
								implements InternalFrameListener,
										   JavaScriptExchange{
	/** рабочий стол, на котором происходит отображение всех фреймов*/
	private JDesktopPane field_desktop;
	/** компонент для передачи текста в JavaScript*/
	private JTextField field_textfield_text;
	/** компонент, который ведет логирование сообщений, полученных от HTML(JavaScript) кода*/
	private JTextArea field_textarea_log;
	/** ссылка на родительский апплет */
	private JApplet field_applet;
	/**
	 * Главный внутренний фрейм
	 * @param desktop - рабочий стол внутреннего фрейма
	 * @param caption - заголовок для внутреннего фрейма
	 * @param width - ширина внутреннего фрейма
	 * @param height - высота внутреннего фрейма
	 */
	public JInternalFrameMain(JApplet applet,
							  JDesktopPane desktop,
							  String caption,
							  int width,
							  int height){
		super(caption,true,true,true,true);
		this.field_applet=applet;
		this.field_desktop=desktop;
		this.addInternalFrameListener(this);
		this.setSize(width,height);
		initComponents();
		this.setVisible(true);
		this.field_desktop.add(this);
		Position.set_frame_to_center(this, field_desktop, 20, 20);
	}

	/** создание визуальных компонентов*/
	private void initComponents(){
		// создание компонентов
		JButton field_button_go=new JButton("Передать данные");
		field_textfield_text=new JTextField();
		JLabel field_label_text=new JLabel("Данные для передачи");
		
		// назначить слушателей
		field_button_go.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Click:");
				onButtonGoClick();
			}
		});
		// расставить компоненты
		JPanel panel_main=new JPanel(new BorderLayout());
		/** панель для отправки данных в JavaScript код*/
		JPanel panel_send_data=new JPanel();
		panel_send_data.setBorder(javax.swing.BorderFactory.createTitledBorder("Отправка данных на JavaScript"));
		GroupLayout group_layout=new GroupLayout(panel_send_data);
		panel_send_data.setLayout(group_layout);
		GroupLayout.SequentialGroup group_layout_horizontal=group_layout.createSequentialGroup();
		GroupLayout.SequentialGroup group_layout_veritcal=group_layout.createSequentialGroup();
		group_layout_horizontal.addGroup(group_layout.createParallelGroup(GroupLayout.Alignment.CENTER)
										 .addComponent(field_label_text,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
										 .addComponent(field_textfield_text)
										 .addComponent(field_button_go,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
										 );
		group_layout_veritcal.addComponent(field_label_text,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		group_layout_veritcal.addComponent(field_textfield_text,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		group_layout_veritcal.addComponent(field_button_go,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);

		group_layout.setHorizontalGroup(group_layout_horizontal);
		group_layout.setVerticalGroup(group_layout_veritcal);
		
		/** панель для примема данных от JavaScript кода*/
		JPanel panel_get_data=new JPanel();
		panel_get_data.setBorder(javax.swing.BorderFactory.createTitledBorder("Полученные данный от HTML"));
		panel_get_data.setLayout(new GridLayout(1,1));
		field_textarea_log=new JTextArea();
		panel_get_data.add(field_textarea_log);
		// расставить основные компоненты
		panel_main.add(panel_send_data,BorderLayout.NORTH);
		panel_main.add(panel_get_data,BorderLayout.CENTER);
		
		this.getContentPane().add(panel_main);
	}

	/** reaction on striking button GO */
	private void onButtonGoClick(){
		try{
			System.out.println("javascript:toConsole(\"" + this.field_textfield_text.getText() +"\")");
			this.field_applet.getAppletContext().showDocument(new URL("javascript:toConsole(\"" + this.field_textfield_text.getText() +"\")"));
			System.out.println("onButtonGoClick:end");
		}catch (MalformedURLException me) { 
			System.out.println("Exception:"+me.getMessage());
		}
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		System.exit(0);
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}


	@Override
	public String echo(String value) {
		this.field_textarea_log.append("echo:>>>"+value);
		this.field_textarea_log.append("\n");
		return value;
	}

	@Override
	public void method_simple() {
		this.field_textarea_log.append("method_simple:>>>");
		this.field_textarea_log.append("\n");
	}

	@Override
	public void method_string(String value) {
		this.field_textarea_log.append("method_string:>>>"+value);
		this.field_textarea_log.append("\n");
	}
}
