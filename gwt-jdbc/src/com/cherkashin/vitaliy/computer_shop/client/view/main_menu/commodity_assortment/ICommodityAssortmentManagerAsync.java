package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("commodity_assortment_manager")
public interface ICommodityAssortmentManagerAsync {

	/** �������� �� ����� XLS ������ � ���� ������� 
	 * @param fieldId - ���������� ������������� ������������ �����
	 * @param
	 * 	<li> <b>List</b> ���������� ������ � ���� ������� ����� </li> 
	 * 	<li> <b>null</b> ������ �������� ������ </li> 
	 * */
	void getTableData(String fileId, AsyncCallback<ArrayList<String[]>> callback);


	/** �������� ���� � ������������ �� ������� CLASS 
	 * @param ������ [���, ��������]
	 * */
	void getClassMap(AsyncCallback<ArrayList<ClassIdentifier>> callback);

	/** 
	 * @param �������� ������� ���� ����� 
	 * */
	void getCourse(AsyncCallback<Float> callback);

	/** "������" � ����������� ������ ( ���������� ��������� ����� ������ )
	 * @param fileName - ������������ �����, ������� ��� "�����" 
	 * @param fieldNames - ������ ����� 
	 * <ul>
	 * 	<li>Firm code</li>
	 * 	<li>Name</li>
	 * 	<li>Warranty</li>
	 * 	<li>Price</li>
	 * </ul>
	 * @param fieldPosition - ��������������� ������ �������� ( 0..RowSize-1) ��� ������ �����
	 * @param assortmentQuestion - ������ ��������, ������� ��� ��������� (�������� �� ����������� ��������� � ������� ������) � ��������� �� �������
	 * @param margePercent - �������, �� ������� ������� ��������� ����������� ��������, ���� ��� ������ - ���������� �������������� ���� �������   
	 * @param callback 
	 * <ul>
	 * 	<li><b>null</b> - ����� ��������� ���� � �� ������������ - ���� ������ � ������� </li>
	 * 	<li><b>empty List</b> - ������ ������ ��� ������ ���������� ������ - {@link #updateData}</li>
	 * 	<li><b>List</b> - �������, �� ������� ���� ������� �� ������������ � ������ � �� ���� ������� </li>
	 * </ul>
	 *  */
	void prepareData(String fileName, String[] fieldNames, int[] fieldPosition,
			float margePercent,
			ArrayList<CommodityAssortmentQuestion> assortmentQuestion,
			AsyncCallback<ArrayList<CommodityAssortmentQuestion>> callback);

}
