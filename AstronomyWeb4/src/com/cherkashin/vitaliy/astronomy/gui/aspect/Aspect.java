package com.cherkashin.vitaliy.astronomy.gui.aspect;

import java.io.Serializable;


import java.sql.ResultSet;
import java.text.SimpleDateFormat;

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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.hibernate.criterion.Restrictions;

import reporter.PatternReportList;
import reporter.ReportQueueElement;
import reporter.done_listener.IReportDoneListener;
import reporter.query.IQuery;

import algorithms.RightAlgorithm_Rithm;
import algorithms.out.database.RithmSave;
import algorithms.utils.CalendarStep;
import algorithms.utils.DoublePlanet;

import com.cherkashin.vitaliy.astronomy.application.AstronomyApplication;
import com.cherkashin.vitaliy.astronomy.gui.PageWrap;
import com.cherkashin.vitaliy.astronomy.session.AstronomySession;

import database.ConnectWrap;
import database.StaticConnector;
import database.wrap_mysql.*;

import wicket_utility.FileResourceStream;
import wicket_utility.ajax.IAjaxActionExecutor;

/** ���� ������, ������� ������ � �������� �������� � �������� �� ����  */
public class Aspect extends Panel implements IReportDoneListener{
	private final static long serialVersionUID=1L;
	
	private SimpleDateFormat sdfReport=new SimpleDateFormat("dd.MM.yyyy");
	private IAjaxActionExecutor executor;
	private DoublePlanet doublePlanet;
	
	/** ���� ������, ������� ������ � �������� �������� � �������� �� ����  
	 * @param id - ���������� �������������  ������ 
	 * @param executor - �����������, �������� ������� ���������� ����������
	 * @param doublePlanet - ���� ������, ������� �������� ������
	 * @param kpd - ��� 
	 */
	public Aspect(String id, IAjaxActionExecutor executor, DoublePlanet doublePlanet){
		super(id);
		this.executor=executor;
		this.doublePlanet=doublePlanet;
		initComponents();
	}
	
	/** ������� ����� */
	private Form<Object> formMain=null;
	private Model<Date> modelDateBegin=new Model<Date>(new Date());
	private Model<Date> modelDateEnd=new Model<Date>(new Date());
	
	private WebMarkupContainer dateBeginContainer;
	private WebMarkupContainer dateEndContainer;
	private WebComponent buttonExecuteWrap;
	private AjaxButton buttonExecute;
	private Link<?> reportLink;
	
	private void initComponents(){
		formMain=new Form<Object>("form_main");
		formMain.setOutputMarkupId(true);
		this.add(formMain);
		
		formMain.add(new ComponentFeedbackPanel("form_error", formMain));
		/** label name */
		formMain.add(new Label("label", 
								getCaptionFromDoublePlanet(doublePlanet)
								)
					);
		
		/** label kpd */
		formMain.add(new Label("label_kpd", Float.toString(doublePlanet.getKpd())+"%"));
		
		/** text comment */
		AjaxButton buttonTextComment=new AjaxButton("button_text_comment"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonTextComment(target);
			}
		};
		formMain.add(buttonTextComment);
		buttonTextComment.add(new SimpleAttributeModifier("value",this.getString("button_text_comment")));
		
		this.dateBeginContainer=new WebMarkupContainer("date_begin_container");
		formMain.add(this.dateBeginContainer);
		this.dateBeginContainer.setOutputMarkupId(true);

		DateTextField dateBegin=new DateTextField("date_begin",
									modelDateBegin, 
									new PatternDateConverter("dd.MM.yyyy",false));
		dateBegin.add(new DatePicker());
		dateBegin.setRequired(false);
		this.dateBeginContainer.add(dateBegin);
		
		this.dateEndContainer=new WebMarkupContainer("date_end_container");
		formMain.add(this.dateEndContainer);
		this.dateEndContainer.setOutputMarkupId(true);

		DateTextField dateEnd=new DateTextField("date_end", 
								  modelDateEnd, 
								  new PatternDateConverter("dd.MM.yyyy",false));
		dateEnd.add(new DatePicker());
		dateEnd.setRequired(false);
		this.dateEndContainer.add(dateEnd);
		
