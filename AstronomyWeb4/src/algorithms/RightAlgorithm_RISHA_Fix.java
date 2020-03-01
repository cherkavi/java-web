package algorithms;

import java.util.Calendar;
import java.util.Date;

import algorithms.out.time_angle_angle.ConsoleOutput;
import algorithms.utils.CalendarStep;
import algorithms.utils.PlanetName;
import algorithms.utils.SinglePlanetValue;
import algorithms.utils.UtilsAngle;
import algorithms.utils.Weight;
import at.kugel.zodiac.TextHoroscop;
import at.kugel.zodiac.house.HouseKoch;
import at.kugel.zodiac.planet.PlanetAA0;

/** алгоритм РИША - c фиксированной планетой*/
public class RightAlgorithm_RISHA_Fix {
	
	public static void main(String[] args){
		Calendar calendarBegin=Calendar.getInstance();
		calendarBegin.set(Calendar.YEAR, 1972);
		calendarBegin.set(Calendar.MONTH, 6-1);
		calendarBegin.set(Calendar.DAY_OF_MONTH,06);
		calendarBegin.set(Calendar.HOUR_OF_DAY, 12);
		calendarBegin.set(Calendar.MINUTE, 30);
		calendarBegin.set(Calendar.SECOND, 00);
		calendarBegin.set(Calendar.MILLISECOND, 00);

		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2010);
		calendar.set(Calendar.MONTH, 1-1);
		calendar.set(Calendar.DAY_OF_MONTH,01);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		Date dateBegin=calendar.getTime();
		
		calendar.set(Calendar.YEAR, 2011);
		Date dateEnd=calendar.getTime();
		
		SinglePlanetValue singlePlanet=new SinglePlanetValue(PlanetName.Sun);
		double longitude=(34 + 6.0 / 60f);
		double latitude=(+44 + 56.0 / 60f);
		new RightAlgorithm_RISHA_Fix(longitude, 
								 latitude,
								 dateBegin, 
								 dateEnd,  
								 new CalendarStep(0,0,7,0,0,0), 
								 PlanetName.Sun,
								 singlePlanet.getAngle(longitude, latitude, calendarBegin, 3),
								 PlanetName.Mars,
								 3,
								 new ConsoleOutput());
	}
	
	/** алгоритм РИША - c фиксированной планетой
	 * @param longitude - долгота 
	 * @param latitude - широта
	 * @param dateBegin - дата начала 
	 * @param dateEnd - дата окончания 
	 * @param step - шаг 
	 * @param planet1 - первая планета (фиксированная )
	 * @param planet1Angle - <ul> <li><b>null</b> - назначить стартовый угол начало диапазона сканирования </li> <li><b>value</b> - фиксированный угол</li> </ul> 
	 * @param planet2 - вторая планета ( движущаяся )
	 * @param gmt - GMT
	 * @param output - вывод 
	 */
	public RightAlgorithm_RISHA_Fix(double longitude, 
								double latitude,
								Date dateBegin, 
								Date dateEnd, 
								CalendarStep step,
								PlanetName planet1,
								Double planet1Angle, // фиксированный угол для планеты - не от стартовой точки 
								PlanetName planet2,
								int gmt, 
								ConsoleOutput output) {
		// get a horoscop instance
		final TextHoroscop horoscop = new TextHoroscop();
		// set your desired planet position calculation algorithm
		horoscop.setPlanet(new PlanetAA0());
		// set your desired house system calculation algorithm
		// may be anything from the at.kugel.zodiac.house package.
		horoscop.setHouse(new HouseKoch());
		// set your user data location value
		horoscop.setLocationDegree(longitude, latitude);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateBegin);
		
		// Double lastAngleBetween=null;
		double currentAngleBetween=0;
		Double firstAngle=planet1Angle;
		int[] value=null;
		// рабочий цикл 
		while (calendar.getTime().before(dateEnd)) {
			horoscop.setTime(calendar.get(Calendar.DAY_OF_MONTH), 
							 calendar.get(Calendar.MONTH) + 1, 
							 calendar.get(Calendar.YEAR),
							 calendar.get(Calendar.HOUR_OF_DAY), 
							 calendar.get(Calendar.MINUTE), 
							 calendar.get(Calendar.SECOND), 
							 gmt);
			// вычислить очередное значение 
			horoscop.calcValues();
			if(firstAngle==null){
				UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet1.getKod()]);
			}
			currentAngleBetween=UtilsAngle.getAngleBetweenAngles(firstAngle, 
											 					 UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]));
			
			value=Weight.getAngleWeight(currentAngleBetween);
			if(value!=null){
				// аспект найден
				output.execute(calendar.getTime(),
						   firstAngle, 
						   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]),
						   Weight.array[value[0]][0],
						   Weight.array[value[0]][value[1]],
						   Weight.risha[value[0]]
						   );
			}else{
				// аспект не найден 
				output.execute(calendar.getTime(),
						   firstAngle, 
						   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]));
			}

			// lastAngleBetween=currentAngleBetween;
			step.addToCalendar(calendar);
		}
	}
}
