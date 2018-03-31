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

/** ���������� ����������� ������  */
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
		// ������ ���� � ����� 
		String filePath=this.tempDirectory+fileId;
		File file=new File(filePath);
		if(file.exists()){
			Workbook workbook=null;
			try{
				// ������� ���� EXCEL
				workbook=Workbook.getWorkbook(file);
				Sheet sheet=workbook.getSheet(0);
				// ���������� ������������ �������� ��� ����� ( �� ����� 1000)
				int maxRowCount=sheet.getRows();
				if(maxRowCount>1000)maxRowCount=1000;
				
				// ���������� ������������ �������� ��� �������� ( �� ����� 10 )				
				int maxColumnCount=sheet.getColumns();
				if(maxColumnCount>10)maxColumnCount=10;
				
				ArrayList<String[]> returnValue=new ArrayList<String[]>();
				for(int rowCounter=0;rowCounter<maxRowCount;rowCounter++){
					returnValue.add(convertToArrayOfString(sheet.getRow(rowCounter)));
				}
				workbook.close();
				
				// ������� ������������ ��������
				// ��������� ������������ �������� 
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

	/** ������������� ������ �� ����� � ������ �� ����� */
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

	
	/** �������� ������� ����  */
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

	/** ��������� ���������� ����������� 
	 * @param connector - ���������� � ����� ������ 
	 * @param assortmentRow - ������ �� Excel �����
	 * @param controlMinPercent - ����������� �������, ������� ������ ���� ��� ���������� ���� �������
	 * @param course - ���� ������ �� ������� ������  
	 * @return 
	 * 	<ul>
	 * 		<li><b>null</b> - ������� ��������� </li>
	 * 		<li><b>{@link CommodityAssortmentQuestion}</b> - ������������ ������ ������ ����� � ���� ������� </li>
	 * 	</ul>
	 * */
	private CommodityAssortmentQuestion saveAssortmentRow(ConnectWrap connector,
								   						  AssortmentRow assortmentRow,
								   						  float controlMinPercent,
								   						  float course) {
		CommodityAssortmentQuestion returnValue=null;
		// �������� �� ���� ����� �� ������������
		int assortmentKod=0;
		if(assortmentRow!=null){
			assortmentKod=this.getAssortmentKod(connector.getConnection(), assortmentRow.getKod());
		}
		if(assortmentKod>0){
			// �������� �������� � ����
			returnValue=this.updateAssortmentPriceBuy(connector, assortmentKod, assortmentRow, controlMinPercent, course);
		}else{
			// �������� ��������� ����� �������������� �������
			returnValue=new CommodityAssortmentQuestion(assortmentRow.getKod(), assortmentRow.getName(), assortmentRow.getWarranty(), assortmentRow.getPriceUSD()*course);
		}
		return returnValue;
	}
	
	
	/**
	 * ���������� � ����� ������ 
	 * @param connection - ���������� � ����� ������ 
	 * @param assortmentKod - ��� ������������ ��� ���������� 
	 * @param assortmentRow - ������, ������� ����� ������� ������������
	 * @param controlMinPercent - ����������� �������� ������� ( �� ������� ��������� ���� ������� ������ ���� ������� )
	 * @param couse - ���� ������ � ������ ������ 
	 * @return
	 * <ul>
	 * 	<li><b>null</b> ������ ������� ���������</li>
	 * 	<li><b>{@link CommodityAssortmentQuestion}</b> - ������, ������� ������� ��������� </li>
	 * </ul>
	 */
	private CommodityAssortmentQuestion updateAssortmentPriceBuy(ConnectWrap connector, int assortmentKod, AssortmentRow assortmentRow, float controlMinPercent, float course) {
		Connection connection=connector.getConnection();
		CommodityAssortmentQuestion returnValue=null;
		try{
			// �������� ����������� � ��������� ��� �� ������������ ������ ( ���� ������������ ��������� ������� ) - �������� ����������� �� ������� ���������� 
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
					// ���� ���������� - ���������� ���������� ������ 
						// ���������, ����� �� �������� ������ ���� �������
					if (((priceSell/assortmentRow.getPriceUSD())-1)<(controlMinPercent/100)){
						// ���������� �������� ������ ���� ������� 
						this.createNewPriceBuyForAssortment(connector, assortmentKod, assortmentRow.getPriceUSD());
						return null;
					}else{
						// ���������� ������ ������ ������� 
						returnValue=new CommodityAssortmentQuestion(assortmentRow.getKod(), assortmentRow.getName(), assortmentRow.getWarranty(), assortmentRow.getPriceUSD()*course);
						returnValue.setClassKod(rs.getInt("class_kod"));
					}
				}else{
					// ������ �� ������� ���������� 
					returnValue=null;
				}
			}else{
				throw new Exception("����������� �� ������ - ����������� ������ ( ���������� ��� �� ������ ):"+assortmentRow);
			}
			// ��������� ����������� ��  
		}catch(Exception ex){
			System.err.println("CommodityAssortmentManager#updateAssortment Exception:"+ex.getMessage());
			returnValue=new CommodityAssortmentQuestion(assortmentRow.getKod(), assortmentRow.getName(), assortmentRow.getWarranty(), assortmentRow.getPriceUSD()*course);
		}
		return returnValue;
	}

	/** ��������� �� ������������� ���-�� ������  */
	private static float roundTo(int sign, float value){
		int tempValue=1;
		for(int counter=0;counter<sign;counter++){
			tempValue*=10;
		}
		value=Math.round(value*tempValue);
		return (float)((float)value)/(float)tempValue;
	}
	
	/** �� ���������� ������������ ������� ����� ������ � ������� Price � ����������� ����� �������  */
	private void createNewPriceBuyForAssortment(ConnectWrap connector, int assortmentKod, float priceBuy) throws SQLException, NullPointerException{
		/** �����������, ������� �����������  */
		Assortment assortment=(Assortment)connector.getSession().get(Assortment.class, assortmentKod);
		Price priceOld=(Price)connector.getSession().get(Price.class, assortment.getPriceKod());
		Price priceNew=priceOld.clone();
		Session session=connector.getSession();
		try{
			session.beginTransaction();
			// ��������� ����� ���� � ���� 
			priceNew.setKod(0);
			priceNew.setNextKod(null);
			priceNew.setValid(1);
			priceNew.setDateWrite(new Date());
			priceNew.setPriceBuying(priceBuy);
			session.save(priceNew);
			// �������� ������ ���� - ������� �� �������������� 
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
		// �������� ������ ������ �� ������� PRICE
		// ������� ����� ������ � ������� PRICE
		
	}

	/**
	 * �������� ASSORTMENT.KOD �� ��������� �������, ������������ �� Excel 
	 * @param connection - ���������� � ����� ������ 
	 * @param row - ������, ����������� �� Excel 
	 * @return 
	 * <ul>
	 * 	<li><b>0</b> - ������ ������ � ���� �� ��������� </li>
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
				// ������� �����
				workbook=Workbook.getWorkbook(file);
				Sheet sheet=workbook.getSheet(0);
				float course=this.getCourse(connector);
				if(course==0)throw new Exception("CommodityAssortmentManager#preapreData course was not got ");
				AssortmentRowBuilder builder=new AssortmentRowBuilder(course, fieldNames, fieldPosition);
				int rowCount=sheet.getRows();
				// ����������� �� ���� ������� � ���������������� �� ������, ������� ������� �������, � ����� ������� UPDATE
				/** ��������� ������, ������� �� ���� ���������������� ��� ����������� */
				int errorRowCount=0;
				ArrayList<CommodityAssortmentQuestion> returnValue=new ArrayList<CommodityAssortmentQuestion>();
				for(int rowCounter=0;rowCounter<rowCount;rowCounter++){
					AssortmentRow assortmentRow=builder.getAssortmentRow(sheet.getRow(rowCounter));
					if(assortmentRow!=null){
						// ��������� ������ ������ � ���� ������
						CommodityAssortmentQuestion assortmentQuestion=saveAssortmentRow(connector, assortmentRow, margePercent, course);
						if(assortmentQuestion!=null){
							// ��������� �� ������� � ������ �� ������������
							int responseIndex=(-1);
							if(assortmentQuestionResponseList!=null){
								responseIndex=assortmentQuestionResponseList.indexOf(assortmentQuestion);
							}
							if(responseIndex>=0){
								// ����� �� ������ (CLASS, PRICE_SELL) ��� ������� - ��������� 
								if(this.insertOrUpdateAssortment(connector, assortmentQuestionResponseList.get(responseIndex), course)==false){
									returnValue.add(assortmentQuestion);
								}
							}else{
								// ����� �� ������ (CLASS, PRICE_SELL) ��� �� �������
								returnValue.add(assortmentQuestion);
							}
						}
					}else{
						// �� ������� �������� AssortmentRow �� ����� Excel
						errorRowCount++;
					}
				}
				// ��������� �� ��������� �������� ��������� ������ 
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

	/** �������� ����� ����������� � ������� Assortment */
	private boolean insertOrUpdateAssortment(ConnectWrap connector, CommodityAssortmentQuestion commodityAssortmentQuestion, float course) throws Exception {
		boolean returnValue=false;
		int assortmentKod=this.getAssortmentKod(connector.getConnection(), commodityAssortmentQuestion.getKod());
		if(assortmentKod>0){
			// �������� ����������� 
			Assortment assortment=(Assortment)connector.getSession().get(Assortment.class, assortmentKod);
			Price priceOld=(Price)connector.getSession().get(Price.class, assortment.getPriceKod());
			Price priceNew=priceOld.clone();
			Session session=connector.getSession();
			try{
				session.beginTransaction();
				// ��������� ����� ���� � ���� 
				priceNew.setKod(0);
				priceNew.setNextKod(null);
				priceNew.setValid(1);
				priceNew.setDateWrite(new Date());
				priceNew.setPriceBuying(commodityAssortmentQuestion.getPriceBuy()/course);
				priceNew.setPrice(commodityAssortmentQuestion.getPriceSell()/course);
				session.save(priceNew);
				// �������� ������ ���� - ������� �� �������������� 
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
			// ������� ����� �������������� �����
			Session session=connector.getSession();
			try{
				if((commodityAssortmentQuestion.getPriceSell()==0)||(commodityAssortmentQuestion.getClassKod()==0)){
					return false;
				}
				// ������� PRICE
				Price price=new Price();
				price.setDateWrite(new Date());
				price.setNextKod(null);
				price.setPriceBuying(commodityAssortmentQuestion.getPriceBuy()/course);
				price.setPrice(commodityAssortmentQuestion.getPriceSell()/course);
				price.setValid(1);
				
				// ������� ASSORTMENT
				Assortment assortment=new Assortment();
				assortment.setBarCode(commodityAssortmentQuestion.getBarCodeProducer());
				assortment.setBarCodeCompany(Integer.toString(commodityAssortmentQuestion.getKod()));
				assortment.setClassKod(commodityAssortmentQuestion.getClassKod());
				assortment.setDateWrite(new Date());
				assortment.setKodPhoto(null);
				assortment.setName(commodityAssortmentQuestion.getName());
				assortment.setNote(null);
				
				assortment.setWarrantyMonth(commodityAssortmentQuestion.getWarranty());
				// ��������� PRICE
				session.beginTransaction();
				session.save(price);
				// ��������� ASSORTMENT
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

/** ������-����������� {@link AssortmentRow} */
class AssortmentRowBuilder{
	private float course;
	private int columnKod;
	private int columnName;
	private int columnWarranty;
	private int columnPrice;
	/** ������-����������� {@link AssortmentRow}
	 * @param course - ���� USD  
	 * @param fieldNames - ������������ ����� {@link ICommodityAssortmentManager#saveData}
	 * @param fieldPosition - ������� ���� ����� � ������  
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
	
	/** ���� �� ���������� {@link AssortmentRow} ���� �� ���������� null �� ��������� row*/
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




/** ������, ������� �������������� ���� ������ ��� ������� � ���� ������ � �������� ���������� ������������  */
class AssortmentRow{
	private int kod;
	private String name;
	private int warranty;
	private float priceUSD;
	
	/** ������, ������� �������������� ���� ������ ��� ������� � ���� ������ � �������� ���������� ������������  
	 * @param kod - ��� �����  
	 * @param name - ������������ 
	 * @param warranty - ��������
	 * @param priceUSD - ���� � USD 
	 */
	public AssortmentRow(int kod, String name, int warranty, float priceUSD){
		this.kod=kod;
		this.name=name;
		this.warranty=warranty;
		this.priceUSD=priceUSD;
	}

	/** �������� ��� ������ ������� (��� ����� ) */
	public int getKod() {
		return kod;
	}

	/** �������� ������������ ������� ������������ */
	public String getName() {
		return name;
	}

	/** �������� � �������  */
	public int getWarranty() {
		return warranty;
	}

	/** ���� � ��������  */
	public float getPriceUSD() {
		return priceUSD;
	}
	
	
}
