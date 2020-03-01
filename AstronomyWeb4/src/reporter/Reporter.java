package reporter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import reporter.done_listener.IReportDoneListener;
import reporter.query.IQuery;

import database.ConnectWrap;
import database.StaticConnector;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;

public class Reporter implements Runnable{
	/** ��������� ������� ���� (����� - ���� ) */
	public final static String reportRithmDay="reportRithmDay";
	/** ��������� ������� ���� (����� - �����) */
	public final static String reportRithmMonth="reportRithmMonth";
	/** ��� �����, ������� �������������� "������" ����������� - ������ ������������  */
	private String fileErrorImage=null;
	/** ����������� ����� � ������ */
	private String fileEmptyImage=null;
	
	/** ������ ���� � �������  */
	private String pathToReport;
	/** ������ ���� � �������� ��� �������� ����������� */
	private String pathToOutputImage;

	/** ������ ���������������� �������  */
	private ArrayList<JasperReport> listCompiledReport=new ArrayList<JasperReport>();
	/** ������ �������������� �������  */
	private ArrayList<PatternReportList> listPreparedPattern=new ArrayList<PatternReportList>();
	/** ��������� � 2D ������� */
	private JRGraphics2DExporter exporter=null;
	/** ������ ����������  */
	private ArrayList<IReportDoneListener> listOfListener=new ArrayList<IReportDoneListener>();
	
	
	
	/** ��������� �������  
	 * @param pathToOutputImage - ������ ���� � �������� ��� �������� ������ � �������� 
	 * @param pathToReport - ������ ���� � �������� �������
	 * @param emptyImage - ������ ����������� ( ��� ������ ��� ������) 
	 * @param errorImage - ��������� ����������� ( ������ ������������ ������ )
	 */
	public Reporter(String pathToOutputImage, 
					String pathToReport,
					String emptyImage,
					String errorImage){
		this.pathToReport=pathToReport;
		this.pathToOutputImage=pathToOutputImage;
		this.fileErrorImage=errorImage;
		this.fileEmptyImage=emptyImage;
		try{
			exporter=new JRGraphics2DExporter();
		}catch(Exception ex){};
		(new Thread(this)).start();
	}

	
	/** �������� ��������� ���������� �������  */
	public void addReportListener(IReportDoneListener listener){
		if(listener!=null)listOfListener.add(listener);
	}
	
