package com.bonclub.health.server_interface;

import com.bonclub.health.common.AnswerRegUser;
import com.bonclub.health.common.UserData;

public interface IRegisterUser {
	/** ���������������� ������������  */
	public AnswerRegUser registerUser(UserData user);
}
