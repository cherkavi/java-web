package algorithms.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** �����, ������� ������ ��� ���������� ���� �� ��������� ������  */
public class CalendarStep {
	private int DAY_OF_MONTH;
	private int MONTH;
	private int YEAR;
	private int HOUR_OF_DAY;
	private int MINUTE;
	private int SECOND;
	
	/** �����, ������� ������ ��� ���������� ���� �� ��������� ������  */
	public CalendarStep(){
	}
	
	/** �����, ������� ������ ��� ���������� ���� �� ��������� ������  
	 * @param year - ����
	 * @param month - ������ 
	 * @param dayOfMonth - ��� ������ 
	 * @param hourOfDay - ���� 
	 * @param minute - ������ 
	 * @param second - ������� 
	 */
	public CalendarStep(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second){
		this.YEAR=year;
		this.MONTH=month;
		this.DAY_OF_MONTH=dayOfMonth;
		this.HOUR_OF_DAY=hourOfDay;
		this.MINUTE=minute;
		this.SECOND=second;
	}
	
	/** �������� ��������� ��������� ������ � ���������  */
	public void addToCalendar(Calendar calendar){
		calendar.add(Calendar.YEAR, this.YEAR);
		calendar.add(Calendar.MONTH, this.MONTH);
		calendar.add(Calendar.DAY_OF_MONTH, this.DAY_OF_MONTH);
		calendar.add(Calendar.HOUR_OF_DAY, this.HOUR_OF_DAY);
		calendar.add(Calendar.MINUTE, this.MINUTE);
		calendar.add(Calendar.SECOND, this.SECOND);
	}
	
	/** �������� � ��������� ���� �������� ����  
	 * @param calendar - ���� 
	 * @return ����, ������� � ������� ���� ��������� �������� ����  
	 * */
	public Calendar getHalfStepAdd(Calendar calendar){
		Date dateBegin=calendar.getTime();
		
		Calendar calendarEnd=Calendar.getInstance();
		this.addToCalendar(calendarEnd);
		
		Date dateEnd=calendarEnd.getTime();
		
		long step=dateEnd.getTime()-dateBegin.getTime();
		calendarEnd.setTimeInMillis( dateBegin.getTime()+step/2  );
		return calendarEnd;
	}
	
	/** ������� �� ��������� ���� �������� ����  
	 * @param calendar - ���� 
	 * @return ����, ������� � ������� ���� ��������� �������� ����  
	 * */
	public Calendar getHalfStepSub(Calendar calendar){
		Date dateBegin=calendar.getTime();
		
		Calendar calendarEnd=Calendar.getInstance();
		this.addToCalendar(calendarEnd);
		
		Date dateEnd=calendarEnd.getTime();
		
		long step=dateEnd.getTime()-dateBegin.getTime();
		calendarEnd.setTimeInMillis( dateBegin.getTime()-step/2  );
		return calendarEnd;
	}
	
	/** ������� ��������� ��������� ������ � ���������  */
	public void subFromCalendar(Calendar calendar){
		calendar.add(Calendar.YEAR, -this.YEAR);
		calendar.add(Calendar.MONTH, -this.MONTH);
		calendar.add(Calendar.DAY_OF_MONTH, -this.DAY_OF_MONTH);
		calendar.add(Calendar.HOUR_OF_DAY, -this.HOUR_OF_DAY);
		calendar.add(Calendar.MINUTE, -this.MINUTE);
		calendar.add(Calendar.SECOND, -this.SECOND);
	}
	
	
	public static void main(String[] args){
		System.out.println("CalendarStep Test Begin");
		CalendarStep step=new CalendarStep(0,0,1,0,1,0);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		Calendar calendar=Calendar.getInstance();
		Calendar half=step.getHalfStepAdd(calendar);
		System.out.println(">>> "+sdf.format(calendar.getTime()));
		System.out.println(">>> "+sdf.format(half.getTime()));
		
		System.out.println("CalendarStep Test End");
	}
}
