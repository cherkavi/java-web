package com.cherkashin.vitaliy.astronomy.gui.menu.business.section_16;

import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;

import reporter.PatternReportList;
import reporter.ReportQueueElement;
import reporter.done_listener.IReportDoneListener;
import reporter.query.IQuery;

import algorithms.RightAlgorithm_ISK;
import algorithms.out.database.RithmSave;
import algorithms.utils.CalendarStep;

import com.cherkashin.vitaliy.astronomy.application.AstronomyApplication;
import com.cherkashin.vitaliy.astronomy.common_objects.City;
import com.cherkashin.vitaliy.astronomy.gui.PageWrap;

import database.ConnectWrap;
import database.StaticConnector;

import wicket_utility.FileResourceStream;
import wicket_utility.ajax.IAjaxActionExecutor;

/** ������ � �������.����� ����� */
public class Section16 extends Panel implements IReportDoneListener{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	/** ������ � �������.����� ����� */
	public Section16(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	/** ������ ��������� ������� */
	private WebComponent buttonExecuteMirror=null;
	private Link<Object> reportLink=null;
	private Model<String> fullPathToGeneratedReport=new Model<String>();
	private Form<Object> formMain=null;
	private Model<Date> modelDateFortune=new Model<Date>(new Date());
	private Model<City> modelCityFortune=new Model<City>();
	
	private void initComponents(){
		formMain=new Form<Object>("form_main");
		this.add(formMain);
		formMain.setOutputMarkupId(true);
		
		formMain.add(new Label("title",this.getString("title")));

		WebMarkupContainer dateFortuneWrap=new WebMarkupContainer("date_fortune_wrap");
		formMain.add(dateFortuneWrap);
		dateFortuneWrap.setOutputMarkupId(true);
		
		DateTextField dateFortune=new DateTextField("date_fortune", 
													modelDateFortune, 
													new PatternDateConverter("dd.MM.yyyy",false)
													);
		dateFortuneWrap.add(dateFortune);
		dateFortune.add(new DatePicker());
		
		WebMarkupContainer cityFortuneWrap=new WebMarkupContainer("city_fortune_wrap");
		formMain.add(cityFortuneWrap);
		cityFortuneWrap.setOutputMarkupId(true);
		
		Model<ArrayList<City>> listOfCity=new Model<ArrayList<City>>();
		listOfCity.setObject(this.getAllCityAsList());
		if((listOfCity.getObject()!=null)&&(listOfCity.getObject().size()>0)){
			modelCityFortune.setObject(listOfCity.getObject().get(0));
		}
		DropDownChoice<City> cityFortune=new DropDownChoice<City>("city_fortune", this.modelCityFortune, listOfCity, new IChoiceRenderer<City>() {
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
		cityFortuneWrap.add(cityFortune);
		
		formMain.add(new ComponentFeedbackPanel("form_error", formMain));
		
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
		buttonExecuteMirrorClick.append("document.getElementById('"+cityFortuneWrap.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteMirrorClick.append("document.getElementById('"+dateFortuneWrap.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteMirrorClick.append("document.getElementById('"+buttonExecuteMirror.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteMirrorClick.append("document.getElementById('"+buttonExecute.getMarkupId()+"').click();");
		buttonExecuteMirror.add(new SimpleAttributeModifier("onclick",buttonExecuteMirrorClick.toString()));
		
		reportLink=new Link<Object>("report_link"){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick() {
				getRequestCycle().setRequestTarget(new ResourceStreamRequestTarget(new FileResourceStream(fullPathToGeneratedReport,
						  																				  "application/jpeg",
						  																				  Section16.this.getLocale()
						  																				  ),
						  											               "BusinessMoney.jpeg"
																					)
													);
			}
		};
		formMain.add(reportLink);
		reportLink.setOutputMarkupId(true);
		reportLink.setVisible(false);
		
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
		this.executor.action(target, PageWrap.actionBusiness);
	}
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionMenu);
	}
	
	private Serializable signalObject=new Serializable(){
		private final static long serialVersionUID=1L;
	};

	/** �������� ���� ���������� ������������ NOW */
	private Calendar getDateBegin(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		// calendar.add(Calendar.MONTH, -3);
		// System.out.println("DateBegin:"+sdf.format(calendar.getTime()));
		return calendar;
	}
	
	/** �������� ���� ��������� ������������ +6 Month*/
	private Calendar getDateEnd(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 6);
		// System.out.println("DateEnd:"+sdf.format(calendar.getTime()));
		return calendar;
	}
	
	/** ������������� ������, ������� ������������ � ������ ������ */
	private String reportIdentifier;
	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
	
	private void onButtonExecute(AjaxRequestTarget target){
		// ��������� ���� ����
		if(this.modelDateFortune.getObject()==null){
			formMain.error(this.getString("error_date_fortune"));
			target.addComponent(formMain);
			return;
		}
		
		// ��������� �����
		// �������� ��� ������� ������������ "������� ���� � ��������� �� ���, ����� �� ����������� ���������� �����". 
		// � ��������� �������� ���� �������� ���� � ��� -100% (���� ������ ���, �� 99% � �.�.- ��� ������ ��������� ������ ��������������!) � ������: 
		// �������� ����, � ������������ ��� �� 6 ������� �����. (��� 3 ���).   �  � ������ ������� ������� ���� - ����������� �� ������! 
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			AstronomyApplication application=(AstronomyApplication)this.getApplication();
			String uniqueId=application.getReporter().uniqueId();
			
			City cityFortune=modelCityFortune.getObject();
			Calendar dateFortune=Calendar.getInstance();
			dateFortune.setTime(modelDateFortune.getObject());
			
			City cityCurrent=cityFortune;

			RithmSave saver=new RithmSave();
			saver.begin(connector, uniqueId);
			// ��������� ������
			new RightAlgorithm_ISK(cityFortune.getLongitude(),
								   cityFortune.getLatitude(),
								   dateFortune,
								   cityFortune.getGmt(),
								   
								   cityCurrent.getLongitude(),
								   cityCurrent.getLatitude(),
								   this.getDateBegin(),
								   this.getDateEnd(),
								   new CalendarStep(0,0,3,0,0,0),
								   cityCurrent.getGmt(),
								   saver);
				 
			// ������� ���������� ��� ������
			reportIdentifier=application.generateUniqueId();
			fullPathToGeneratedReport.setObject(null);
			try{
				// ������������ ���������
				application.getReporter().addReportListener(this);
				application.getReporter().addReportForExecute(new ReportQueueElement(reportIdentifier, 
																					 PatternReportList.reportRithmWeek,
																					 "Поиск денег ( "+sdf.format(this.getDateBegin().getTime())+".."+sdf.format(this.getDateEnd().getTime())+") ",
																					 new Query(uniqueId)));
				// ������� �� ��������� ������� �� ���������
				synchronized(this.signalObject){
					this.signalObject.wait(application.getMaxWaitReportTime());
				}
			}catch(Exception waitException){
				System.err.println("Section16#onButtonExecute Exception:"+waitException.getMessage());
			}finally{
				application.getReporter().removeReportListener(this);
			}
			// System.out.println("OutputFile:"+this.fullPathToGeneratedReport);
			
		}catch(Exception ex){
			System.err.println("Section16#onButtonExecute Exception:"+ex.getMessage());
			fullPathToGeneratedReport.setObject(AstronomyApplication.errorReportFileName);
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		
		// ������� ����� �� ����� ������� 
		this.reportLink.setVisible(true);
		// this.buttonExecuteMirror.setVisible(true);
		// this.buttonExecuteMirror.add(new SimpleAttributeModifier("style",""));
		// target.addComponent(this.buttonExecuteMirror);
		target.addComponent(this.formMain);
	}

	@Override
	public void reportDone(String reportId, String fullPathToFile) {
		if(reportId.equals(this.reportIdentifier)){
			this.fullPathToGeneratedReport.setObject(fullPathToFile);
			synchronized (this.signalObject) {
				this.signalObject.notify();
			}
		}else{
			// another report
		}
	}

	@Override
	public void reportError(String reportId, String fullPathToEmptyFile) {
		if(reportId.equals(this.reportIdentifier)){
			this.fullPathToGeneratedReport.setObject(fullPathToEmptyFile);
			synchronized (this.signalObject) {
				this.signalObject.notify();
			}
		}else{
			// another report
		}
	}

	/** �������� ������ ���� ������� � ���� ������ */
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
/** ������ � ���� ������ ��� ��������� ������ �� ������� A_CALCULATION_RITHM �� ���������  */
class Query implements IQuery{
	private final static long serialVersionUID=1L;
	
	private String userIdentifier;
	
	public Query(String userIdentifier){
		this.userIdentifier=userIdentifier;
	}
		
	@Override
	public String getQuery() {
		return "select * from a_calculation_rithm where id_user=(select a_user_id.id from a_user_id where a_user_id.user_identifier='"+this.userIdentifier+"' limit 0,1)";
	}

	@Override
	public String getQuerySize() {
		return "select count(*) from a_calculation_rithm where id_user=(select a_user_id.id from a_user_id where a_user_id.user_identifier='"+this.userIdentifier+"' limit 0,1)";
	}
}
