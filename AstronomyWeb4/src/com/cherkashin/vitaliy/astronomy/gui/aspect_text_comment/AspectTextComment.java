package com.cherkashin.vitaliy.astronomy.gui.aspect_text_comment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.application.AstronomyApplication;
import com.cherkashin.vitaliy.astronomy.gui.PageWrap;

import database.ConnectWrap;
import database.StaticConnector;

import wicket_utility.ajax.IAjaxActionExecutor;

/** ��������� �������� ��� ���������� ������� */
public class AspectTextComment extends Panel{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	private DoublePlanet doublePlanet;
	
	/** ��������� �������� ��� ���������� ������� 
	 * @param id - ���������� ������������� 
	 * @param executor - �����������
	 * @param doublePlanet - ���� ������, � ������� ����� ������������ 
	 */
	public AspectTextComment(String id, IAjaxActionExecutor executor, DoublePlanet doublePlanet){
		super(id);
		this.executor=executor;
		this.doublePlanet=doublePlanet;
		initComponents();
	}
	
	private void initComponents(){
		this.add(new Label("title", this.getString("title")));
		
		Label textComment=new Label("text_comment",this.getTextCommentString(this.doublePlanet));
		textComment.setEscapeModelStrings(false);
		this.add(textComment);

		Form<Object> formMain=new Form<Object>("form_main");
		this.add(formMain);

		/** aspect list */
		AjaxButton buttonBack=new AjaxButton("button_back"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonAspect(target,doublePlanet);
			}
		};
		formMain.add(buttonBack);
		buttonBack.add(new SimpleAttributeModifier("value", 
												   this.getString("button_back")+this.getCaptionFromDoublePlanet(this.doublePlanet)));

		/** aspect list */
		AjaxButton buttonAspectList=new AjaxButton("button_aspect_list"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonAspectList(target);
			}
		};
		formMain.add(buttonAspectList);
		buttonAspectList.add(new SimpleAttributeModifier("value", 
														 this.getString("button_aspect_list")));
		
		/**  button menu */
		AjaxButton buttonMenu=new AjaxButton("button_menu"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonMenu(target);
			}
		};
		formMain.add(buttonMenu);
		buttonMenu.add(new SimpleAttributeModifier("value",
											  	   this.getString("button_menu")));
		
	}
	
	/** ������� �� ������ ������ ����  */
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, 
							 PageWrap.actionMenu);
	}
	
	/** ������� �� ������ ������ �� ��������  */
	private void onButtonAspect(AjaxRequestTarget target, 
							    DoublePlanet doublePlanet){
		// ������� �� ��������� ���� 
		this.executor.action(target, 
							 PageWrap.actionBirthDayAspect, 
							 doublePlanet);
	}
	
	private void onButtonAspectList(AjaxRequestTarget target){
		this.executor.action(target, 
							 PageWrap.actionBirthDayAspectList);
	}

	/** �������� ��������� ����������� �� ���� ������  */
	private String getTextCommentString(DoublePlanet doublePlanet){
		String returnValue="";
		// �������� ��� ����� �� ������ ������� 
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			ResultSet rs=connector.getConnection().createStatement().executeQuery("select * from a_planet_double where (id_planet_one="+doublePlanet.getPlanet1().getKod()+" and id_planet_two="+doublePlanet.getPlanet2().getKod()+") or (id_planet_one="+doublePlanet.getPlanet2().getKod()+" and id_planet_two="+doublePlanet.getPlanet1().getKod()+")");
			if(rs.next()){
				String fileComment=rs.getString("file_comment");
				returnValue=readFileComment(((AstronomyApplication)this.getApplication()).getPathToTextCommentDirectory()+fileComment);
			}
		}catch(Exception ex){
			System.err.println("AspectTextComment#getTextCommentString Exception:");
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** �������� ���� � ����� � ������� ��� � ��������� ����  */
	private String readFileComment(String pathToFile){
		try{
			// printAvaiableCharset();
			StringBuffer returnValue=new StringBuffer();
			// INFO charset for read data from file
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(pathToFile),"windows-1251"));
			String currentLine=null;
			while( (currentLine=reader.readLine())!=null){
				// System.out.println(currentLine);
				returnValue.append(currentLine);
			}
			return returnValue.toString();
		}catch(Exception ex){
			System.err.println("AspectTextComment#readFileComment Exception:"+ex.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private void printAvaiableCharset(){
		Map<String,Charset> avaiableCharset=Charset.availableCharsets();
		Iterator<String> iterator=avaiableCharset.keySet().iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			System.out.println("Key:"+key+"   Value:"+avaiableCharset.get(key).name());
		}
	}
	
	
	/** �������� ������������ ������� �� ���������  */
	private String getCaptionFromDoublePlanet(DoublePlanet doublePlanet){
		String returnValue="";
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			ResultSet rs=connector.getConnection().createStatement().executeQuery("select name from a_planet_double where (id_planet_one="+doublePlanet.getPlanet1().getKod()+" and id_planet_two="+doublePlanet.getPlanet2().getKod()+") or (id_planet_one="+doublePlanet.getPlanet2().getKod()+" and id_planet_two="+doublePlanet.getPlanet1().getKod()+")");
			if(rs.next()){
				returnValue=rs.getString(1);
			}
		}catch(Exception ex){
			System.err.println("#getCaptionFromDoublePlanet Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		// return doublePlanet.getPlanet1().getName()+" - "+doublePlanet.getPlanet2().getName()+"  "+doublePlanet.getKpd();
		return returnValue;
	}
	
}
