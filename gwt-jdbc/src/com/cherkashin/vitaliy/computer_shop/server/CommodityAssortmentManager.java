package com.cherkashin.vitaliy.computer_shop.server;

import java.io.File;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;

import org.hibernate.Session;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.ClassIdentifier;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.CommodityAssortmentQuestion;
import com.cherkashin.vitaliy.computer_shop.client.view.main_menu.commodity_assortment.ICommodityAssortmentManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.StaticConnector;
import database.ConnectWrap;
import database.wrap.Assortment;
import database.wrap.Price;

/** управление загружаемым файлом  */
public class CommodityAssortmentManager extends RemoteServiceServlet implements ICommodityAssortmentManager {
	private final static long serialVersionUID=1L;
	private String tempDirectory;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		tempDirectory=this.getServletContext().getInitParameter("temp_directory");
		if(tempDirectory!=null){
			tempDirectory=tempDirectory.trim();
		}else{
			tempDirectory=System.getProperty("java.io.tmpdir");
			if(tempDirectory!=null){
				tempDirectory=tempDirectory.trim();
			}
		}
		
		String fileSeparator=System.getProperty("file.separator");
		if(tempDirectory.endsWith(fileSeparator)==false){
			tempDirectory=tempDirectory+fileSeparator;
		}
	}
	
	@Override
	public ArrayList<String[]> getTableData(String fileId) {
		// полный путь к файлу 
		String filePath=this.tempDirectory+fileId;
		File file=new File(filePath);
		if(file.exists()){
			Workbook workbook=null;
			try{
				// открыть файл EXCEL
				workbook=Workbook.getWorkbook(file);
				Sheet sheet=workbook.getSheet(0);
				// определить максимальное значение для строк ( не более 1000)
				int maxRowCount=sheet.getRows();
				if(maxRowCount>1000)maxRowCount=1000;
				
				// определить максимальное значение для столбцов ( не более 10 )				
				int maxColumnCount=sheet.getColumns();
				if(maxColumnCount>10)maxColumnCount=10;
				
				ArrayList<String[]> returnValue=new ArrayList<String[]>();
				for(int rowCounter=0;rowCounter<maxRowCount;rowCounter++){
					returnValue.add(convertToArrayOfString(sheet.getRow(rowCounter)));
				}
				workbook.close();
				
				// создать возвращаемое значение
				// наполнить возвращаемое значение 
				return returnValue;
			}catch(Exception ex){
				System.err.println("#getTableData Exception:"+ex.getMessage());
				return null;
			}finally{
				try{
					workbook.close();
				}catch(Exception ex){};
			}
		}else{
			System.err.println("File does not found : "+filePath);
			return null;
		}
	}

	/** преобразовать массив из ячеек в массив из строк */
	private String[] convertToArrayOfString(Cell[] cells){
		if(cells!=null){
			String[] returnValue=new String[cells.length];
			for(int counter=0;counter<cells.length;counter++){
				returnValue[counter]=cells[counter].getContents();
			}
			return returnValue;
		}else{
			return new String[]{};
		}
	}

	
	/** получить текущий курс  */
	private float getCourse(ConnectWrap connector) {
		float returnValue=0;
		try{
			PreparedStatement ps=connector.getConnection().prepareStatement("select first 1 skip 0 currency_value from course where course.date_set <=? order by kod desc");
			ps.setDate(1, new java.sql.Date((new java.util.Date()).getTime()));
			ResultSet rs=ps.executeQuery();
			rs.next();
			returnValue=rs.getFloat(1);
			ps.close();
		}catch(Exception ex){
			System.err.println("CommodityAssortmentManager#getCourse Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	/** сохранить переданный ассортимент 
	 * @param connector - соединение с базой данных 
	 * @param assortmentRow - строка из Excel файла
	 * @param controlMinPercent - минимальный процент, который должен быть при обновлении цены закупки
	 * @param course - курс валюты на текущий момент  
	 * @return 
	 * 	<ul>
	 * 		<li><b>null</b> - успешно обработан </li>
	 * 		<li><b>{@link CommodityAssortmentQuestion}</b> - пользователь должен ввести класс и цену продажи </li>
	 * 	</ul>
	 * */
	private CommodityAssortmentQuestion saveAssortmentRow(ConnectWrap connector,
								   						  AssortmentRow assortmentRow,
								   						  float controlMinPercent,
								   						  float course) {
		CommodityAssortmentQuestion returnValue=null;
		// получить по коду товар из ассортимента
		int assortmentKod=0;
		if(assortmentRow!=null){
			assortmentKod=this.getAssortmentKod(connector.getConnection(), assortmentRow.getKod());
		}
		if(assortmentKod>0){
			// обновить гарантию и цену
			returnValue=this.updateAssortmentPriceBuy(connector, assortmentKod, assortmentRow, controlMinPercent, course);
		}else{
			// записать полностью новую ассортиментную единицу
			returnValue=new CommodityAssortmentQuestion(assortmentRow.getKod(), assortmentRow.getName(), assortmentRow.getWarranty(), assortmentRow.getPriceUSD()*course);
		}
		return returnValue;
	}
	
	
	/**
	 * соединение с базой данных 
	 * @param connection - соединение с базой данных 
	 * @param assortmentKod - код ассортимента для обновления 
	 * @param assortmentRow - объект, который нужно считать приоритетным
	 * @param controlMinPercent - минимальный маржевой процент ( на сколько процентов цена продажи больше цены закупки )
	 * @param couse - курс валюты в данный момент 
	 * @return
	 * <ul>
	 * 	<li><b>null</b> данные успешно обновлены</li>
	 * 	<li><b>{@link CommodityAssortmentQuestion}</b> - объект, который требует уточнения </li>
	 * </ul>
	 */
	private CommodityAssortmentQuestion updateAssortmentPriceBuy(ConnectWrap connector, int assortmentKod, AssortmentRow assortmentRow, float controlMinPercent, float course) {
		Connection connection=connector.getConnection();
		CommodityAssortmentQuestion returnValue=null;
		try{
			// получить ассортимент и проверить его на идентичность данных ( цена ассортимента идентична текущей ) - возможно ассортимент не требует обновления 
			StringBuffer query=new StringBuffer();
			query.append("select assortment.bar_code_company, assortment.class_kod, price.price_buying, price.price, price.kod price_kod \n");
			query.append("from assortment \n");
			query.append("inner join price on price.kod=assortment.price_kod \n");
			query.append("where assortment.kod="+assortmentKod);
			ResultSet rs=connection.createStatement().executeQuery(query.toString());
			if(rs.next()){
				float priceBuy=rs.getFloat("price_buying");
				float priceSell=rs.getFloat("price");
				if(roundTo(2, assortmentRow.getPriceUSD())!=priceBuy){
					// цены отличаются - необходимо обновление данных 
						// проверить, можно ли заменить только цену закупки
					if (((priceSell/assortmentRow.getPriceUSD())-1)<(controlMinPercent/100)){
						// достаточно заменить только цену закупки 
						this.createNewPriceBuyForAssortment(connector, assortmentKod, assortmentRow.getPriceUSD());
						return null;
					}else{
						// необходима полная замена продажи 
						returnValue=new CommodityAssortmentQuestion(assortmentRow.getKod(), assortmentRow.getName(), assortmentRow.getWarranty(), assortmentRow.getPriceUSD()*course);
						returnValue.setClassKod(rs.getInt("class_kod"));
					}
				}else{
					// данные не требуют обновления 
					returnValue=null;
				}
			}else{
				throw new Exception("Ассортимент не найден - критическая ошибка ( переданный код не найден ):"+assortmentRow);
			}
			// проверить ассортимент на  
		}catch(Exception ex){
			System.err.println("CommodityAssortmentManager#updateAssortment Exception:"+ex.getMessage());
			returnValue=new CommodityAssortmentQuestion(assortmentRow.getKod(), assortmentRow.getName(), assortmentRow.getWarranty(), assortmentRow.getPriceUSD()*course);
		}
		return returnValue;
	}

	/** округлить до определенного кол-ва знаков  */
	private static float roundTo(int sign, float value){
		int tempValue=1;
		for(int counter=0;counter<sign;counter++){
			tempValue*=10;
		}
		value=Math.round(value*tempValue);
		return (float)((float)value)/(float)tempValue;
	}
	
	/** по указанному ассортименту создать новую запись в таблице Price с обновленной ценой закупки  */
	private void createNewPriceBuyForAssortment(ConnectWrap connector, int assortmentKod, float priceBuy) throws SQLException, NullPointerException{
		/** ассортимент, который обновляется  */
		Assortment assortment=(Assortment)connector.getSession().get(Assortment.class, assortmentKod);
		Price priceOld=(Price)connector.getSession().get(Price.class, assortment.getPriceKod());
		Price priceNew=priceOld.clone();
		Session session=connector.getSession();
		try{
			session.beginTransaction();
			// сохранить новую цену в базе 
			priceNew.setKod(0);
			priceNew.setNextKod(null);
			priceNew.setValid(1);
			priceNew.setDateWrite(new Date());
			priceNew.setPriceBuying(priceBuy);
			session.save(priceNew);
			// обновить старую цену - сделать не действительной 
			priceOld.setNextKod(priceNew.getKod());
			priceOld.setValid(0);
			session.update(priceOld);

			assortment.setPriceKod(priceNew.getKod());
			session.update(assortment);
			session.getTransaction().commit();
		}finally{
			try{
				session.getTransaction().rollback();
			}catch(Exception ex){};
		}
		// получить полную запись по таблице PRICE
		// создать новую запись в таблице PRICE
		
	}

	/**
	 * получить ASSORTMENT.KOD на основании объекта, прочитанного из Excel 
	 * @param connection - соединение с базой данных 
	 * @param row - объект, прочитанный из Excel 
	 * @return 
	 * <ul>
	 * 	<li><b>0</b> - данный объект в базе не обнаружен </li>
	 * 	<li><b>&gt0</b> - ASSORTMENT.KOD</li>
	 * </ul>
	 */
	private int getAssortmentKod(Connection connection, int barCodeCompany){
		int returnValue=0;
		try{
			ResultSet rs=connection.createStatement().executeQuery("select * from assortment where assortment.bar_code_company="+barCodeCompany);
			if(rs.next()){
				returnValue=rs.getInt("kod");
			}
		}catch(Exception ex){
			System.err.println("CommodityAssortmentManager#getAssortmentKod Exception:"+ex.getMessage());
			returnValue=0;
		}
		return returnValue;
	}

	@Override
	public ArrayList<ClassIdentifier> getClassMap() {
		ArrayList<ClassIdentifier> returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Connection connection=connector.getConnection();
			ResultSet rs=connection.createStatement().executeQuery("select kod, name from class order by kod");
			returnValue=new ArrayList<ClassIdentifier>();
			while(rs.next()){
				returnValue.add(new ClassIdentifier(rs.getInt("kod"), rs.getString("name")));
			}
			rs.getStatement().close();
		}catch(Exception ex){
			System.err.println("CommodityAssortmentManager#getClassMap Exception:"+ex.getMessage());
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}

	@Override
	public Float getCourse() {
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			return this.getCourse(connector);
		}catch(Exception ex){
			System.err.println("CommodityAssortmentManager#getCourse Exception:"+ex.getMessage());
			return null;
		}finally{
			try{
				connector.close();
			}catch(Exception ex){};
		}
		
		
	}

	@Override
	public ArrayList<CommodityAssortmentQuestion> prepareData(String fileName, 
															  String[] fieldNames, 
															  int[] fieldPosition, 
															  float margePercent, 
															  ArrayList<CommodityAssortmentQuestion> assortmentQuestionResponseList) {
		int amount=0;
		for(int counter=0;counter<fieldNames.length;counter++){
			if(fieldPosition[counter]>=0)amount++;
		}
		if(amount<fieldPosition.length){
			System.err.println("need to select all fields");
			return null;
		}else{
			ConnectWrap connector=StaticConnector.getConnectWrap();
			
			Workbook workbook=null;
			try{
				File file=new File(this.tempDirectory+fileName);
				if(file.exists()==false)throw new Exception("File does not found");
				// открыть файла
				workbook=Workbook.getWorkbook(file);
				Sheet sheet=workbook.getSheet(0);
				float course=this.getCourse(connector);
				if(course==0)throw new Exception("CommodityAssortmentManager#preapreData course was not got ");
				AssortmentRowBuilder builder=new AssortmentRowBuilder(course, fieldNames, fieldPosition);
				int rowCount=sheet.getRows();
				// пробежаться по всем строкам и проанализировать те строки, которые требуют вставки, а какие требуют UPDATE
				/** ошибочные строки, которые не были идентифицированы как ассортимент */
				int errorRowCount=0;
				ArrayList<CommodityAssortmentQuestion> returnValue=new ArrayList<CommodityAssortmentQuestion>();
				for(int rowCounter=0;rowCounter<rowCount;rowCounter++){
					AssortmentRow assortmentRow=builder.getAssortmentRow(sheet.getRow(rowCounter));
					if(assortmentRow!=null){
						// сохранить каждую строку в базе данных
						CommodityAssortmentQuestion assortmentQuestion=saveAssortmentRow(connector, assortmentRow, margePercent, course);
						if(assortmentQuestion!=null){
							// проверить на наличие в ответе от пользователя
							int responseIndex=(-1);
							if(assortmentQuestionResponseList!=null){
								responseIndex=assortmentQuestionResponseList.indexOf(assortmentQuestion);
							}
							if(responseIndex>=0){
								// ответ на вопрос (CLASS, PRICE_SELL) уже получен - сохранить 
								if(this.insertOrUpdateAssortment(connector, assortmentQuestionResponseList.get(responseIndex), course)==false){
									returnValue.add(assortmentQuestion);
								}
							}else{
								// ответ на вопрос (CLASS, PRICE_SELL) еще не получен
								returnValue.add(assortmentQuestion);
							}
						}
					}else{
						// не удалось получить AssortmentRow из файла Excel
						errorRowCount++;
					}
				}
				// проверить на полностью ошибочну обработку данных 
				if(errorRowCount==rowCount){
					return null;
				}
				return returnValue;
			}catch(Exception ex){
				System.err.println("CommodityAssortmentManager#saveData Exception:"+ex.getMessage());
				return null;
			}finally{
				try{
					workbook.close();
				}catch(Exception ex){};
				try{
					connector.close();
				}catch(Exception ex){};
			}
		}
	}

	/** вставить новый ассортимент в таблицу Assortment */
	private boolean insertOrUpdateAssortment(ConnectWrap connector, CommodityAssortmentQuestion commodityAssortmentQuestion, float course) throws Exception {
		boolean returnValue=false;
		int assortmentKod=this.getAssortmentKod(connector.getConnection(), commodityAssortmentQuestion.getKod());
		if(assortmentKod>0){
			// обновить ассортимент 
			Assortment assortment=(Assortment)connector.getSession().get(Assortment.class, assortmentKod);
			Price priceOld=(Price)connector.getSession().get(Price.class, assortment.getPriceKod());
			Price priceNew=priceOld.clone();
			Session session=connector.getSession();
			try{
				session.beginTransaction();
				// сохранить новую цену в базе 
				priceNew.setKod(0);
				priceNew.setNextKod(null);
				priceNew.setValid(1);
				priceNew.setDateWrite(new Date());
				priceNew.setPriceBuying(commodityAssortmentQuestion.getPriceBuy()/course);
				priceNew.setPrice(commodityAssortmentQuestion.getPriceSell()/course);
				session.save(priceNew);
				// обновить старую цену - сделать не действительной 
				priceOld.setNextKod(priceNew.getKod());
				priceOld.setValid(0);
				session.update(priceOld);

				assortment.setPriceKod(priceNew.getKod());
				session.update(assortment);
				session.getTransaction().commit();
				returnValue=true;
			}finally{
				try{
					session.getTransaction().rollback();
				}catch(Exception ex){};
			}
		}else{
			// создать новый ассортиментный номер
			Session session=connector.getSession();
			try{
				if((commodityAssortmentQuestion.getPriceSell()==0)||(commodityAssortmentQuestion.getClassKod()==0)){
					return false;
				}
				// создать PRICE
				Price price=new Price();
				price.setDateWrite(new Date());
				price.setNextKod(null);
				price.setPriceBuying(commodityAssortmentQuestion.getPriceBuy()/course);
				price.setPrice(commodityAssortmentQuestion.getPriceSell()/course);
				price.setValid(1);
				
				// создать ASSORTMENT
				Assortment assortment=new Assortment();
				assortment.setBarCode(commodityAssortmentQuestion.getBarCodeProducer());
				assortment.setBarCodeCompany(Integer.toString(commodityAssortmentQuestion.getKod()));
				assortment.setClassKod(commodityAssortmentQuestion.getClassKod());
				assortment.setDateWrite(new Date());
				assortment.setKodPhoto(null);
				assortment.setName(commodityAssortmentQuestion.getName());
				assortment.setNote(null);
				
				assortment.setWarrantyMonth(commodityAssortmentQuestion.getWarranty());
				// сохранить PRICE
				session.beginTransaction();
				session.save(price);
				// сохранить ASSORTMENT
				assortment.setPriceKod(price.getKod());
				session.save(assortment);
				session.getTransaction().commit();
				returnValue=true;
			}finally{
				try{
					session.getTransaction().rollback();
				}catch(Exception ex){};
			}
		}
		return returnValue;
	}

}

/** объект-построитель {@link AssortmentRow} */
class AssortmentRowBuilder{
	private float course;
	private int columnKod;
	private int columnName;
	private int columnWarranty;
	private int columnPrice;
	/** объект-построитель {@link AssortmentRow}
	 * @param course - курс USD  
	 * @param fieldNames - наименования полей {@link ICommodityAssortmentManager#saveData}
	 * @param fieldPosition - позиции этих полей в строке  
	 * */
	public AssortmentRowBuilder(float course, String[] fieldNames, int[] fieldPosition){
		this.course=course;
		columnKod=fieldPosition[this.getPosition(fieldNames,"Firm code")];
		columnName=fieldPosition[this.getPosition(fieldNames,"Name")];
		columnWarranty=fieldPosition[this.getPosition(fieldNames,"Warranty")];
		columnPrice=fieldPosition[this.getPosition(fieldNames,"Price")];
	}
	
	private int getPosition(String[] values, String value){
		int returnValue=(-1);
		for(int counter=0;counter<values.length;counter++){
			if(values[counter].equalsIgnoreCase(value)){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
	
	/** либо же возвращает {@link AssortmentRow} либо же возвращает null на основании row*/
	public AssortmentRow getAssortmentRow(Cell[] row){
		Integer kod=this.getKod(row);
		String name=this.getName(row);
		Integer warranty=this.getWarranty(row);
		Float price=this.getPrice(row);
		if((kod!=null)&&(name!=null)&&(warranty!=null)&&(price!=null)){
			return new AssortmentRow(kod, name, warranty, price);
		}else{
			return null;
		}
	}

	private Float getPrice(Cell[] row) {
		if(row.length>this.columnPrice){
			String value=row[this.columnPrice].getContents();
			try{
				float returnValue=Float.parseFloat(value.replaceAll(",", "."));
				if(returnValue==0)throw new Exception("price does not found");
				return returnValue/this.course;
			}catch(Exception ex){
				return null;
			}
		}else{
			return null;
		}
	}

	private Integer getWarranty(Cell[] row) {
		if(row.length>this.columnWarranty){
			String value=row[this.columnWarranty].getContents();
			try{
				return Integer.parseInt(value);
			}catch(Exception ex){
				return 0;
			}
		}else{
			return null;
		}
	}

	private String getName(Cell[] row) {
		if(row.length>this.columnName){
			String value=row[this.columnName].getContents();
			try{
				return value.trim();
			}catch(Exception ex){
				return null;
			}
		}else{
			return null;
		}
	}

	private Integer getKod(Cell[] row) {
		if(row.length>this.columnKod){
			String value=row[columnKod].getContents();
			try{
				return Integer.parseInt(value);
			}catch(Exception ex){
				return null;
			}
		}else{
			return null;
		}
	}
}




/** объект, который идентифицирует одну строку для заливки в базу данных в качестве обновления ассортимента  */
class AssortmentRow{
	private int kod;
	private String name;
	private int warranty;
	private float priceUSD;
	
	/** объект, который идентифицирует одну строку для заливки в базу данных в качестве обновления ассортимента  
	 * @param kod - код фирмы  
	 * @param name - наименование 
	 * @param warranty - гарантия
	 * @param priceUSD - цена в USD 
	 */
	public AssortmentRow(int kod, String name, int warranty, float priceUSD){
		this.kod=kod;
		this.name=name;
		this.warranty=warranty;
		this.priceUSD=priceUSD;
	}

	/** получить код данной позиции (код фирмы ) */
	public int getKod() {
		return kod;
	}

	/** получить наименование данного ассортимента */
	public String getName() {
		return name;
	}

	/** гарантия в месяцах  */
	public int getWarranty() {
		return warranty;
	}

	/** цена в долларах  */
	public float getPriceUSD() {
		return priceUSD;
	}
	
	
}
