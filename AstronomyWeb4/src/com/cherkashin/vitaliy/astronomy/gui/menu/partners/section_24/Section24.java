package com.cherkashin.vitaliy.astronomy.gui.menu.partners.section_24;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import algorithms.RightAlgorithm_FindAngle;
import algorithms.RightAlgorithm_Partner;
import algorithms.out.list.ListPartnerOutput;
import algorithms.utils.PlanetName;

import com.cherkashin.vitaliy.astronomy.common_objects.City;
import com.cherkashin.vitaliy.astronomy.gui.PageWrap;

import database.ConnectWrap;
import database.StaticConnector;

import wicket_utility.ajax.IAjaxActionExecutor;

/** Знакомства.Формула любви  */
public class Section24 extends Panel {
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	/** Знакомства.Формула любви  */
	public Section24(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	/** кнопка получения скрипта */
	private WebComponent buttonExecuteMirror=null;
	private Form<Object> formMain=null;
	private WebMarkupContainer result;
	private Model<Date> modelDate=new Model<Date>(new Date());
	private Model<City> modelCity=new Model<City>();
	private WebMarkupContainer cityWrap=null;
	private WebMarkupContainer dateWrap=null;
	
	private void initComponents(){
		formMain=new Form<Object>("form_main");
		this.add(formMain);
		formMain.setOutputMarkupId(true);
		
		formMain.add(new Label("title",this.getString("title")));
		
		dateWrap=new WebMarkupContainer("date_wrap");
		formMain.add(dateWrap);
		dateWrap.setOutputMarkupId(true);
		
		DateTextField date=new DateTextField("date", 
											 modelDate, 
											 new PatternDateConverter("dd.MM.yyyy",false)
											 );
		dateWrap.add(date);
		date.add(new DatePicker());
		
		cityWrap=new WebMarkupContainer("city_wrap");
		formMain.add(cityWrap);
		cityWrap.setOutputMarkupId(true);
		
		Model<ArrayList<City>> listOfCity=new Model<ArrayList<City>>();
		listOfCity.setObject(this.getAllCityAsList());
		if((listOfCity.getObject()!=null)&&(listOfCity.getObject().size()>0)){
			modelCity.setObject(listOfCity.getObject().get(0));
		}
		DropDownChoice<City> city=new DropDownChoice<City>("city", this.modelCity, listOfCity, new IChoiceRenderer<City>() {
			private final static long serialVersionUID=1L;
			@Override
			public Object getDisplayValue(City object) {
				return object.getName();
			}
			@Override
			public String getIdValue(City object, int index) {
				return Integer.toString(object.getId());
			}
		});
		cityWrap.add(city);
		
		
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
		buttonExecuteMirrorClick.append("document.getElementById('"+cityWrap.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteMirrorClick.append("document.getElementById('"+dateWrap.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteMirror.add(new SimpleAttributeModifier("onclick",buttonExecuteMirrorClick.toString()));
		
		this.result=new WebMarkupContainer("result");
		formMain.add(result);
		this.result.setVisible(false);
		this.result.setOutputMarkupId(true);
		
		this.result.add(new WebComponent("result_list"));
		
		formMain.add(new ComponentFeedbackPanel("form_error", formMain));
		
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
		// валидатор формы 
		if(this.modelCity.getObject()==null){
			formMain.error(this.getString("error_city_select"));
			target.addComponent(formMain);
			return;
		}
		if(this.modelDate.getObject()==null){
			formMain.error(this.getString("error_date"));
			target.addComponent(formMain);
			return;
		}
		// произвести расчет 
		RightAlgorithm_FindAngle findAngle=new RightAlgorithm_FindAngle();
		double angle=findAngle.getAngle(this.modelCity.getObject().getLongitude(), 
										this.modelCity.getObject().getLatitude(), 
										this.modelCity.getObject().getGmt(), 
						   				this.modelDate.getObject(), 
						   				PlanetName.Sun);
		
		ListPartnerOutput result=new ListPartnerOutput();
		new RightAlgorithm_Partner(angle, 8, result);
		
		this.result.removeAll();
		Label label=new Label("result_list", this.getHtmlStringFromResult(result));
		label.setEscapeModelStrings(false);
		this.result.add(label);
		this.result.setVisible(true);
		
		buttonExecuteMirror.setVisible(false);
		this.dateWrap.setVisible(false);
		this.cityWrap.setVisible(false);
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
	/** получить список всех городов в виде списка */
	private ArrayList<City> getAllCityAsList(){
		ArrayList<City> returnValue=new ArrayList<City>();
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			ResultSet rs=connector.getConnection().createStatement().executeQuery("select * from a_city order by id");
			while(rs.next()){
				returnValue.add(this.getCityFromResultSet(rs));
			}
		}catch(Exception ex){
			System.err.println();
		}finally{
			connector.close();
		}
		return returnValue;
	}
	
	private City getCityFromResultSet(ResultSet rs) throws SQLException{
		return new City(rs.getInt("id"), rs.getString("name"), rs.getFloat("longitude"),rs.getFloat("latitude"), rs.getInt("gmt"));
	}
	
}
