package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("commodity_assortment_manager")
public interface ICommodityAssortmentManager extends RemoteService{
	/** �������� �� ����� XLS ������ � ���� ������� 
	 * @param fieldId - ���������� ������������� ������������ �����
	 * @return 
	 * 	<li> <b>List</b> ���������� ������ � ���� ������� ����� </li> 
	 * 	<li> <b>null</b> ������ �������� ������ </li> 
	 * */
	public ArrayList<String[]> getTableData(String fileId);
	
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
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - ����� ��������� ���� � �� ������������ - ���� ������ � ������� </li>
	 * 	<li><b>empty List</b> - ������ ������ ��� ������ ���������� ������ - {@link #updateData}</li>
	 * 	<li><b>List</b> - �������, �� ������� ���� ������� �� ������������ � ������ � �� ���� ������� </li>
	 * </ul>
	 *  */
	public ArrayList<CommodityAssortmentQuestion> prepareData(String fileName, 
															  String[] fieldNames, 
															  int[] fieldPosition,
															  float margePercent,
															  ArrayList<CommodityAssortmentQuestion> assortmentQuestion);
	
	/** �������� ���� � ������������ �� ������� CLASS 
	 * @return ������ [���, ��������]
	 * */
	public ArrayList<ClassIdentifier> getClassMap();
	
	/** �������� ������� ���� ����� */
	public Float getCourse();
}
