package wicket_utility.table.column.align_column;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket_utility.panel_label.PanelLabel;

/** колонка в таблице, которая выровненная по горизонтали   */
public class AlignColumn<T> extends AbstractColumn<T>{
	private static final long serialVersionUID = 1L;
	private String beanAttribute;
	private int horizontalAlignment;
	/** колонка в таблице, которая выровненная по горизонтали   
	 * @param displayModel - модель отображения заголовка для колонки 
	 * @param sortProperties - текст сортировки, который будет передан в IDataProvider для таблицы
	 * @param beanAttribute - Bean атрибут, который имеет метод get(beanAttribute)
	 * @param horizontalAlignment - выравнивание по горизонтали 
	 * 	<ul>
	 * 		<li><b>-1</b> LEFT </li>
	 * 		<li><b>0</b> CENTER </li>
	 * 		<li><b>1</b> RIGHT </li>
	 * 	</ul>
	 */
	public AlignColumn(IModel<String> displayModel, 
					   String sortProperties,
					   String beanAttribute,
					   int horizontalAlignment) {
		super(displayModel,sortProperties);
		this.beanAttribute=beanAttribute;
		this.horizontalAlignment=horizontalAlignment;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> item, 
							 String id,
							 IModel<T> model) {
		String value=null;
		try{
			value=BeanUtils.getProperty(model.getObject(), beanAttribute);
		}catch(Exception ex){
			value="";
		}
		item.add(new PanelLabel(id,
								new Model<String>(value),
								this.horizontalAlignment
								)
				);
	}

	
}
