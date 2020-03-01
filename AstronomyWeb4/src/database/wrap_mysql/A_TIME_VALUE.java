package database.wrap_mysql;

import javax.persistence.*;

@Entity
@Table(name="a_time_value")
public class A_TIME_VALUE {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="YEAR_VALUE")
	private int year;
	@Column(name="MONTH_VALUE")
	private int month;
	@Column(name="DAY_VALUE")
	private int day;
	@Column(name="HOUR_VALUE")
	private int hour;
	@Column(name="MINUTE_VALUE")
	private int minute;
	@Column(name="SECOND_VALUE")
	private int second;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	
}
