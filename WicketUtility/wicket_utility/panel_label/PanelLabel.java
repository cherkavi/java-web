package wicket_utility.panel_label;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;



/** ������ � ��������  */
public class PanelLabel extends Panel{
	private final static long serialVersionUID=1L;
	
	/** ������ � ��������  
	 * @param id - ���������� ������������� ��� ������ �� �������� Wicket
	 * @param model - ������ ������, ������� ����� ���������� � ��������� ����������
	 * @param alignment - ������������ ���������� ���������� 
	 * 	<ul>
	 * 		<li><b>-1</b> LEFT </li>
	 * 		<li><b>0</b> CENTER </li>
	 * 		<li><b>1</b> RIGHT </li>
	 * 	</ul>
	 */
	public PanelLabel(String id, 
					  IModel<String> model,
					  int alignment
					  ) {
		super(id);
		Label label=new Label("label",model);
		if(alignment==0){
			// center
			label.add(new SimpleAttributeModifier("align","center"));
		}else if(alignment>0){
			// right
			label.add(new SimpleAttributeModifier("align","right"));
		}else{
			// left
			label.add(new SimpleAttributeModifier("align","left"));
		}
		this.add(label);
	}

}
