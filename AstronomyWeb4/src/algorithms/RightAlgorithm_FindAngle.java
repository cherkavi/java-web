package algorithms;

import java.util.Calendar;
import java.util.Date;

import algorithms.utils.PlanetName;
import algorithms.utils.UtilsAngle;
import at.kugel.zodiac.TextHoroscop;
import at.kugel.zodiac.house.HouseKoch;
import at.kugel.zodiac.planet.PlanetAA0;

/** ����� ����� ������ �� ��������� ����  */
public class RightAlgorithm_FindAngle {
	
	/** ����� ����� ������ �� ��������� ����  
	 * @param longitude - �������
	 * @param latitude - ������
	 * @param gmt - GMT
	 * @param calendar - ����
	 * @param planet - ������� �������
	 * @return ���� � �������� 
	 */
	public double getAngle(double longitude, 
					    double latitude,
			   			int gmt,
			   			Calendar calendar,
			   			PlanetName planet){
		final TextHoroscop horoscop = new TextHoroscop();
		horoscop.setPlanet(new PlanetAA0());
		horoscop.setHouse(new HouseKoch());
		horoscop.setLocationDegree(longitude, latitude);
		
		horoscop.setTime(calendar.get(Calendar.DAY_OF_MONTH), 
				 calendar.get(Calendar.MONTH) + 1, 
				 calendar.get(Calendar.YEAR),
				 calendar.get(Calendar.HOUR_OF_DAY), 
				 calendar.get(Calendar.MINUTE), 
				 calendar.get(Calendar.SECOND), 
				 gmt);
		horoscop.calcValues();
		return UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet.getKod()]);
	}
	
	/** ����� ����� ������ �� ��������� ����  
	 * @param longitude - �������
	 * @param latitude - ������
	 * @param gmt - GMT
	 * @param calendar - ����
	 * @param planet - ������� �������
	 * @return ���� � �������� 
	 */
	public double getAngle(double longitude, 
		    double latitude,
   			int gmt,
   			Date date,
   			PlanetName planet){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return this.getAngle(longitude, latitude, gmt, calendar, planet);
	}
}
