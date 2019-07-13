package example.eclipselink;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main {

	public static void main(String[] args){
		System.out.println("begin");
		Main main=new Main();
		
		System.out.println("-end-");
	}
	
	private static final String PERSISTENCE_UNIT_NAME = "entity_example";
	private EntityManagerFactory factory;
	
	public Main(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, new HashMap());
		// PersistenceUnitProperties.JDBC_DRIVER
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("select m from ExampleEntity m");

		em.close();
		factory.close();
	}

	/** пример записи объекта через JPA */
	private void write(ExampleEntity example){
		
	}
	
	/** пример чтения объекта через JPA */
	private ExampleEntity read(int key){
		return null;
	}
	
}
