package com.bonclub.health.server_impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bonclub.health.common.AnswerRegUser;

import com.bonclub.health.common.UserData;
import com.bonclub.health.server_interface.IRegisterUser;

public class RegisterUser implements IRegisterUser {
	private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss dd.MM");
	
	@Override
	public AnswerRegUser registerUser(UserData user) {
		System.out.println("Test connect: "+sdf.format(new java.util.Date())+" value:"+user.toString());
		AnswerRegUser returnValue=new AnswerRegUser();
		returnValue.setUniqueId(user.getUser());
		returnValue.setUser(user.getUser());
		returnValue.setPassword(user.getPassword());
		returnValue.setUniqueId(this.getUniqueIdByUser(user));
		returnValue.setUrlPdf(this.getUrlForGetPdf(user, returnValue));
		return returnValue;
	}

	
	/** на основании пользователя получить уникальный номер-идентификатор данного пользователя */
	private String getUniqueIdByUser(UserData user){
		return Long.toString((new Date()).getTime());
	}
	
	private String getUrlForGetPdf(UserData data, AnswerRegUser returnValue){
		return "http://localhost:8080/TempXFire/GetPdf?uniqueId="+returnValue.getUniqueId();
	}
}
