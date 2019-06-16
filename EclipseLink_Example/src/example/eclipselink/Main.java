package example.eclipselink;

import java.util.HashMap;
import java.util.List;

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
		if(q.getResultList().size()>0){
			ExampleEntity example=read(em, 3);
			System.out.println("Read OK:"+example.getName()+"   "+example.getId());
		}else{
			ExampleEntity example=new ExampleEntity();
			example.setId(4);
			example.setName("name_4");
			example.setDescription("description_4");
			example.setTempValue(5);
			write(em, example);
			System.out.println("Write OK:"+example.getName()+"   "+example.getId());
		}
		em.close();
		factory.close();
	}

	/** пример записи объекта через JPA */
	private void write(EntityManager manager, ExampleEntity example){
		manager.persist(example);
	}
	
	/** пример чтения объекта через JPA */
	private ExampleEntity read(EntityManager manager, int key){
		Query q = manager.createQuery("select m from ExampleEntity m where m.id="+key);
		List list=q.getResultList();
		return (ExampleEntity)list.get(0);
	}
	
}
