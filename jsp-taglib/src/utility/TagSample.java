package utility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 
   1.

      Call the setPageContext() method of the BodyTag to set the current PageContext for it to use.
   2.

      Call the setParent() method to set any parent of the encountered Tag (or null if none).
   3.

      Set any attributes defined to be given to the BodyTag.
   4.

      Call the doStartTag() method of the BodyTag. If this method returns SKIP_BODY, the doEndTag() method should be called (see 9 below). If EVAL_BODY_TAG is returned, continue below.
   5.

      Call the setBodyContent() method of the BodyTag to set the current BodyContent for it to use (if there is at least one evaluation of the body of the BodyTag).
   6.

      Call the doInitBody() method of the BodyTag. This is where we will normally put any instructions that should be taken care of before the body of the BodyTag is evaluated for the first time.
   7.

      Call the doAfterBody() method of the BodyTag after each evaluation of the BodyTags body. If this method returns EVAL_BODY_TAG, the body of the BodyTag should be evaluated anew, and then this method should be called again. If this method returns SKIP_BODY, continue below.
   8.

      Call the doEndTag() method of the BodyTag after the last evaluation of the BodyTags body. If this method returns EVAL_PAGE, the container will evaluate the rest of the JSP page. If this method returns SKIP_PAGE, the Container will not evaluate the rest of the JSP page.
   9.

      Call the release() method. This method can be used by the Tag developer to release any resources that the Tag was using. Please notice that it is up to the Container to call the release() method when seemed fit, so you can't rely on this method being called at any specific time. In theory, this method could be called 12 days after the Tag was used. For this reason, any code that must be executed at the end of the Tag usage should be called from the doEndTag() method (see 8 above).
 *
 */

public class TagSample extends BodyTagSupport{
	private static final long serialVersionUID=1L;
	
	/** размер шрифта */
	private String fontSize;
	/** цвет */
	private String color;
	
	public TagSample(){
		
	}
	
	private BodyContent bodyContent;
	
	@Override
	public void setBodyContent(BodyContent b) {
		this.bodyContent=b;
	}
	
	@Override
	public BodyContent getBodyContent(){
		return bodyContent;
	}
	
	@Override
	public void release() {
		this.fontSize=null;
		this.color=null;
	}
	
	private boolean isInteger(String value){
		boolean returnValue=false;
		try{
			Integer.parseInt(value);
			returnValue=true;
		}catch(NumberFormatException ex){};
		return returnValue;
	}
	
	@Override
	public int doStartTag() throws JspException{
		try{
			System.out.println("print:"+this.getBodyContent());
		}catch(Exception ex){
			System.out.println("body is empty");
		}
		// return SKIP_BODY; // не выводить тело 
		return EVAL_BODY_BUFFERED; // тело в буфер 
	}

	@Override
	public int doEndTag() throws JspException {
		String body="";
		try{
			body=getBodyContent().getString();
		}catch(Exception ex){};
		System.out.println("body content:"+body);
		if(body==null){
			body="";
		};
		if(color==null){
			color="red";
		}
		if((fontSize==null)||(isInteger(fontSize))){
			fontSize="10";
		}
		JspWriter out=pageContext.getOut();
		try{
			out.println("<font size=\""+fontSize+"\"  color=\""+color+"\">"+body+"</font>");
		}catch(Exception ex){
			System.err.println("TagSample#Exception doEndTag: "+ex.getMessage());
		}
		return EVAL_PAGE;
	}


	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
