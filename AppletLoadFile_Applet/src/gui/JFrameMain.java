package gui;
import java.awt.BorderLayout;

import javax.swing.*;

public class JFrameMain extends JFrame{

	public static void main(String[] args){
		debug("start");
		new JFrameMain();
		debug("end");
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
	
	
	public JFrameMain(){
		super("Example load files into Server");
		initComponents();
	}
	
	private void initComponents(){
		this.getContentPane().add(new PanelMain());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0,0,300,200);
		this.setVisible(true);
	}
	
}
