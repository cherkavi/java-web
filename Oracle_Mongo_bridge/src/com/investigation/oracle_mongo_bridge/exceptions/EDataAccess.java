package com.investigation.oracle_mongo_bridge.exceptions;

/**
 * root class for represent the Exception 
 */
public class EDataAccess extends Exception {
	private final static long serialVersionUID=1L;
	
	public EDataAccess(){
		super();
	}
	
	public EDataAccess(String message){
		this(message, null);
	}

	public EDataAccess(Exception ex){
		this(null, ex);
	}
	
	public EDataAccess(String message, Exception ex){
		super(message, ex);
	}
}
