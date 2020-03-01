package algorithms;

import java.util.Calendar;

import java.util.Date;

import algorithms.out.IRithmOutput;
import algorithms.out.time_angle_angle.ConsoleOutput;
import algorithms.utils.CalendarStep;
import algorithms.utils.PlanetName;
import algorithms.utils.UtilsAngle;
import at.kugel.zodiac.TextHoroscop;
import at.kugel.zodiac.house.HouseKoch;
import at.kugel.zodiac.planet.PlanetAA0;

/** алгоритм РИША - РИТМ и ШАГ  */
public class RightAlgorithm_RISHA {
	
	public static void main(String[] args){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2007);
		calendar.set(Calendar.MONTH, 4-1);
		calendar.set(Calendar.DAY_OF_MONTH,30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		Date dateBegin=calendar.getTime();
		
		calendar.set(Calendar.YEAR, 2009);
		Date dateEnd=calendar.getTime();
		
		new RightAlgorithm_RISHA(34 + 6.0 / 60, 
								 +44 + 56.0 / 60,
								 dateBegin, 
								 dateEnd,  
								 new CalendarStep(0,1,0,0,0,0), 
								 PlanetName.Jupiter, 
								 PlanetName.Neptune,
								 3,
								 new ConsoleOutput());
	}
	
	/** алгоритм РИША - РИТМ и ШАГ  */
	public RightAlgorithm_RISHA(double longitude, 
								double latitude,
								Date dateBegin, 
								Date dateEnd, 
								CalendarStep step,
								PlanetName planet1, 
								PlanetName planet2,
								int gmt, 
								IRithmOutput output) {
		// get a horoscop instance
		final TextHoroscop horoscop = new TextHoroscop();
		// set your desired planet position calculation algorithm
		PlanetAA0 planet = new PlanetAA0();
		horoscop.setPlanet(planet);
		// set your desired house system calculation algorithm
		// may be anything from the at.kugel.zodiac.house package.
		horoscop.setHouse(new HouseKoch());
		// set your user data location value
		horoscop.setLocationDegree(longitude, latitude);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateBegin);
		
		// Double lastAngleBetween=null;
		// double currentAngleBetween=0;
		
		// int[] value=null;
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

			/*if(lastAngleBetween==null){
				// первая итерация
				value=Weight.getAspectBetween(currentAngleBetween, currentAngleBetween);
			}else{
				// не первая итерация 
				value=Weight.getAspectBetween(lastAngleBetween, currentAngleBetween);
			}*/
			
			output.execute(calendar.getTime(), UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet1.getKod()]), UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]));
			/*
			currentAngleBetween=UtilsAngle.getAngleBetweenAngles(UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet1.getKod()]), 
											 					 UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]));

			value=Weight.getAngleWeight(currentAngleBetween);
			if(value!=null){
				// аспект найден
				output.execute(calendar.getTime(),
						   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet1.getKod()]), 
						   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]),
						   Weight.array[value[0]][0],
						   Weight.array[value[0]][value[1]],
						   Weight.risha[value[0]]
						   );
			}else{
				// аспект не найден 
				output.execute(calendar.getTime(),
						   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet1.getKod()]), 
						   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planet2.getKod()]));
			}
			*/
			// lastAngleBetween=currentAngleBetween;
			step.addToCalendar(calendar);
		}
	}
}
