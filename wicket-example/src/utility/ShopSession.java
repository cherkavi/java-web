package utility;

import org.apache.wicket.Request;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebSession;

public class ShopSession extends WebSession{
	/** страница, на которую можно вернуться из других режимов */
	private WebPage field_web_page;
	/** trolley for commodity */
	private Trolley field_trolley=null;
	
	private static final long serialVersionUID = 1L;

	public ShopSession(Request request) {
		super(request);
	}
	
	/** положить страницу, на которую можно будет вернуться */
	public void pushWebPage(WebPage webPage){
		this.field_web_page=webPage;
	}
	/** получить страницу, которая была положена в сессию ранее для возврата */
	public WebPage popWebPage(){
		return this.field_web_page;
	}
	
	/** получить тележку для покупок */
	public Trolley getTrolley(){
		if(field_trolley==null){
			field_trolley=new Trolley();
		}
		return field_trolley;
	}
}
