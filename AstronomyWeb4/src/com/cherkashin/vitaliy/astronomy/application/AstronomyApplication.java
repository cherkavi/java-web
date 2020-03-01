package com.cherkashin.vitaliy.astronomy.application;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;

import org.apache.wicket.protocol.http.WebApplication;

import reporter.PatternReportList;
import reporter.Reporter;

import com.cherkashin.vitaliy.astronomy.gui.PageWrap;
import com.cherkashin.vitaliy.astronomy.session.AstronomySession;

import database.DatabaseDrivers;
import database.StaticConnector;


public class AstronomyApplication extends WebApplication{
	/** ��� "�������" ������ */
	public static final String emptyReportFileName="emptyReport.jpg";
	/** ��� "����������" ������ */
	public static final String errorReportFileName="errorReport.jpg";
	
	private static final String initPrefix;
	static {
		if(isWindows()){
			initPrefix="windows_";
		}else{
			if(isMac()){
				initPrefix="mac_";
			}else{
				initPrefix="unix_";
			}
		}
	}
	
	private static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
	    return (os.indexOf( "win" ) >= 0); 
	}
	private static boolean isMac(){
		String os = System.getProperty("os.name").toLowerCase();
	    return (os.indexOf( "mac" ) >= 0); 
	}
	/* private static boolean isUnix(){
		String os = System.getProperty("os.name").toLowerCase();
	    return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
	}*/
	
	/** ������ ���� � ���������� ����������� */
	private String pathToTextCommentDirectory=null;
	/** ������ ���� � �������� � ��������� ( ����� ������� � {@link PatternReportList}) */
	private String pathToPatternDirectory=null;
	/** ������ ���� � �������� � ��������� �������  */
	private String pathToOutputReport=null;
	/** ��������� �������  */
	private Reporter reporter=null;
	@Override
	protected void init() {
		try{
			StaticConnector.driver=DatabaseDrivers.valueOf(this.getInitParameter(initPrefix+"database_driver").trim());
		}catch(Exception ex){
			System.err.println("AstronomyApplication database_url Exception: "+ex.getMessage());
		}
		try{
			StaticConnector.url=StaticConnector.driver.getUrl(this.getInitParameter(initPrefix+"database_host"),"astronomy");
		}catch(Exception ex){
			System.err.println("AstronomyApplication database_url Exception: "+ex.getMessage());
		}
		try{
			StaticConnector.userName=this.getInitParameter(initPrefix+"database_login").trim();
		}catch(Exception ex){
			System.err.println("AstronomyApplication database_url Exception: "+ex.getMessage());
		}
		try{
			StaticConnector.password=this.getInitParameter(initPrefix+"database_password").trim();
		}catch(Exception ex){
			System.err.println("AstronomyApplication database_url Exception: "+ex.getMessage());
		}

		
		try{
			this.pathToTextCommentDirectory=this.getInitParameter(initPrefix+"path_to_text_comment");
		}catch(Exception ex){
			System.err.println("AstronomyApplication readTextCommentDirectory Exception: "+ex.getMessage());
		}
		try{
			this.pathToPatternDirectory=this.getInitParameter(initPrefix+"path_to_pattern");
		}catch(Exception ex){
			System.err.println("AstronomyApplication pathToPatternDirectory Exception: "+ex.getMessage());
		}
		try{
			this.pathToOutputReport=this.getInitParameter(initPrefix+"path_to_output_report");
		}catch(Exception ex){
			System.err.println("AstronomyApplication pathToTextCommentDirectory Exception: "+ex.getMessage());
		}
		this.reporter=new Reporter(this.pathToOutputReport, 
								   this.pathToPatternDirectory,
								   emptyReportFileName,
								   errorReportFileName
								   );
	}

	public Reporter getReporter(){
		return this.reporter;
	}
	
	/** �������� ������ ���� � �������� � �������, ������� �������� ��������� �����������  */
	public String getPathToTextCommentDirectory(){
		return this.pathToTextCommentDirectory;
	}
	
	/* 
	//  �������� ������ ���� � �������� � ��������� �������  
	public String getPathToPatternDirectory(){
		return this.pathToPatternDirectory;
	}
	
	// ������� ������ ���� � �������� � ��������� ��������  
	public String getPathToOutputReport(){
		return this.pathToOutputReport;
	}
	*/
	
	@Override
	public Class<? extends Page> getHomePage() {
		return PageWrap.class;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new AstronomySession(request);
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("MMddHHmmss");
	private Random random=new Random();
	
	/** ������������� ���������� ������������� */
	public String generateUniqueId(){
		return sdf.format(new java.util.Date())+this.getRandomString(7);
	}
	
	/** �������� ������ �� ��������� ������������������ �������� */
	private String getRandomString(int count){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<counter;counter++)returnValue.append(random.nextInt(16));
		return returnValue.toString();
	}
	
	/** ������������ ����� �������� �������� ������ */
	public long getMaxWaitReportTime() {
		return 60000;
	}
	
}
