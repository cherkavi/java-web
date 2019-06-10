public class main_class extends Applet{
	Frame fmMain;
	public void init(){
		fmMain=new frameMain();
		fmMain.setVisible(true);
	}
	public void destroy(){
		
	}
	public void start(){
		repaint();
	}
	public void stop(){
		
	}
	public void paint(Graphics g){
		URL url=null;
		try{
			url=new URL("file:\\d:\\");
		}
		catch(Exception e){
			System.out.println("error\n"+e.getMessage());
		}
		//System.out.println(this.getDocumentBase());
		g.drawImage(this.getImage(url,"snapshot.jpg"),10,20,this);
		
	}
}
