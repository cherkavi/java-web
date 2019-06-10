package untitled5;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

class draw_string implements Runnable
{
  Graphics g;
  String message;
  int delay;

  Color text_color;
  //constructor
  draw_string(Graphics g,String s, int delay_ms,Color text_color)
  {
    this.g=g;
    this.message=s;
    this.delay=delay_ms;
    this.text_color=text_color;
    new Thread(this,"my_thread").start();
  }

  void draw_and_delay(int x,int y)
  {
    g.drawString(this.message,x,y);
    try
    {
      Thread.currentThread().sleep(this.delay);
    }
    catch(Exception e)
    {
      System.out.println("error");
    }
  }
  // runnable method
  public void run()
  {
     g.setColor(this.text_color);
     for(int i=0;i<10;i++)
     {
        draw_and_delay(i*10,i*10);
     }
  }
}

public class Applet1 extends Applet {
  draw_string thread_1,thread_2;
  boolean isStandalone = false;
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JToggleButton jToggleButton1 = new JToggleButton();
  /**Get a parameter value*/
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  /**Construct the applet*/
  public Applet1() {
  }
  /**Initialize the applet*/
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /* Start applet*/
  public void start(){
     System.out.println("hello");
  }
  /* Stop applet*/
  public void stop(){
     System.out.println("stop");
  }
  /* Repaint window of applet -> WM_PAINT */
  public void paint(Graphics g)
  {
    Color text_color=new Color(255,255,0);
    Color fill_color=new Color(175,0,0);
    g.setBackground(text_color);
    /*g.setColor(fill_color);
    g.fillRect(100,100,100,100);
    g.setColor(text_color);
    g.drawString("This is my string",50,50);
    for(int i=0;i<10;i++)
    {
      g.clearRect(1,1,300,300);
      g.drawString("text drawing",10*i,10*i);
      try
      {
         Thread.sleep(1000);
      }
      catch(Exception e)
      {
        System.out.println("Exception in Sleep");
      }
    }*/
    //thread_1=new draw_string(g,"one string",1000,text_color);
    //thread_2=new draw_string(g,"two string",250,fill_color);
  }

  /**Component initialization*/
  private void jbInit() throws Exception {
    jLabel1.setText("jLabel1");
    jLabel2.setText("jLabel2");
    jToggleButton1.setText("jToggleButton1");
    this.add(jLabel1, null);
    this.add(jLabel2, null);
    this.add(jToggleButton1, null);
  }
  /**Get Applet information*/
  public String getAppletInfo() {
    return "Applet Information";
  }
  /**Get parameter info*/
  public String[][] getParameterInfo() {
    return null;
  }
}