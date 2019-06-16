package wicket_utility.table.column.align_date_column;

import java.lang.reflect.Method;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket_utility.panel_label.PanelLabel;

/** ������� � �������, ������� ����������� �� �����������   */
public class AlignDateColumn<T> extends AbstractColumn<T>{
	private static final long serialVersionUID = 1L;
	private String beanAttribute;
	private int horizontalAlignment;
	private SimpleDateFormat sdf;
	/** ������� � �������, ������� ����������� �� �����������   
	 * @param displayModel - ������ ����������� ��������� ��� ������� 
	 * @param sortProperties - ����� ����������, ������� ����� ������� � IDataProvider ��� �������
	 * @param beanAttribute - Bean �������, ������� ����� ����� get(beanAttribute)
	 * @param horizontalAlignment - ������������ �� ����������� 
	 * 	<ul>
	 * 		<li><b>-1</b> LEFT </li>
	 * 		<li><b>0</b> CENTER </li>
	 * 		<li><b>1</b> RIGHT </li>
	 * 	</ul>
	 * @param format - ������ ������ ��� ������ {@link SimpleDateFormat}
	 */
	public AlignDateColumn(IModel<String> displayModel, 
					   String sortProperties,
					   String beanAttribute,
					   int horizontalAlignment,
					   String dateFormat) {
		super(displayModel,sortProperties);
		this.beanAttribute=beanAttribute;
		this.horizontalAlignment=horizontalAlignment;
		this.sdf=new SimpleDateFormat(dateFormat);
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> item, 
							 String id,
							 IModel<T> model) {
		String value=null;
		try{
			Object date=this.getObjectByBeanName(model.getObject(), this.beanAttribute);
			if(date instanceof Date){
				value=this.sdf.format(((Date)date)); 
			}else{
				value="";
			}
		}catch(Exception ex){
			value="";
		}
		item.add(new PanelLabel(id,
								new Model<String>(value),
								this.horizontalAlignment
								)
				);
	}

	/** �������� get-�� ����� �� ��������� ����� Bean Property � ������ �������  */
	private Object getObjectByBeanName(Object object, String fieldName){
		Object returnValue=null;
		try{
			String methodName="get"+(fieldName.substring(0,1).toUpperCase())+fieldName.substring(1,fieldName.length());
			Method method=object.getClass().getMethod(methodName);
			returnValue=method.invoke(object);
		}catch(Exception ex){
			System.out.println("getObjectByBeanName Exception: "+ex.getMessage());
		}
		return returnValue;
	}
	
}
