package algorithms.utils;

import java.util.Calendar;

import at.kugel.zodiac.TextHoroscop;
import at.kugel.zodiac.house.HouseKoch;
import at.kugel.zodiac.planet.PlanetAA0;

/** получение углов для указанной планеты */
public class SinglePlanetValue {
	private PlanetName planet=null;
	
	/** получение углов для указанной планеты */
	public SinglePlanetValue(PlanetName planet){
		this.planet=planet;
	}
	
	/**
	 * Получение  углов
	 * @param longitude - долгота
	 * @param latitude - широта
	 * @param calendar - календарь
	 * @param gmt - GMT
	 * @return 
	 */
	public double getAngle(double longitude,double latitude,Calendar calendar, int gmt){
		TextHoroscop horoscop = new TextHoroscop();
		horoscop.setPlanet(new PlanetAA0());
		// set your desired house system calculation algorithm
		// may be anything from the at.kugel.zodiac.house package.
		horoscop.setHouse(new HouseKoch());
		// set your user data location value
		horoscop.setLocationDegree(longitude, latitude);
		horoscop.setTime(calendar.get(Calendar.DAY_OF_MONTH), 
				 calendar.get(Calendar.MONTH) + 1, 
				 calendar.get(Calendar.YEAR),
				 calendar.get(Calendar.HOUR_OF_DAY), 
				 calendar.get(Calendar.MINUTE), 
				 calendar.get(Calendar.SECOND), 
				 gmt);
		horoscop.calcValues();
		return UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[this.planet.getKod()]);
	}
}
