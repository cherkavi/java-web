package wicket_extension.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.wicket.Page;
import java.io.Serializable;

/** ��������, ������� ������ �������� ��� ��������������� ������� �� ����� �������� �� ������,
 * �������������� ������ ������ �����, � ������ ��� ����������� ������ */
public class PageAction implements Serializable{
	private final static long serialVersionUID=1L;
	
	private Class<? extends Page> pageClass;
	private Class<?>[] constructorClasses;
	private Object[] constructorObjects;
	
	private ArrayList<String> methodName=new ArrayList<String>();
	private ArrayList<Class<?>[]> methodClasses=new ArrayList<Class<?>[]>();
	private ArrayList<Object[]> methodObjects=new ArrayList<Object[]>();
	
	/** ��������, ������� ������ �������� ��� ��������������� ������� �� ����� �������� �� ������,
	 * �������������� ������ ������ �����, � ������ ��� ����������� ������ */
	public PageAction(Class<? extends Page> pageClass){
		this.pageClass=pageClass;
	}

	/** ��������, ������� ������ �������� ��� ��������������� ������� �� ����� �������� �� ������,
	 * �������������� ������ ������ �����, � ������ ��� ����������� ������ */
	public PageAction(Class<? extends Page> pageClass, Class<?>[] classes, Object[] objects){
		this.pageClass=pageClass;
		this.constructorClasses=classes;
		this.constructorObjects=objects;
	}
	
	/** ��� ������, ������� ������ ���� ������ */
	public void addMethodForCall(String methodName){
		this.methodName.add(methodName);
		this.methodClasses.add(null);
		this.methodObjects.add(null);
	}

	/** ��� ������, ������� ������ ���� ������ + ��������� ��� ������ ������� ������ */
	public void addMethodForCall(String methodName, Class<?>[] classes, Object[] objects){
		this.methodName.add(methodName);
		this.methodClasses.add(classes);
		this.methodObjects.add(objects);
	}
	
	@SuppressWarnings("unchecked")
	public Page getRedirectPage() throws Exception{
		// ������� ��������� ������
		Constructor<Page> constructor=null;
		constructor=(Constructor<Page>)this.pageClass.getConstructor(this.constructorClasses);
		Page page=constructor.newInstance(this.constructorObjects);
		for(int counter=0;counter<this.methodName.size();counter++){
			// ������� ��� �������������� ������
			try{
				Method method=this.pageClass.getMethod(this.methodName.get(counter), this.methodClasses.get(counter));
				method.invoke(page, this.methodObjects.get(counter));
			}catch(Exception ex){};
		}
		// ������� ��������� ������
		return page;
	}
}
