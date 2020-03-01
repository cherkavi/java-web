package com.cherkashin.vitaliy.astronomy.gui.menu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.cherkashin.vitaliy.astronomy.gui.PageWrap;

import wicket_utility.ajax.IAjaxActionExecutor;

/** Панель главного меню - бизнес и карьера, Знакомство и партнерство */
public class Menu extends Panel{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	public Menu(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	private void initComponents(){
		Form<Object> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		formMain.add(new Label("title",this.getString("title")));
		
		AjaxButton buttonAspectList=new AjaxButton("button_aspect_list"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonAspectList(target);
			}
		};
		formMain.add(buttonAspectList);
		buttonAspectList.add(new SimpleAttributeModifier("value",this.getString("button_aspect_list")));
		 
		
		AjaxButton buttonBusiness=new AjaxButton("button_business"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonBusiness(target);
			}
		};
		formMain.add(buttonBusiness);
		buttonBusiness.add(new SimpleAttributeModifier("value",this.getString("button_business")));
		
		AjaxButton buttonPartners=new AjaxButton("button_partners"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonPartners(target);
			}
		};
		formMain.add(buttonPartners);
		buttonPartners.add(new SimpleAttributeModifier("value",this.getString("button_partners")));
		
	}
	
	private void onButtonAspectList(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionBirthDayAspectList);
	}
	
	private void onButtonBusiness(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionBusiness);
	}
	
	private void onButtonPartners(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners);
	}
}
