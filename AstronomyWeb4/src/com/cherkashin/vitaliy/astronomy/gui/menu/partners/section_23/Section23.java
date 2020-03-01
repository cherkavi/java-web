package com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_23;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import algorithms.RightAlgorithm_FindAngle;
import algorithms.RightAlgorithm_Partner;
import algorithms.out.list.ListPartnerOutput;
import algorithms.utils.PlanetName;

import com.cherkashin.vitaliy.astronomy.gui.PageWrap;
import com.cherkashin.vitaliy.astronomy.session.AstronomySession;

import wicket_utility.ajax.IAjaxActionExecutor;

/** Знакомства.Формула любви  */
public class Section23 extends Panel {
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	/** Знакомства.Формула любви  */
	public Section23(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	/** кнопка получения скрипта */
	private WebComponent buttonExecuteMirror=null;
	private Form<Object> formMain=null;
	private WebMarkupContainer result;
	
	private void initComponents(){
		formMain=new Form<Object>("form_main");
		this.add(formMain);
		formMain.setOutputMarkupId(true);
		
		formMain.add(new Label("title",this.getString("title")));
		
		IndicatingAjaxButton buttonExecute=new IndicatingAjaxButton("button_execute"){
			private final static long serialVersionUID=1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonExecute(target);
			}
			@Override
			public String getAjaxIndicatorMarkupId() {
				return "waiter";
			}
		};
		formMain.add(buttonExecute);
		buttonExecute.setOutputMarkupId(true);
		
		buttonExecuteMirror=new WebComponent("button_execute_mirror");
		formMain.add(buttonExecuteMirror);
		buttonExecuteMirror.setOutputMarkupId(true);
		buttonExecuteMirror.add(new SimpleAttributeModifier("value",this.getString("button_execute")));
		StringBuffer buttonExecuteMirrorClick=new StringBuffer();
		buttonExecuteMirrorClick.append("document.getElementById('"+buttonExecuteMirror.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteMirrorClick.append("document.getElementById('"+buttonExecute.getMarkupId()+"').click();");
		buttonExecuteMirror.add(new SimpleAttributeModifier("onclick",buttonExecuteMirrorClick.toString()));
		
		this.result=new WebMarkupContainer("result");
		formMain.add(result);
		this.result.setVisible(false);
		this.result.setOutputMarkupId(true);
		
		this.result.add(new WebComponent("result_list"));
		
		AjaxButton buttonBack=new AjaxButton("button_back"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonBack(target);
			}
		};
		formMain.add(buttonBack);
		buttonBack.add(new SimpleAttributeModifier("value",this.getString("button_back")));
		
		AjaxButton buttonMenu=new AjaxButton("button_menu"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonMenu(target);
			}
		};
		formMain.add(buttonMenu);
		buttonMenu.add(new SimpleAttributeModifier("value",this.getString("button_menu")));
	}
	
	private void onButtonBack(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners);
	}
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionMenu);
	}
	
	private void onButtonExecute(AjaxRequestTarget target){
		// произвести расчет 
		AstronomySession session=(AstronomySession)this.getSession();
		RightAlgorithm_FindAngle findAngle=new RightAlgorithm_FindAngle();
		double angle=findAngle.getAngle(session.getCity().getLongitude(), 
						   session.getCity().getLatitude(), 
						   session.getCity().getGmt(), 
						   session.getDateBirthday(), 
						   PlanetName.Sun);
		
		ListPartnerOutput result=new ListPartnerOutput();
		new RightAlgorithm_Partner(angle, 8, result);
		
		this.result.removeAll();
		Label label=new Label("result_list", this.getHtmlStringFromResult(result));
		label.setEscapeModelStrings(false);
		this.result.add(label);
		this.result.setVisible(true);
		
		buttonExecuteMirror.setVisible(false);
		target.addComponent(formMain);
	}

	/** получить строковое представление указанного значения  */
	private String getHtmlStringFromResult(ListPartnerOutput output){
		ArrayList<Double> listOfDouble=output.getResult();
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<listOfDouble.size();counter++){
			if(listOfDouble.get(counter)!=null){
				returnValue.append(output.convertAngleToDayOfYearString(listOfDouble.get(counter)));
				returnValue.append("<br />");
			}
		}
		return returnValue.toString();
	}
	
}
