package com.cherkashin.vitaliy.astronomy.gui.menu.business.section_15;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;

import reporter.PatternReportList;
import reporter.ReportQueueElement;
import reporter.done_listener.IReportDoneListener;
import reporter.query.IQuery;

import algorithms.RightAlgorithm_FindAngle;
import algorithms.RightAlgorithm_Shag;
import algorithms.out.database.RithmSave;
import algorithms.utils.PlanetName;

import com.cherkashin.vitaliy.astronomy.application.AstronomyApplication;
import com.cherkashin.vitaliy.astronomy.common_objects.City;
import com.cherkashin.vitaliy.astronomy.gui.PageWrap;
import com.cherkashin.vitaliy.astronomy.session.AstronomySession;

import database.ConnectWrap;
import database.StaticConnector;

import wicket_utility.FileResourceStream;
import wicket_utility.ajax.IAjaxActionExecutor;

/** ������ � �������.������� �������� ������ */
public class Section15 extends Panel implements IReportDoneListener{
	private final static long serialVersionUID=1L;
	
	private IAjaxActionExecutor executor;
	
	/** ������ � �������.������� �������� ������ */
	public Section15(String id, IAjaxActionExecutor executor){
		super(id);
		this.executor=executor;
		initComponents();
	}
	
	/** ������ ��������� ������� */
	private WebComponent buttonExecuteMirror=null;
	private Link<Object> reportLink=null;
	private Model<String> fullPathToGeneratedReport=new Model<String>();
	private Form<Object> formMain=null;
	
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
		
		reportLink=new Link<Object>("report_link"){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick() {
				getRequestCycle().setRequestTarget(new ResourceStreamRequestTarget(new FileResourceStream(fullPathToGeneratedReport,
						  																				  "application/jpeg",
						  																				  Section15.this.getLocale()
						  																				  ),
						  											               "BusinessFortune.jpeg"
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

	/** �������� ���� ���������� ������������ MONTH -3*/
	private Date getDateBegin(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -3);
		// System.out.println("DateBegin:"+sdf.format(calendar.getTime()));
		return calendar.getTime();
	}
	
	/** �������� ���� ��������� ������������ +15 Month*/
	private Date getDateEnd(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, 15);
		// System.out.println("DateEnd:"+sdf.format(calendar.getTime()));
		return calendar.getTime();
	}
	
	/** ������������� ������, ������� ������������ � ������ ������ */
	private String reportIdentifier;
	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
	
	private void onButtonExecute(AjaxRequestTarget target){
		// ��������� �����
		// �������� ���. ������ ������� (�����) ������ (���������) - ������ �������: 9 �������, � ������������ ���. 3 ������ ����� - 6 ������� ������ (��� 1 ����) 
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			AstronomyApplication application=(AstronomyApplication)this.getApplication();
			String uniqueId=application.getReporter().uniqueId();
			AstronomySession session=(AstronomySession)this.getSession();
			City city=session.getCity();
			// �������� ���� ��������
			Date birthDayDate=session.getDateBirthday();
			// �������� �������� ������ ������� 
			RightAlgorithm_FindAngle algorithmAngle=new RightAlgorithm_FindAngle();
			double firstAngle=0;
			firstAngle=algorithmAngle.getAngle(city.getLongitude(), 
					city.getLatitude(), 
					city.getGmt(), 
					birthDayDate, 
					PlanetName.Uranus);
			
			RithmSave saver=new RithmSave();
			saver.begin(connector, uniqueId);
			// ��������� ������
			new RightAlgorithm_Shag().executeShag(this.getDateBegin(), 
					  this.getDateEnd(),
					  7,
					  city.getLongitude(),
					  city.getLatitude(),
					  city.getGmt(),
					  firstAngle,
					  PlanetName.Mars,
					  saver
					   );
				 
			// ������� ���������� ��� ������
			reportIdentifier=application.generateUniqueId();
			fullPathToGeneratedReport.setObject(null);
			try{
				// ������������ ���������
				application.getReporter().addReportListener(this);
				application.getReporter().addReportForExecute(new ReportQueueElement(reportIdentifier, 
																					 PatternReportList.reportRithmWeek,
																					 "Формула делового успеха ( "+sdf.format(this.getDateBegin())+".."+sdf.format(this.getDateEnd())+") ",
																					 new Query(uniqueId)));
				// ������� �� ��������� ������� �� ���������
				synchronized(this.signalObject){
					this.signalObject.wait(application.getMaxWaitReportTime());
				}
			}catch(Exception waitException){
				System.err.println("Section11#onButtonExecute Exception:"+waitException.getMessage());
			}finally{
				application.getReporter().removeReportListener(this);
			}
			// System.out.println("OutputFile:"+this.fullPathToGeneratedReport);
			this.reportLink.setVisible(true);
			target.addComponent(this.formMain);
		}catch(Exception ex){
			System.err.println("Section11#onButtonExecute Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		
		// ������� ����� �� ����� ������� 
		reportLink.setVisible(true);
		buttonExecuteMirror.setVisible(false);
		target.addComponent(formMain);
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
