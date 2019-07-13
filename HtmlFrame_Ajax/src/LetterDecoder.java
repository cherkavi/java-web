

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LetterDecoder
 */
public class LetterDecoder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetterDecoder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		HashMap<String,String> parameters=this.getUtfParameters(request.getQueryString());
		Iterator<Entry<String,String>> iterator=parameters.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry=iterator.next();
			System.out.println(entry.getKey()+"   "+URLDecoder.decode(entry.getValue(), "UTF-8"));
		}
	}

	private HashMap<String,String> getUtfParameters(String query){
		HashMap<String,String> returnValue=new HashMap<String,String>();
		StringTokenizer tokenizer=new StringTokenizer(query,"&");
		while(tokenizer.hasMoreTokens()){
			String nextElement=tokenizer.nextToken();
			int index=nextElement.indexOf('=');
			if(index>0){
				returnValue.put(nextElement.substring(0,index), nextElement.substring(index+1));
			}
		}
		return returnValue;
	}
}
