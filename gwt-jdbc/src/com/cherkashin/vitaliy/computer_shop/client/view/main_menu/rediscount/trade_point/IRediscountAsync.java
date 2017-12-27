package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.rediscount.trade_point;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IRediscountAsync {

	/** ��������� ������� ���������� ��������� �� ��������� ����� 
	 * @param pointKod - ��� �������� �����
	 * @return 
	 * <ul>
	 * 	<li> <b>true</b> - �������� ������ </li>
	 * 	<li> <b>false</b> - ��� ��������� �� �������� ��� </li>
	 * </ul>
	 */
	void isRediscountExists(int pointKod, AsyncCallback<Boolean> callback);

	/** ������� ��������� ����� ��� ��������� ( ��������� ����� ������� ���� ��������� � ������� ) 
	 * @param pointKod - ��� �������� �����
	 * @return 
	 * <ul>
	 * 	<li> <b>true</b> - �������� ������ </li>
	 * 	<li> <b>false</b> - ������ �������� ��������� </li>
	 * </ul>
	 */
	void createRediscount(int pointKod, AsyncCallback<Boolean> callback);

	/**
	 * ��������� ����������� ��� ( ����������� �������� �����-����� )
	 * @param pointKod - ��� �������� ����� 
	 * @param readedKod - ����������� ��� 
	 * @return
	 * <ul>
	 * 	<li> <b>true</b> - ��� ������� � �������� </li>
	 * 	<li> <b>false</b> - ��� �� ������� </li>
	 * </ul>
	 */
	void saveBarCode(int pointKod, String readedCod, AsyncCallback<Boolean> callback);

}
