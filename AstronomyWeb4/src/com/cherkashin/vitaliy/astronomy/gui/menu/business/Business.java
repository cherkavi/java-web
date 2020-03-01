package com.cherkashin.vitaliy.astronomy.gui.menu.business;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.cherkashin.vitaliy.astronomy.gui.PageWrap;

import wicket_utility.ajax.IAjaxActionExecutor;

/** Панель главного меню - бизнес и карьера, Знакомство и партнерство */
public class Business extends Panel{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	public Business(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	private void initComponents(){
		Form<Object> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		formMain.add(new Label("title",this.getString("title")));
		
		AjaxButton button11=new AjaxButton("button_11"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton11(target);
			}
		};
		formMain.add(button11);
		button11.add(new SimpleAttributeModifier("value",this.getString("button_11")));
		 
		AjaxButton button12=new AjaxButton("button_12"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton12(target);
			}
		};
		formMain.add(button12);
		button12.add(new SimpleAttributeModifier("value",this.getString("button_12")));
		
		AjaxButton button13=new AjaxButton("button_13"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton13(target);
			}
		};
		formMain.add(button13);
		button13.add(new SimpleAttributeModifier("value",this.getString("button_13")));
		
		AjaxButton button14=new AjaxButton("button_14"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton14(target);
			}
		};
		formMain.add(button14);
		button14.add(new SimpleAttributeModifier("value",this.getString("button_14")));
		
		AjaxButton button15=new AjaxButton("button_15"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton15(target);
			}
		};
		formMain.add(button15);
		button15.add(new SimpleAttributeModifier("value",this.getString("button_15")));
		
		AjaxButton button16=new AjaxButton("button_16"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton16(target);
			}
		};
		formMain.add(button16);
		button16.add(new SimpleAttributeModifier("value",this.getString("button_16")));
		
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
	
	private void onButton11(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners11);
	}
	private void onButton12(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners12);
	}
	private void onButton13(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners13);
	}
	private void onButton14(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners14);
	}
	private void onButton15(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners15);
	}
	private void onButton16(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners16);
	}
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionMenu);
	}
}
