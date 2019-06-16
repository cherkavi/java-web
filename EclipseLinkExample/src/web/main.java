package web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.eclipselink.ExampleEntity;

/**
 * Servlet implementation class main
 */
public class main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static final String PERSISTENCE_UNIT_NAME = "entity_example";
	private EntityManagerFactory factory= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public main() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	try{
    		factory= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, new HashMap());
    	}catch(Exception ex){
    		System.err.println("ERROR: "+ex.getMessage());
    		ex.printStackTrace(System.err);
    	};
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out=response.getOutputStream();
		out.println("<html>");
		out.println("<body>");
		// PersistenceUnitProperties.JDBC_DRIVER
		if(factory==null){
			factory= Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, new HashMap());
		}
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select m from ExampleEntity m");
		
		if(q.getResultList().size()==0){
			// create object
			out.println("<b> create object begin </b>");
			ExampleEntity example=new ExampleEntity();
			example.setId(3);
			example.setName("Test1");
			example.setDescription("this is temp Entity");
			example.setTempValue(1);
			em.persist(example);
			out.println("<br>");
			out.println("<b>create object DONE </b>");
		}else{
			// output object
			List list=q.getResultList();
			for(int counter=0;counter<list.size();counter++){
				out.println(counter+" : "+list.get(counter).getClass().getName());
				out.println("<br>");
			}
		}
		em.getTransaction().commit();
		out.println("DONE");
		out.println("</body></html>");
	}
	
}
