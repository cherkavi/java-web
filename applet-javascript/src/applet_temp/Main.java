/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet_temp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author First
 */
public class Main extends JApplet{
    private JTextArea field_area;
    private JTextField field_text;
    private JButton field_button;
    
    @Override
    public void init(){
        
    }
    
    @Override
    public void destroy(){
        
    }
    
    public void Main(){
        initComponents();
    }
    
    public void initComponents(){
        // create object's
        field_area=new JTextArea();
        field_text=new JTextField();
        field_button=new JButton("Send to javascript");
        // add listener's
        field_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                onButtonClick();
            }
        });
        // placing component's
        JPanel panel_text=new JPanel(new GridLayout(1,1));
        panel_text.add(field_text);
        panel_text.setBorder(javax.swing.BorderFactory.createTitledBorder("текст для отправки в JavaScript"));
        JPanel panel_area=new JPanel(new GridLayout(1,1));
        panel_area.add(field_area);
        panel_area.setBorder(javax.swing.BorderFactory.createTitledBorder("сообщения, полученные от JavaScript"));
        
        JPanel panel_main=new JPanel();
        panel_main.setLayout(new BorderLayout());
        panel_main.add(panel_text,BorderLayout.NORTH);
        panel_main.add(panel_area,BorderLayout.CENTER);
        panel_main.add(field_button, BorderLayout.SOUTH);
        this.getContentPane().add(panel_main);
    }
    
    private void onButtonClick(){
        try{
            this.getAppletContext().showDocument(new URL("javascript:alert('hello')"));
        }catch(Exception ex){
            System.out.println("onButtonClick Exception:"+ex.getMessage());
        }
    }
    
    public void public_method(){
        this.field_area.append("public_method\n");
    }
    public void public_method_2(String value){
        this.field_area.append("public_method_2:"+value+"\n");
    }
}