	/** ������� ��������� ���������� ������� */
	public void removeReportListener(IReportDoneListener listener){
		try{
			listOfListener.remove(listener);
		}catch(Exception ex){};
	}
	
	
	/** ������� �����  
	 * @param uniqueId - ���������� ������������� ������ 
	 * @param patternReport - {@link PatternReportList} �����, ������� ����� �������
	 * @param title - ��������� ��� ������ 
	 * @param sqlQuery - ������, ������� ����� �������� 
	 * */
	private String createImage(String uniqueId, 
							   PatternReportList patternReport,
							   String title,
							   IQuery query){

		/**  ���������������� ����� */
		JasperReport jasperReporter=null;
		int reportIndex=this.listPreparedPattern.indexOf(patternReport);
		if(reportIndex<0){
	    	try{
	    		jasperReporter=JasperCompileManager.compileReport(this.pathToReport+patternReport.getPatternReportFileName());
	    		this.listCompiledReport.add(jasperReporter);
	    		this.listPreparedPattern.add(patternReport);
	    	}catch(Exception ex){
	    		System.err.println("Reporter#getCreatedImage Exception: "+ex.getMessage());
	    	};
		}else{
			jasperReporter=this.listCompiledReport.get(reportIndex);
		}
		String fileOutputImage=this.pathToOutputImage+this.generateUniqueFileName();
		HashMap <String,Object> parameters=new HashMap<String,Object>();
		parameters.put("TITLE", (title==null)?"":title );
		parameters.put("TITLE_BACKGROUND", "CHRONO MODEL");
		ConnectWrap connector=StaticConnector.getConnectWrap();
		Connection connection=connector.getConnection();
		try{
			ResultSet rs=connection.createStatement().executeQuery(query.getQuerySize());
			if(rs.next()&&(rs.getInt(1)>0)){
				rs=connection.createStatement().executeQuery(query.getQuery());
				JasperPrint jasper_print=JasperFillManager.fillReport(jasperReporter,parameters,new JRResultSetDataSource(rs));
				//JasperExportManager.exportReportToPdfFile(jasper_print,"c:\\temp_1.pdf");
				BufferedImage buffered_image =new BufferedImage(830,330, BufferedImage.TYPE_INT_RGB);
				// �������� 2D �������� ��� �������
				Graphics2D g2d=buffered_image.createGraphics();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper_print);
				// �������������� � �������� �������
				exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g2d);
				exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(0));
				exporter.exportReport();
				// ��������� ���������� ����������� � ����
				//ImageIO.write(buffered_image, "BMP",new File("c:\\out.bmp"));
				ImageIO.write(buffered_image, "JPG",new File(fileOutputImage));
				this.notifyReportListenerOk(uniqueId, fileOutputImage);
			}else{
				this.notifyReportListenerOk(uniqueId, this.pathToOutputImage+this.fileEmptyImage);
			}
		}catch(Exception ex){
			fileOutputImage=this.pathToOutputImage+fileErrorImage;
			System.err.println("Reporter#createdImage:\n"+ex.getMessage());
			this.notifyReportListenerError(uniqueId, fileOutputImage);
		}finally{
			connector.close();
		}
		return fileOutputImage;
	}
	
	/** ���������� ���������� ������ � ������������� ���������� */
	private void notifyReportListenerOk(String uniqueId, String fullFilePath){
		try{
			IReportDoneListener listener=null;
			for(int counter=0;counter<this.listOfListener.size();counter++){
				listener=this.listOfListener.get(counter);
				try{
					listener.reportDone(uniqueId, fullFilePath);
				}catch(Exception ex){
					System.err.println("Reporter#notifyReportListenerOk inner Exception:"+ex.getMessage());
				};
			}
		}catch(Exception ex){
			System.err.println("Reporter#notifyReportListenerOk external Exception:"+ex.getMessage());
		}
	}
	
	/** ���������� ���������� �� ��������� ���������� */
	private void notifyReportListenerError(String uniqueId, String fullFilePath){
		for(IReportDoneListener listener : this.listOfListener){
			try{
				listener.reportError(uniqueId, fullFilePath);
			}catch(Exception ex){
				System.err.println("Reporter#notifyReportListenerError Exception:"+ex.getMessage());
			}
		}
	}

	private SimpleDateFormat sdf=new SimpleDateFormat("MMddHHmmss");
	private Random random=new Random();
	
	/** �������� ������ �� ��������� ������������������ �������� */
	private String getRandomString(int count){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<counter;counter++)returnValue.append(random.nextInt(16));
		return returnValue.toString();
	}

	/** �������� ���������� ��� ����� */
	private String generateUniqueFileName(){
		return this.sdf.format(new Date())+this.getRandomString(4)+".jpg";		
	}

	private ArrayList<ReportQueueElement> queueReport=new ArrayList<ReportQueueElement>();
	
	/** �������� ����� ��� ����������  */
	public void addReportForExecute(ReportQueueElement element){
		 synchronized(queueReport){
			 queueReport.add(element);
			 queueReport.notify();
		 }
	}
	
	@Override
	public void run() {
		ReportQueueElement currentElement=null;
		while(true){
			currentElement=null;
			synchronized (this.queueReport) {
				if(this.queueReport.size()>0){
					// ���� �������� ��� ���������
					currentElement=this.queueReport.remove(0);
				}else{
					try{
						this.queueReport.wait();
					}catch(InterruptedException ex){};
				}
			}
			if(currentElement!=null){
				this.createImage(currentElement.getUniqueId(), 
								 currentElement.getPattern(),
								 currentElement.getTitle(),
								 currentElement.getQuery());
			}
		}
	}


	
	/** ������������� ���������� ������������� ( ��� ������������� ������ ) */
	public String uniqueId(){
		return sdf.format(new Date())+unique(5);
	}
	
	/** �������� ��������� ������������������ ����� */
	private String unique(int count){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<count;counter++){
			returnValue.append(Integer.toHexString(random.nextInt(16)));
		}
		return returnValue.toString();
	}
	
}
