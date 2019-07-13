package wicket_utility.table.toolbar.title;

import java.io.Serializable;

import org.apache.wicket.model.Model;

/**  ������ ��� ����������  */
public class TitleToolbarModel implements Serializable{
	private final static long serialVersionUID=1L;
	private Model<String> model;
	private int horizontaAlignment;
	private String sqlField;
	
	/**  ������ ��� ����������  
	 * @param sqlField - ���� SQL, ������� ����� ��������������� ������ ������ 
	 * @param model ������, ������� ����� ��������� ������� �������� 
	 * @param horizontalAlignment - �������������� ������������ 
	 * <ul>
	 * 	<li><b>-1</b> left </li>
	 * 	<li><b>0</b> center </li>
	 * 	<li><b>1</b> right </li>
	 * </ul>
	 */
	public TitleToolbarModel(String sqlField, Model<String> model, int horizontalAlignment){
		this.sqlField=sqlField;
		this.model=model;
		this.horizontaAlignment=horizontalAlignment;
	}

	public Model<String> getModel() {
		return model;
	}

	public void setModel(Model<String> model) {
		this.model = model;
	}

	public int getHorizontaAlignment() {
		return horizontaAlignment;
	}

	public void setHorizontaAlignment(int horizontaAlignment) {
		this.horizontaAlignment = horizontaAlignment;
	}

	public String getSqlField() {
		return sqlField;
	}

	public void setSqlField(String sqlField) {
		this.sqlField = sqlField;
	}
}
