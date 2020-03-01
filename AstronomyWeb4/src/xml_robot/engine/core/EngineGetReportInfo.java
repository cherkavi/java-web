package xml_robot.engine.core;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xml_robot.engine.Engine;
import xml_robot.exception.EngineException;
import xml_robot.report.Report;
import xml_robot.report.ReportManager;
import xml_robot.report.ReportParameter;

public class EngineGetReportInfo extends Engine{

	@Override
	protected Document execute(int partnerKod, Document requestDocument) throws EngineException {
		Integer reportNumber=this.getParameterAsInteger(requestDocument, "//REQUEST/REPORT_NUMBER");
		Report report=ReportManager.getReportByNumber(reportNumber);
		if(report==null){
			assert false:"check Engine algorithm";
			throw new EngineException(408,"");
		}
		
		Document returnValue=Engine.createDocument();
		Element responseElement=this.createElementAndAddToParent(returnValue, "RESPONSE", returnValue);
			Element reportElement=this.createElementAndAddToParent(returnValue, "REPORT", responseElement);
				this.createElementWithTextAndAddToParent(returnValue, "KOD", Integer.toString(report.getUniqueReportNumber()), reportElement);
				this.createElementWithTextAndAddToParent(returnValue, "NAME", report.getName(), reportElement);
				Element params=this.createElementAndAddToParent(returnValue, "PARAMS", reportElement);
				ArrayList<ReportParameter> listOfParameters=report.getReportParameters();
				for(int counter=0;counter<listOfParameters.size();counter++){
					params.appendChild(this.createParamElement(returnValue, counter, listOfParameters.get(counter)));
				}
		// report.getReportParameters();
		return returnValue;
	}

	private Element createParamElement(Document document, int index, ReportParameter parameter){
		Element param=document.createElement("PARAM");
			Element number=document.createElement("NUMBER");
			param.appendChild(number);
			number.setTextContent(Integer.toString(index));
			
			Element type=document.createElement("TYPE");
			param.appendChild(type);
			type.setTextContent(parameter.getType().name());
			
			Element description=document.createElement("DESCRIPTION");
			param.appendChild(description);
			description.setTextContent(parameter.getDescription());
			
		return param;
	}
	
	@Override
	protected String getFunctionName() {
		return "get_report_info";
	}

	@Override
	protected void isAllParametersExists(Document document) throws EngineException {
		this.checkParameter(document, "//REQUEST/REPORT_NUMBER");
	}

	@Override
	protected void isAllParametersValid(Document document) throws EngineException {
		// проверить параметр на принадлежность к Report
		if(this.isParameterAsInteger(document, "//REQUEST/REPORT_NUMBER")==true){
			isReportNumberExists(this.getParameterAsInteger(document, "//REQUEST/REPORT_NUMBER"));
		}
	}

	/** существует ли номер отчета в списке отчетов */
	private void isReportNumberExists(Integer reportNumber) throws EngineException{
		if(ReportManager.getReportByNumber(reportNumber)==null)throw new EngineException(409, "REPORT_NUMBER");
	}

}
