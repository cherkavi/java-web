import javax.xml.ws.Holder;

import cherkashin.vitaliy.Calculator;
import cherkashin.vitaliy.CalculatorService;
import cherkashin.vitaliy.UserObject;

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("Begin");
		CalculatorService service=new CalculatorService();
		Calculator calculator=service.getCalculatorPort();
		System.out.println(calculator.addValue(10,20));
		System.out.println("End");
		UserObject userObject=calculator.getUserObject(10, "String value for send");
		System.out.println(userObject.getIntValue());
		System.out.println(userObject.getStringValue());
		
		/*
try {
			URL url = new URL("http://localhost:9191/wisequotes?wsdl");
			CalculatorService serviceWithUrl = new CalculatorService(
					url,
					new QName(
							"http://provider.first.java6.webservice.vogella.de/",
							"CalculatorService"));
			Calculator calculator = serviceWithUrl
					.getCalculatorPort();
			System.out.println(calculator.getQuote("fun"));
			System.out.println(calculator.getQuote("work"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		 */
	}
}
