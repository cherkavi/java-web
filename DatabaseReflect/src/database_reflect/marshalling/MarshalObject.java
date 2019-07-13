package database_reflect.marshalling;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** ������������� �������� ������ � ������ ������  */
public class MarshalObject {
	// xstream = new XStream(new DomDriver()); // does not require XPP3 library
	// xstream = new XStream(); // does not require XPP3 library
	private XStream xstream = new XStream(new DomDriver());
	
	/** �������� ������ �� ������� */
	public String getStringFromObject(Object object){
		return xstream.toXML(object);
	}
	
	/** �������� ������ �� ������ */
	public Object getObjectFromString(String value){
		try{
			return xstream.fromXML(value);
		}catch(Exception ex){
			return null;
		}
	}
}
