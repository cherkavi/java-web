package algorithms.out.list;

import java.util.ArrayList;
import java.util.Calendar;

import algorithms.out.IPartnerOutput;

/** ���������� � ���� ������������� ������ ��� �������� �����������  */
public class ListPartnerOutput implements IPartnerOutput{
	private ArrayList<Double> resultList=new ArrayList<Double>();

	/** ���������� � ���� ������������� ������ ��� �������� �����������  */
	public ListPartnerOutput(){
	}
	
	@Override
	public void execute(double angle1, double aspectAngle, double kpd, int risha) {
		this.resultList.add(angle1);
	}

	@Override
	public void execute(double angle1) {
		this.resultList.add(angle1);
	}

	/** �������� ���������� ���������� */
	public ArrayList<Double> getResult(){
		return this.resultList;
	}

	/** ������������� ���� � ��������� �������� ����   */
	public String convertAngleToDayOfYearString(Double value){
		int dayOfYear=(int) Math.floor(value*365/360);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.DAY_OF_YEAR, dayOfYear);
		
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		int month=calendar.get(Calendar.MONTH);
		// TODO �������� �� �������������� � ���� ������������ ����  
		return day+" "+this.month[month];
	}
	
	private String[] month=new String[]{"������", "�������", "����", "������", "���", "����", "����", "������", "��������", "�������", "������", "�������"}; 
	
	
	/** ������������� ���� � ������ ����� [������, ������, �������]*/
	public int[] getDouble(double d){
	      int h,m,s;
	      // final double d = CalcUtil.DFromR(r);
	      h = (int)Math.floor(d); // Y
	      final double t = (d-h)*60;
	      m = (int)Math.floor(t); // Z
	      s = (int)Math.floor((t-m)*60);
	      int[] returnValue=new int[3];
	      returnValue[0]=h;
	      returnValue[1]=m;
	      returnValue[2]=s;
	      return returnValue;
	      /*
	      StringBuffer buf=new StringBuffer();  
	      if (h<10) buf.append('0');
	      buf.append(h);
	      buf.append('�');
	      if (m<10) buf.append('0');
	      buf.append(m);
	      buf.append('\'');
	      if (s<10) buf.append('0');
	      buf.append(s);
	      buf.append('\"');
	   System.out.println(buf.toString());*/
	}
	
}
