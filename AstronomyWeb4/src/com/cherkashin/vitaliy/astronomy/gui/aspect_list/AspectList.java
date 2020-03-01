package com.cherkashin.vitaliy.astronomy.gui.aspect_list;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.gui.PageWrap;
import com.cherkashin.vitaliy.astronomy.session.AstronomySession;

import database.ConnectWrap;
import database.StaticConnector;

import wicket_utility.ajax.IAjaxActionExecutor;

/** отображение списка найденных аспектов по указанной дате рождения */
public class AspectList extends Panel{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	public AspectList(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	private void initComponents(){
		Form<Object> formMain=new Form<Object>("form_main");
		this.add(formMain);
		
		formMain.add(new Label("title", this.getString("title")));
		
		AjaxButton buttonMenu=new AjaxButton("button_menu"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonMenu(target);
			}
		};
		formMain.add(buttonMenu);
		buttonMenu.add(new SimpleAttributeModifier("value",this.getString("button_menu")));
		
		ArrayList<DoublePlanet> listOfElement=((AstronomySession)this.getSession()).getListOfAspects();
		Collections.sort(listOfElement, new Comparator<DoublePlanet>(){
			@Override
			public int compare(DoublePlanet arg0, DoublePlanet arg1) {
				if(arg0.getKpd()==arg1.getKpd()){
					return 0;
				}else{
					if(arg0.getKpd()<arg1.getKpd()){
						return 1;
					}else{
						return -1;
					}
				}
			}
		});
		
		ListView<DoublePlanet> list=new ListView<DoublePlanet>("aspect_list", listOfElement){
			private final static long serialVersionUID=1L;
			@Override
			protected void populateItem(ListItem<DoublePlanet> item) {
				final DoublePlanet model=item.getModel().getObject();
				AjaxButton button=new AjaxButton("button_aspect"){
					private final static long serialVersionUID=1L;
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						onButtonAspect(target, model);
					}
				};
				item.add(button);
				button.add(new SimpleAttributeModifier("value", getCaptionFromDoublePlanet(model)));
			}
		};
		formMain.add(list);
	}
	
	/** нажатие на кнопку выбора меню  */
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionMenu);
	}
	
	/** нажатие на кнопку одного из аспектов  */
	private void onButtonAspect(AjaxRequestTarget target, DoublePlanet doublePlanet){
		// перейти на указанный ритм 
		this.executor.action(target, PageWrap.actionBirthDayAspect, doublePlanet);
	}
	
	/** получить наименование аспекта на основании  */
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
