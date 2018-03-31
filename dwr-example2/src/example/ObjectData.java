package example;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ObjectData {
	private static Logger field_logger;
	static{
		field_logger=Logger.getLogger("ObjectData");
		field_logger.setLevel(Level.DEBUG);
	}
	private ObjectValue field_value;
	public ObjectValue getObject(){
		field_logger.debug("getObject:begin");
		return field_value; 
	}
	public void setObject(ObjectValue value){
		field_logger.debug("setObject:begin");
		this.field_value=value;
		field_logger.debug("setObject:end");
	}

	public ObjectData(){
		field_logger.debug("constructor:begin");
		this.field_value=new ObjectValue();
		field_logger.debug("constructor:end");
	}
}
