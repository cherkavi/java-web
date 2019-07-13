package bonclub.office_private.web_gui;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.request.WebClientInfo;

public abstract class BasePage extends WebPage{
	public BasePage(){
		WebMarkupContainer cssLink=new WebMarkupContainer("main_style");
		
		cssLink.add(new SimpleAttributeModifier("href","BasePage"+getSuffix()+".css"));
		
		this.add(cssLink);
		Model<String> model=new Model<String>();
		try{
			model.setObject(this.getString("title_message"));
		}catch(Exception ex){
		}
		Label titleMessage=new Label("title_message",model);
		this.add(titleMessage);
	}
	
	/** получить суффикс для CSS файла в зависимости от браузера */
	private String getSuffix(){
		String userAgent=null;
		try{
			userAgent=((WebClientInfo)this.getRequestCycle().getClientInfo()).getUserAgent();
		}catch(Exception ex){};
		if(userAgent!=null){
			if(isChorme(userAgent)){
				return "_Chrome";
			}else if (isExplorer(userAgent)){
				return "_Explorer";
			}else if (isOpera(userAgent)){
				return "_Opera";
			}else if (isSafari(userAgent)){
				return "_Safari";
			}else return "";
		}else{
			return "";
		}
	}
	
	private boolean isChorme(String userAgent){
		return isSubString(userAgent, "Chrome")>0;
	}
	private boolean isExplorer(String userAgent){
		return isSubString(userAgent, "MSIE")>0;
	}
	
	private boolean isOpera(String userAgent){
		return isSubString(userAgent, "Opera")>=0;
	}
	
	private boolean isSafari(String userAgent){
		return isSubString(userAgent, "Safari")>0;
	}

	/** получить индекс строки текста  */
	private int isSubString(String source, String find){
		if(source!=null){
			return source.indexOf(find);
		}else{
			return (-1);
		}
	}
	
}
