package com.cherkashin.vitaliy.astronomy.gui.menu.partners;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.cherkashin.vitaliy.astronomy.gui.PageWrap;

import wicket_utility.ajax.IAjaxActionExecutor;

/** Панель главного меню - бизнес и карьера, Знакомство и партнерство */
public class Partners extends Panel{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	public Partners(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	private void initComponents(){
		Form<Object> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		formMain.add(new Label("title",this.getString("title")));
		
		AjaxButton button17=new AjaxButton("button_17"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton17(target);
			}
		};
		formMain.add(button17);
		button17.add(new SimpleAttributeModifier("value",this.getString("button_17")));
		 
		AjaxButton button19=new AjaxButton("button_19"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton19(target);
			}
		};
		formMain.add(button19);
		button19.add(new SimpleAttributeModifier("value",this.getString("button_19")));
		
		AjaxButton button20=new AjaxButton("button_20"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton20(target);
			}
		};
		formMain.add(button20);
		button20.add(new SimpleAttributeModifier("value",this.getString("button_20")));
		
		AjaxButton button22=new AjaxButton("button_22"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton22(target);
			}
		};
		formMain.add(button22);
		button22.add(new SimpleAttributeModifier("value",this.getString("button_22")));
		
		AjaxButton button23=new AjaxButton("button_23"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton23(target);
			}
		};
		formMain.add(button23);
		button23.add(new SimpleAttributeModifier("value",this.getString("button_23")));

		AjaxButton button24=new AjaxButton("button_24"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton24(target);
			}
		};
		formMain.add(button24);
		button24.add(new SimpleAttributeModifier("value",this.getString("button_24")));
		
		AjaxButton button25=new AjaxButton("button_25"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButton25(target);
			}
		};
		formMain.add(button25);
		button25.add(new SimpleAttributeModifier("value",this.getString("button_25")));
		
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
	
	private void onButton17(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners17);
	}
	private void onButton19(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners19);
	}
	private void onButton20(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners20);
	}
	private void onButton22(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners22);
	}
	private void onButton23(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners23);
	}
	private void onButton24(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners24);
	}
	private void onButton25(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionPartners25);
	}
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionMenu);
	}
}
