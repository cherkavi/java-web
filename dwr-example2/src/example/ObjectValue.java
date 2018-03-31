package example;

public class ObjectValue {
	public String field_string;
	public String[] field_string_array;
	public int field_int;
	public double field_double;
	
	public ObjectValue(){
		this.field_string="default_value";
		this.field_int=200;
		this.field_double=150.0;
		this.field_string_array=new String[]{"one_element","two_element","three_element"};
	}
	
	public void setField_string_array(String[] value){
		this.field_string_array=value;
	}
	public String[] getField_string_array(){
		return this.field_string_array;
	}
	
	public void setField_string(String value){
		this.field_string=value;
	}
	public String getField_string(){
		return this.field_string;
	}
	
	public void setField_int(int value){
		this.field_int=value;
	}
	public int getField_int(){
		return this.field_int;
	}
	
	public void setField_double(double value){
		this.field_double=value;
	}
	public double getField_double(){
		return this.field_double;
	}
	
}
