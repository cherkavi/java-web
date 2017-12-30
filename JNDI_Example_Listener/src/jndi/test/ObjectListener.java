package jndi.test;

import javax.naming.event.NamespaceChangeListener;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;

public class ObjectListener implements NamespaceChangeListener {
	private String name;
	
	public ObjectListener(String name){
		this.name=name;
	}
	

	@Override
	public void namingExceptionThrown(NamingExceptionEvent ex) {
		System.out.println("Exception:"+ex.getException().getMessage());
	}

	@Override
	public void objectAdded(NamingEvent event) {
		System.out.println("ADD Name:"+name+"      OldValue:"+event.getOldBinding().getObject() +"    NewValue:"+event.getNewBinding().getObject());
	}

	@Override
	public void objectRemoved(NamingEvent event) {
		System.out.println("Remove Name:"+name+"      OldValue:"+event.getOldBinding().getObject() +"    NewValue:"+event.getNewBinding().getObject());
	}

	@Override
	public void objectRenamed(NamingEvent event) {
		System.out.println("Renamed Name:"+name+"      OldValue:"+event.getOldBinding().getObject() +"    NewValue:"+event.getNewBinding().getObject());
	}

}