		buttonExecute=new IndicatingAjaxButton("button_execute"){
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
		buttonExecute.setOutputMarkupId(true);
		formMain.add(buttonExecute);
		buttonExecute.add(new SimpleAttributeModifier("value",this.getString("button_execute")));
		
		buttonExecuteWrap=new WebComponent("button_execute_wrap");
		buttonExecuteWrap.setOutputMarkupId(true);
		formMain.add(buttonExecuteWrap);
		StringBuffer buttonExecuteOnClick=new StringBuffer();
		buttonExecuteOnClick.append("document.getElementById('"+dateBeginContainer.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteOnClick.append("document.getElementById('"+dateEndContainer.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteOnClick.append("document.getElementById('"+buttonExecuteWrap.getMarkupId()+"').setAttribute('style','display:none');");
		buttonExecuteOnClick.append("document.getElementById('"+buttonExecute.getMarkupId()+"').click();");
		buttonExecuteWrap.add(new SimpleAttributeModifier("onclick",buttonExecuteOnClick.toString()));
		buttonExecuteWrap.add(new SimpleAttributeModifier("value",this.getString("button_execute")));
		
		this.reportLink=new Link<Object>("report_link"){
			private final static long serialVersionUID=1L;
			@Override
			public void onClick() {
				getRequestCycle().setRequestTarget(new ResourceStreamRequestTarget(new FileResourceStream(fullPathToGeneratedReport,
																										  "application/jpeg",
																										  Aspect.this.getLocale()
																										  ),
																				   "AlexandrAstro.jpeg"
																				   )
												   );
			}
		};
		formMain.add(this.reportLink);
		this.reportLink.setOutputMarkupId(true);
		this.reportLink.setVisible(false);
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
		/** aspect */
		AjaxButton buttonMenu=new AjaxButton("button_menu"){
			private final static long serialVersionUID=1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onButtonMenu(target);
			}
		};
		formMain.add(buttonMenu);
		buttonMenu.add(new SimpleAttributeModifier("value", this.getString("button_menu")));
	}
	
	
	private void onButtonAspectList(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionBirthDayAspectList);
	}
	
