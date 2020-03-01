package com.cherkashin.vitaliy.astronomy.gui.birth_day;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import algorithms.RightAlgorithm_FindAspects;
import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.common_objects.City;
import com.cherkashin.vitaliy.astronomy.gui.PageWrap;
import com.cherkashin.vitaliy.astronomy.session.AstronomySession;

import database.ConnectWrap;
import database.StaticConnector;

import wicket_utility.ajax.IAjaxActionExecutor;

/** отображение даты рождения, времени рождения и места рождения из списка  */
public class BirthDay extends Panel{
	private final static long serialVersionUID=1L;
	private static final String male="male";
	private static final String female="female";

	
	
	private IAjaxActionExecutor executor;
	
	public BirthDay(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	private Form<Object> formMain;
	private Model<Date> modelDateBirth=new Model<Date>();
	private Model<String> modelTimeHour=new Model<String>("12");
	private Model<String> modelTimeMinute=new Model<String>("00");
	private Model<String> modelSex=new Model<String>(male);
	private Model<City> modelCity=new Model<City>();
	
	private void initComponents(){
		formMain=new Form<Object>("form_main");
		formMain.setOutputMarkupId(true);
		this.add(formMain);
		
		formMain.add(new Label("title",this.getString("title")));
		
		formMain.add(new DateField("date_birth",modelDateBirth));
		
		formMain.add(new Label("label_time",this.getString("label_time")));
		
		TextField<String> timeHour=new TextField<String>("time_hour",modelTimeHour);
		timeHour.setOutputMarkupId(true);
		formMain.add(timeHour);
		WebComponent timeHourInc=new WebComponent("time_hour_inc");
		formMain.add(timeHourInc);
		timeHourInc.add(new SimpleAttributeModifier("onclick", "step('"+timeHour.getMarkupId()+"',1,0,23)"));
		WebComponent timeHourDec=new WebComponent("time_hour_dec");
		formMain.add(timeHourDec);
		timeHourDec.add(new SimpleAttributeModifier("onclick", "step('"+timeHour.getMarkupId()+"',-1,0,23)"));

		TextField<String> timeMinute=new TextField<String>("time_minute",modelTimeMinute);
		timeMinute.setOutputMarkupId(true);
		formMain.add(timeMinute);
		WebComponent timeMinuteInc=new WebComponent("time_minute_inc");
		formMain.add(timeMinuteInc);
		timeMinuteInc.add(new SimpleAttributeModifier("onclick", "step('"+timeMinute.getMarkupId()+"',1,0,59)"));
		WebComponent timeMinuteDec=new WebComponent("time_minute_dec");
		formMain.add(timeMinuteDec);
		timeMinuteDec.add(new SimpleAttributeModifier("onclick", "step('"+timeMinute.getMarkupId()+"',-1,0,59)"));
	
		formMain.add(new Label("label_place",this.getString("label_place")));
		Model<ArrayList<City>> listOfCity=new Model<ArrayList<City>>();
		listOfCity.setObject(this.getAllCityAsList());
		if((listOfCity.getObject()!=null)&&(listOfCity.getObject().size()>0)){
			modelCity.setObject(listOfCity.getObject().get(0));
		}
		DropDownChoice<City> selectCity=new DropDownChoice<City>("select_city", modelCity, listOfCity, new IChoiceRenderer<City>() {
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
		// formMain.add(selectCity);
		
		RadioGroup<String> radioGroup=new RadioGroup<String>("sex_block", this.modelSex);
		formMain.add(radioGroup);
		radioGroup.add(new Radio<String>("male", new Model<String>(male)));
		radioGroup.add(selectCity);
		radioGroup.add(new Radio<String>("female", new Model<String>(female)));
		
		
		ComponentFeedbackPanel feedback=new ComponentFeedbackPanel("form_error", formMain);
		feedback.setOutputMarkupId(true);
		formMain.add(feedback);
	
		AjaxButton buttonCalculate=new AjaxButton("button_calculate"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonCalculate(target);
			}
		};
		buttonCalculate.add(new SimpleAttributeModifier("value",this.getString("label_calculate")));
		formMain.add(buttonCalculate);
	}
	
	private void onButtonCalculate(AjaxRequestTarget target){
		if(this.modelDateBirth.getObject()==null){
			formMain.error(this.getString("error_date_birthday"));
			target.addComponent(formMain);
			return ;
		}
		if(this.modelTimeHour.getObject()==null){
			formMain.error("error_time_hour");
			target.addComponent(formMain);
			return ;
		}
		if(this.modelTimeMinute.getObject()==null){
			formMain.error("error_time_minute");
			target.addComponent(formMain);
			return ;
		}

		if(this.modelCity.getObject()==null){
			formMain.error("error_select_city");
			target.addComponent(formMain);
			return ;
		}
		RightAlgorithm_FindAspects findAspects=new RightAlgorithm_FindAspects();
		
		City city=this.modelCity.getObject();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(this.modelDateBirth.getObject());
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(this.modelTimeHour.getObject()));
		calendar.set(Calendar.MINUTE, Integer.parseInt(this.modelTimeMinute.getObject()));
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		
		ArrayList<DoublePlanet> listOfAspects=findAspects.filterAspects(findAspects.getAspects(city.getLongitude(), city.getLatitude(), city.getGmt(), calendar));
		AstronomySession session=(AstronomySession)this.getSession();
		session.setCity(city);
		session.setDateBirthday(this.modelDateBirth.getObject());
		session.setListOfAspects(listOfAspects);
		session.setMale(this.modelSex.getObject().equals(male));
		// получить все аспекты 
		this.executor.action(target, 
							 PageWrap.actionBirthDayAspectList);
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
