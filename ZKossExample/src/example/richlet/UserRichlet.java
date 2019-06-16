package example.richlet;

import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class UserRichlet extends GenericRichlet{

	public UserRichlet(){
		System.out.println("created UserRichlet ");
	}
	@Override
	public void service(Page page) {
		Window win=new Window("this is user richlet","normal",false);
		Label label=new Label("this is label into Window");
		label.setParent(win);

		win.setPage(page);
	}
}

/* ---------- zk.xml -----------
 * 
 <richlet>
   	<richlet-class>biz.opengate.helloworld.Index</richlet-class> 
   	<richlet-url>/index</richlet-url> 
 	</richlet>
 * 
 */
 