	private void onButtonMenu(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionMenu);
		
	}
	
	private void onButtonTextComment(AjaxRequestTarget target){
		this.executor.action(target, PageWrap.actionAspectTextComment, this.doublePlanet);
	}
	
	private void onButtonExecute(AjaxRequestTarget target){
		// �������� �� ���� ���� ������
		if(this.modelDateBegin.getObject()==null){
			formMain.error(this.getString("error_date_begin_empty"));
			target.addComponent(formMain);
			return;
		}
		// �������� �� ���� ���� ���������
		if(this.modelDateEnd.getObject()==null){
			formMain.error(this.getString("error_date_end_empty"));
			target.addComponent(formMain);
			return;
		}
		// �������� �� ���������� ���� ������
		if(this.modelDateBegin.getObject().getTime()>this.modelDateEnd.getObject().getTime()){
			formMain.error(this.getString("error_date_begin_big"));
			target.addComponent(formMain);
			return;
		}
		
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			AstronomyApplication application=(AstronomyApplication)this.getApplication();
			String uniqueId=application.getReporter().uniqueId();
			AstronomySession session=(AstronomySession)this.getSession();
			A_PLANET_DOUBLE dbDoublePlanet=getPlanetDouble(connector, this.doublePlanet.getPlanet1().getKod(), this.doublePlanet.getPlanet2().getKod());
			A_TIME_VALUE step=getTimeValue(connector, dbDoublePlanet.getStepCalculation());
			A_TIME_VALUE range=getTimeValue(connector, dbDoublePlanet.getMaxPeriod());
			// �������� �� ���������� ������ � ������ ���������� �������
			if(checkRange(range, this.modelDateBegin.getObject(), this.modelDateEnd.getObject())==false){
				formMain.error(this.getString("error_date_range_limit")+this.getMaxValue(range));
				target.addComponent(formMain);
				return;
			}
			
			RithmSave saver=new RithmSave();
			saver.begin(connector, uniqueId);
			// ��������� ������ ��� ���� 
			new RightAlgorithm_Rithm(session.getCity().getLongitude(), 
									 session.getCity().getLatitude(), 
									 this.modelDateBegin.getObject(), 
									 this.modelDateEnd.getObject(), 
									 this.convertToCalendarStep(step), 
									 this.doublePlanet.getPlanet1(), 
									 this.doublePlanet.getPlanet2(), 
									 session.getCity().getGmt(), 
									 saver
									 );
			/*new RightAlgorithm_Rithm(session.getCity().getLongitude(), 
					 session.getCity().getLatitude(), 
					 this.modelDateBegin.getObject(), 
					 this.modelDateEnd.getObject(), 
					 this.convertToCalendarStep(step), 
					 this.doublePlanet.getPlanet1(), 
					 this.doublePlanet.getPlanet2(), 
					 session.getCity().getGmt(), 
					 new ConsoleOutput()
					 );*/
			// ������� ���������� ��� ������
			reportIdentifier=application.generateUniqueId();
			fullPathToGeneratedReport.setObject(null);
			try{
				// ������������ ���������
				application.getReporter().addReportListener(this);
				application.getReporter().addReportForExecute(new ReportQueueElement(reportIdentifier, 
																					 PatternReportList.reportRithmDay,
																					 getCaptionFromDoublePlanet(this.doublePlanet)+" ( "+sdfReport.format(this.modelDateBegin.getObject())+" .. "+sdfReport.format(this.modelDateEnd.getObject())+")",
																					 new Query(uniqueId)));
				// ������� �� ��������� ������� �� ���������
				synchronized(this.signalObject){
					this.signalObject.wait(application.getMaxWaitReportTime());
				}
			}catch(Exception waitException){
				System.err.println("Aspect#onButtonExecute Exception:"+waitException.getMessage());
			}finally{
				application.getReporter().removeReportListener(this);
			}
			System.out.println("OutputFile:"+this.fullPathToGeneratedReport);
			this.reportLink.setVisible(true);
			target.addComponent(this.formMain);
		}catch(Exception ex){
			System.err.println("Aspect#onButtonExecute Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
	}
	
	/** ������� �����, �� �������� ��������� ����������  */
	private String reportIdentifier=null;
	/** ������ ���� � ����� �� ��������������� ������� */
	private Model<String> fullPathToGeneratedReport=new Model<String>(null);
	
	@Override
	public void reportDone(String reportId, String fullPathToFile) {
		if(reportId.equals(reportIdentifier)){
			fullPathToGeneratedReport.setObject(fullPathToFile);
			synchronized(this.signalObject){
				this.signalObject.notify();
			}
		}else{
			// �������� ������ ����� 
		}
	}


	@Override
	public void reportError(String reportId, String fullPathToEmptyFile) {
		if(reportId.equals(reportIdentifier)){
			fullPathToGeneratedReport.setObject(fullPathToEmptyFile);
			synchronized(this.signalObject){
				this.signalObject.notify();
			}
		}else{
			// �������� ������ ����� 
		}
	}
	

	/** ������ ��� ���������������� �� ���������  */
	private Object signalObject=new Serializable(){
		private final static long serialVersionUID=1L;
	};
	
	/** �������� ������������ ��������� �������� ���������� ������� */
	private String getMaxValue(A_TIME_VALUE range) {
		StringBuffer returnValue=new StringBuffer();
		if(range.getYear()>0){
			returnValue.append("����:"+range.getYear());
			returnValue.append("  ");
		}
		if(range.getMonth()>0){
			returnValue.append("������:"+range.getMonth());
			returnValue.append("  ");
		}
		if(range.getDay()>0){
			returnValue.append("���:"+range.getDay());
			returnValue.append("  ");
		}
		return returnValue.toString();
	}


	/** ��������� ��������� ������ �� ���������� ������
	 * @param range - �������� �� ���� ������
	 * @param object - ��������� ����
	 * @param object2 - �������� ���� 
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - �������� ������� � �� ��������� </li>
	 * 	<li><b>false</b> - �������� ��������� </li>
	 * </ul>
	 */
	private boolean checkRange(A_TIME_VALUE range, Date object, Date object2) {
		if(this.getDayCount(range)<(object2.getTime()/(1000*60*60*24)-object.getTime()/(1000*60*60*24))){
			return false;
		}else{
			return true;
		}
	}
	
	/** �������� ���-�� ����, ������ �� ������������ ��������  */
	private int getDayCount(A_TIME_VALUE value){
		int dayCount=0;
		dayCount+=value.getYear()*365;
		dayCount+=value.getMonth()*30;
		dayCount+=value.getDay();
		return dayCount;
	}

	/** ������������� ��������� �������� �� ���� �� ��������� �������� ��� ��������� */
	private CalendarStep convertToCalendarStep(A_TIME_VALUE value){
		return new CalendarStep(value.getYear(), value.getMonth(), value.getDay(), value.getHour(), value.getMinute(), value.getSecond());
	}
	
	/** �������� ��������� �������� �� ��������� ����������� ���� */
	private A_TIME_VALUE getTimeValue(ConnectWrap connector, int value){
		A_TIME_VALUE returnValue=null;
		try{
			returnValue=(A_TIME_VALUE)connector.getSession().get(A_TIME_VALUE.class, value);
		}catch(Exception ex){
			System.err.println("Aspect#getTimeValue Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	
	private A_PLANET_DOUBLE getPlanetDouble(ConnectWrap connector, int planet1, int planet2){
		A_PLANET_DOUBLE returnValue=null;
		try{
			returnValue=(A_PLANET_DOUBLE )connector.getSession().createCriteria(A_PLANET_DOUBLE.class)
												 .add(Restrictions.or(Restrictions.and(Restrictions.eq("idPlanetOne", planet1),Restrictions.eq("idPlanetTwo", planet2)), 
														 			  Restrictions.and(Restrictions.eq("idPlanetTwo", planet1),Restrictions.eq("idPlanetOne", planet2)))).uniqueResult();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
			return null;
		}
		return returnValue;
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

