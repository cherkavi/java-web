package algorithms.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** класс, который служит для увеличения даты на указанный период  */
public class CalendarStep {
	private int DAY_OF_MONTH;
	private int MONTH;
	private int YEAR;
	private int HOUR_OF_DAY;
	private int MINUTE;
	private int SECOND;
	
	/** класс, который служит для увеличения даты на указанный период  */
	public CalendarStep(){
	}
	
	/** класс, который служит для увеличения даты на указанный период  
	 * @param year - годы
	 * @param month - месяцы 
	 * @param dayOfMonth - дни месяца 
	 * @param hourOfDay - часы 
	 * @param minute - минуты 
	 * @param second - секунды 
	 */
	public CalendarStep(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second){
		this.YEAR=year;
		this.MONTH=month;
		this.DAY_OF_MONTH=dayOfMonth;
		this.HOUR_OF_DAY=hourOfDay;
		this.MINUTE=minute;
		this.SECOND=second;
	}
	
	/** добавить указанный временной период в аргументе  */
	public void addToCalendar(Calendar calendar){
		calendar.add(Calendar.YEAR, this.YEAR);
		calendar.add(Calendar.MONTH, this.MONTH);
		calendar.add(Calendar.DAY_OF_MONTH, this.DAY_OF_MONTH);
		calendar.add(Calendar.HOUR_OF_DAY, this.HOUR_OF_DAY);
		calendar.add(Calendar.MINUTE, this.MINUTE);
		calendar.add(Calendar.SECOND, this.SECOND);
	}
	
	/** добавить в указанную дату половину шага  
	 * @param calendar - дата 
	 * @return дата, которая к которой была добавлена половина шага  
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
	
	/** вычесть из указанной даты половину шага  
	 * @param calendar - дата 
	 * @return дата, которая к которой была добавлена половина шага  
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
	
	/** удалить указанный временной период в аргументе  */
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
