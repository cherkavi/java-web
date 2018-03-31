package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.checkbox.HtmlCheckbox;
import org.apache.myfaces.custom.crosstable.HtmlColumns;


public class SimpleBean implements Serializable{
	private UIComponent component;
	private String value="this is temp Value <br />";
	private boolean value2=true;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
	
	public UIComponent getComponent() {
		System.out.println("#getComponent");
		if(component==null){
			System.out.println("component is null");
			component=new HtmlPanelGroup();
		}
		
		HtmlOutputLabel label=new HtmlOutputLabel();
		// label.setValue("this is temp Value");
		label.setValueBinding("value", 
				  FacesContext.getCurrentInstance().getApplication().createValueBinding("#{simpleBean.value}"));
		label.setId("label_1");
		label.setOnclick("check(this);");
		component.getChildren().add(label);

		HtmlOutputText text=new HtmlOutputText();
		text.setValue("<script type=\"text/javascript\"> function check(caller){alert(caller.id);} </script>");
		text.setEscape(false);
		
		component.getChildren().add(text);
		
		HtmlSelectBooleanCheckbox checkbox=new HtmlSelectBooleanCheckbox();
		System.out.println("LabelId: "+label.getId());
		
		checkbox.setValueBinding("value", 
								  FacesContext.getCurrentInstance().getApplication().createValueBinding("#{simpleBean.value2}"));
		// checkbox.setValueBinding("for", FacesContext.getCurrentInstance().getApplication().createValueBinding(label.getId()));
		component.getChildren().add(checkbox);
		
		HtmlDataTable table=new HtmlDataTable();
		HtmlPanelGroup header=new HtmlPanelGroup();
		header.getChildren().add(createTextComponent("<b>header1</b>"));
		header.getChildren().add(createTextComponent("<i>header2</i>"));
		header.getChildren().add(createTextComponent("header3"));
		table.setHeader(header);
		
		// table.setValue(values);
		table.setVar("item");
		table.setValueBinding("values", FacesContext.getCurrentInstance().getApplication().createValueBinding("#{simpleBean.values}"));
			HtmlOutputLabel tableLabel=new HtmlOutputLabel();
			tableLabel.setValueBinding("value", FacesContext.getCurrentInstance().getApplication().createValueBinding("#{item}"));
			table.getChildren().add(tableLabel);
		
		HtmlPanelGroup footer=new HtmlPanelGroup();
		footer.getChildren().add(createTextComponent("<b>footer1</b>"));
		footer.getChildren().add(createTextComponent("<i>footer2</i>"));
		footer.getChildren().add(createTextComponent("footer3"));
		table.setFooter(footer);
		
		component.getChildren().add(table);
		
		return component;
	}
	
	private ArrayList<Boolean> values=new ArrayList<Boolean>(){
		private final static long serialVersionUID=1L;
		{
			add(true);
			add(true);
			add(true);
		}
	};
	
	public List<Boolean> getValues(){
		return values;
	}
	
	private UIComponent createTextComponent(String text){
		HtmlOutputLabel returnValue=new HtmlOutputLabel();
		returnValue.setValue(text);
		return returnValue;
	}

	public void setComponent(UIComponent component) {
		System.out.println("#setComponent");
		this.component = component;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean getValue2() {
		return value2;
	}

	public void setValue2(boolean value2) {
		System.out.println("boolean value set:"+value2);
		this.value2 = value2;
	}

}

class TempValue implements Serializable{
	private final static long serialVersionUID=1L;
	
	private boolean booleanValue=true;
	private int intValue=0;
	
	public boolean isBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	
}
