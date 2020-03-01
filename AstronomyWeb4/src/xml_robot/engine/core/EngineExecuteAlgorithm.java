package xml_robot.engine.core;

import org.w3c.dom.Document;

import database.ConnectWrap;
import database.StaticConnector;

import xml_robot.database.DatabaseSharedFunctions;
import xml_robot.engine.Engine;
import xml_robot.exception.EngineException;
import xml_robot.report.Report;
import xml_robot.report.ReportManager;
import xml_robot.report.ReportType;

public class EngineExecuteAlgorithm extends Engine{

	@Override
	protected Document execute(int partnerKod, Document document) throws EngineException {
		int reportNumber=this.getParameterAsInteger(document, "//REQUEST/ALGO_EXE/REPORT_NUMBER");
		Report report=ReportManager.createReportByNumber(reportNumber);
		if(report==null)throw new EngineException(408,"");
		int userKod=this.getParameterAsInteger(document, "//REQUEST/ALGO_EXE/USER/KOD");
		
		// FIXME 
		// запустить отчет на выполнение - поставить в очередь и вернуть ключ 
		return null;
	}

	@Override
	protected String getFunctionName() {
		return "execute_algorithm";
	}

	@Override
	protected void isAllParametersExists(Document document) throws EngineException {
		// проверить код пользовател€ по которому будет запущен алгоритм
		this.checkParameter(document, "//REQUEST/ALGO_EXE/USER/KOD");
		// уникальный номер отчета, который должен быть сформирован 
		this.checkParameter(document, "//REQUEST/ALGO_EXE/REPORT_NUMBER");
		// тип выходной формы отчета 
		this.checkParameter(document, "//REQUEST/ALGO_EXE/REPORT_OUTPUT");
	}

	@Override
	protected void isAllParametersValid(Document document) throws EngineException {
		ConnectWrap connector=StaticConnector.getConnectWrap();
		DatabaseSharedFunctions functions=new DatabaseSharedFunctions();
		try{
			String partnerId=this.getParameterAsString(document,"//REQUEST/PARTNTER/KOD");
			// проверить код пользовател€ на доступность из базы данных
			int userKod=this.getParameterAsInteger(document, "//REQUEST/ALGO_EXE/USER/KOD");
			if(functions.isUserExists(connector, partnerId, userKod)==false){
				throw new EngineException(409, "check the kod of user");
			}
			// проверить доступность номера отчета 
			int reportNumber=this.getParameterAsInteger(document, "//REQUEST/ALGO_EXE/REPORT_NUMBER");
			if(ReportManager.getReportByNumber(reportNumber)==null){
				throw new EngineException(409, "check the kod of report");
			}
			// проверить валидность значени€ выходного отчета 
			String outputType=this.getParameterAsString(document, "//REQUEST/ALGO_EXE/REPORT_OUTPUT");
			if(ReportType.getFromString(outputType)==null){
				throw new EngineException(40,"check the kod of output type");
			}
		}catch(EngineException ex){
			throw ex;
		}catch(Exception ex){
			throw new EngineException(409,"check parameters");
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
	}

}
