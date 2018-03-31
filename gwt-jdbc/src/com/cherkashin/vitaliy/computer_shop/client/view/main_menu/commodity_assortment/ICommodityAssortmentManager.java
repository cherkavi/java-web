package com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("commodity_assortment_manager")
public interface ICommodityAssortmentManager extends RemoteService{
	/** получить из файла XLS данные в виде таблицы 
	 * @param fieldId - уникальный идентификатор загруженного файла
	 * @return 
	 * 	<li> <b>List</b> содержащий €чейки в виде массива строк </li> 
	 * 	<li> <b>null</b> ошибка загрузки данных </li> 
	 * */
	public ArrayList<String[]> getTableData(String fileId);
	
	/** "залить" в ассортимент данные ( фактически вставл€ет новые данные )
	 * @param fileName - наименование файла, который был "залит" 
	 * @param fieldNames - список полей 
	 * <ul>
	 * 	<li>Firm code</li>
	 * 	<li>Name</li>
	 * 	<li>Warranty</li>
	 * 	<li>Price</li>
	 * </ul>
	 * @param fieldPosition - соответствующие номера столбцов ( 0..RowSize-1) дл€ списка полей
	 * @param assortmentQuestion - список объектов, которые уже наполнены (получены от предыдущего обращени€ к данному методу) и вернулись от клиента
	 * @param margePercent - процент, на который следует провер€ть обновл€емые значени€, если они меньше - необходимо переустановить цену продажи   
	 * @return 
	 * <ul>
	 * 	<li><b>null</b> - нужно проверить пол€ и их соответстви€ - есть ошибки в анализе </li>
	 * 	<li><b>empty List</b> - данные готовы дл€ вызова следующего метода - {@link #updateData}</li>
	 * 	<li><b>List</b> - объекты, по которым есть вопросы на причастность к классу и на цену продажи </li>
	 * </ul>
	 *  */
	public ArrayList<CommodityAssortmentQuestion> prepareData(String fileName, 
															  String[] fieldNames, 
															  int[] fieldPosition,
															  float margePercent,
															  ArrayList<CommodityAssortmentQuestion> assortmentQuestion);
	
	/** получить коды и наименовани€ из таблицы CLASS 
	 * @return список [код, значение]
	 * */
	public ArrayList<ClassIdentifier> getClassMap();
	
	/** получить текущий курс валют */
	public Float getCourse();
}
