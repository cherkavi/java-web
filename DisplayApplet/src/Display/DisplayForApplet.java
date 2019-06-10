package Display;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayForApplet
 */
public class DisplayForApplet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static boolean FLAG_DEBUG=true;
    /** 
     * метод для отладки информации
     */
    protected static void debug(String information){
        if(FLAG_DEBUG==true){
            System.out.print("DisplayForApplet ");
            System.out.print("DEBUG: ");
            System.out.println(information);
        }
    }
    protected static void error(String information){
    	System.out.print("DisplayForApplet ");
    	System.out.print("ERROR: ");
    	System.out.println(information);
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayForApplet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	
	private  void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		{
			Enumeration parameter_names=request.getParameterNames();
			String key;
			try{
				while(parameter_names.hasMoreElements()){
					key=(String)parameter_names.nextElement();
					debug("doAction: key: "+key+"    value:"+request.getParameter(key));
				}
			}catch(Exception ex){
				error("doAction getParameter Error:"+ex.getMessage());
			}
		}
		response.setContentType("text/html; charset=UTF-8");
	      //PrintWriter out = res.getWriter();
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
		
		//PrintWriter out=response.getWriter();
		if(request.getParameter("FUNCTIONNAME")!=null){
			// TODO SubCommand.FOR_DISPLAY:Interceptors (SubCommand.CommandName()) анализ функции, которую нужно отобразить на клиенте в HTML
			// реакция на показ запрос отображения устройств для дальнейшего подключения
			if(request.getParameter("FUNCTIONNAME").equals("SHOWDEVICES")){
				// получить имена всех устройств
				action_GetAllDevices(request, out);
			}
			if(request.getParameter("FUNCTIONNAME").equals("SHOWMENU")){
				action_ShowMenu(request,out);
			}
		}else{
			// показать кнопку с возможностью отключения от контекста SmartCard+Reader
			out.write("<b>unknown action</b>");
		}
		out.close();
	}
	
	/** 
	 * вывести в сервлет список возможных высокоуровневых действий
	 */
	private void action_ShowMenu(HttpServletRequest request, PrintWriter out){
		out.write("<table border=1>");
		out.write("<tr>");
		out.write("<th>");
		out.write("<b>  Menu </b>");
		out.write("</th>");
		out.write("</tr>");
		// button show_serial_number
		out.write("<tr align=\"center\">\n");
		out.write("    <td>\n");
		out.write("<input type=\"button\"  ");
		out.write("value=\"GetSerialNumber\" "); 
		                                    // ActionName
		out.write("onclick=\"applet_action('GetSerialNumber',new Array(),new Array())\">\n");
		out.write("    </td>\n");
		out.write("</tr>\n");
		// button check_serial_number
		out.write("<tr align=\"center\">\n");
		out.write("    <td>\n");
		out.write("<input type=\"button\"  ");
		out.write("value=\"CheckSerialNumber\" "); 
                                            // ActionName
		out.write("onclick=\"applet_action('CheckSerialNumber',new Array(),new Array())\">\n");
		out.write("    </td>\n");
		out.write("</tr>\n");
		
		// button disconnect
		out.write("<tr>\n");
		out.write("    <td>\n");
		out.write("    </td>\n");
		out.write("</tr>\n");

		out.write("</table>");
	}
	
	/**
	 *  вывести в Servlet.response.out данные страницу, которая отобразит возможность подключения к Reader
	 * */
	private void action_GetAllDevices(HttpServletRequest request, PrintWriter out){
		out.write("Select your device: <br>");
		out.write("<select id=\"devices\">");
		Enumeration parameter_names=request.getParameterNames();
		String key;
		String value;
		int counter=0;
		while(parameter_names.hasMoreElements()){
			key=(String)parameter_names.nextElement();
			value=request.getParameter(key);
			if(!key.equals("FUNCTIONNAME")){
				if(counter==0){
					out.write("<option value=\""+value+"\" selected=\"selected\">"+value+"</option>\n");
				}else{
					out.write("<option value=\""+value+"\">"+value+"</option>\n");
				}
				counter++;
			}
		}
		out.write("</select>\n");
		out.write("<input type=\"button\"  ");
		out.write("value=\"Connect\" ");
		                                 // Action.Name
		out.write("onclick=\"applet_action('ConnectTo',new Array('DEVICE'),new Array(get_selected_value('devices')) )\">\n");
	}

}
