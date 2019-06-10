package gui;

import javax.swing.JApplet;

public class EnterPoint extends JApplet{
	public static void main(String[] args){
		new JFrameMain();
	}
	
	public EnterPoint(){
		super();
		this.add(new PanelMain());
	}
}
