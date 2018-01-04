package example;

import java.io.Serializable;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** 
 * Класс, который содержит простые объекты для передачи и/или приема от клиенте
 * 
 **/
public class SimpleData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger field_logger;
	static{
		field_logger=Logger.getLogger("SimpleData");
		field_logger.setLevel(Level.DEBUG);
		BasicConfigurator.configure();
	}
	private int field_int;
	private double field_double;
	private String field_string;
	
	public SimpleData(){
		field_logger.debug("constructor:begin");
		this.field_int=100;
		this.field_double=200.0;
		this.field_string="default_value";
		field_logger.debug("constructor:end");
	}
	
	public void setString(String value){
		field_logger.debug("set String:"+value);
		this.field_string=value;
	}
	
	public String getString(){
		return this.field_string;
	}
	
	public void setDouble(double value){
		field_logger.debug("set double:"+value);
		this.field_double=value;
	}
	public double getDouble(){
		field_logger.debug("get double:"+this.field_double);
		return this.field_double;
	}
	
	public void setInt(int value){
		field_logger.debug("set int:"+value);
		this.field_int=value;
	}
	public int getInt(){
		field_logger.debug("get int:"+this.field_int);
		return this.field_int;
	}
}
