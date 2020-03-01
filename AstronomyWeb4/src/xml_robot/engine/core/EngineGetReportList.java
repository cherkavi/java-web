package xml_robot.engine.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xml_robot.engine.Engine;
import xml_robot.exception.EngineException;
import xml_robot.report.ReportManager;

public class EngineGetReportList extends Engine{

	@Override
	protected Document execute(int partnerKod, Document document) throws EngineException {
		// INFO возможное ограничение на количество предоставляемых отчетов по указанному партнеру 
		Document returnValue=Engine.createDocument();
		Element response=this.createElementAndAddToParent(returnValue, "RESPONSE", returnValue);
			Element reportList=this.createElementAndAddToParent(returnValue,"REPORT_LIST", response);
				for(int counter=0;counter<ReportManager.getReportCount();counter++){
					addReportElementToParent(returnValue, ReportManager.getReportNumber(counter), ReportManager.getReportName(counter),  reportList);
				}
		return returnValue;
	}
	
	private void addReportElementToParent(Document document, Integer reportNumber, String reportName, Element reportList) {
		Element report=document.createElement("REPORT");
			Element kod=document.createElement("KOD");
			report.appendChild(kod);
			kod.setTextContent((reportNumber==null)?"":reportNumber.toString());
			
			Element name=document.createElement("NAME");
			report.appendChild(name);
			name.setTextContent( (reportName==null)?"":reportName);
	}


	@Override
	protected String getFunctionName() {
		return "get_report_list";
	}

	@Override
	protected void isAllParametersExists(Document document)
			throws EngineException {
		// нет параметров 
	}

	@Override
	protected void isAllParametersValid(Document document)
			throws EngineException {
		// нет параметров 
	}

}
