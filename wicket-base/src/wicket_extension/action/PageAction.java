package wicket_extension.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.wicket.Page;
import java.io.Serializable;

/** действие, которое выдает страницу дл€ перенаправлени€ запроса от одной страницы на другую,
 * предварительно создав данный класс, и вызвав все добавленные методы */
public class PageAction implements Serializable{
	private final static long serialVersionUID=1L;
	
	private Class<? extends Page> pageClass;
	private Class<?>[] constructorClasses;
	private Object[] constructorObjects;
	
	private ArrayList<String> methodName=new ArrayList<String>();
	private ArrayList<Class<?>[]> methodClasses=new ArrayList<Class<?>[]>();
	private ArrayList<Object[]> methodObjects=new ArrayList<Object[]>();
	
	/** действие, которое выдает страницу дл€ перенаправлени€ запроса от одной страницы на другую,
	 * предварительно создав данный класс, и вызвав все добавленные методы */
	public PageAction(Class<? extends Page> pageClass){
		this.pageClass=pageClass;
	}

	/** действие, которое выдает страницу дл€ перенаправлени€ запроса от одной страницы на другую,
	 * предварительно создав данный класс, и вызвав все добавленные методы */
	public PageAction(Class<? extends Page> pageClass, Class<?>[] classes, Object[] objects){
		this.pageClass=pageClass;
		this.constructorClasses=classes;
		this.constructorObjects=objects;
	}
	
	/** им€ метода, который должен быть вызван */
	public void addMethodForCall(String methodName){
		this.methodName.add(methodName);
		this.methodClasses.add(null);
		this.methodObjects.add(null);
	}

	/** им€ метода, который должен быть вызван + параметры дл€ вызова данного метода */
	public void addMethodForCall(String methodName, Class<?>[] classes, Object[] objects){
		this.methodName.add(methodName);
		this.methodClasses.add(classes);
		this.methodObjects.add(objects);
	}
	
	@SuppressWarnings("unchecked")
	public Page getRedirectPage() throws Exception{
		// создать экземпл€р класса
		Constructor<Page> constructor=null;
		constructor=(Constructor<Page>)this.pageClass.getConstructor(this.constructorClasses);
		Page page=constructor.newInstance(this.constructorObjects);
		for(int counter=0;counter<this.methodName.size();counter++){
			// вызвать все дополнительные методы
			try{
				Method method=this.pageClass.getMethod(this.methodName.get(counter), this.methodClasses.get(counter));
				method.invoke(page, this.methodObjects.get(counter));
			}catch(Exception ex){};
		}
		// вернуть экземпл€р класса
		return page;
	}
}
