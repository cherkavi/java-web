package algorithms;

import java.util.Calendar;

import algorithms.utils.CalendarStep;
import algorithms.utils.PlanetName;
import algorithms.utils.UtilsAngle;
import algorithms.utils.Weight;
import at.kugel.zodiac.TextHoroscop;
import at.kugel.zodiac.house.HouseKoch;
import at.kugel.zodiac.planet.PlanetAA0;

import algorithms.out.IRithmOutput;
import algorithms.out.time_angle_angle.*;

/** алгоритм "Поиска денег" производит поиск максимального Аспекта в указанной точке (кроме Луны), 
 * затем по найденной паре планет начинает по алгоритму шаг строить на указанный период график   */
public class RightAlgorithm_ISK {
	
	public static void main(String[] args){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,13);
		calendar.set(Calendar.MONTH, 6-1);
		calendar.set(Calendar.YEAR, 2007);
		calendar.set(Calendar.HOUR_OF_DAY, 22);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);

		Calendar startPoint=Calendar.getInstance();
		startPoint.set(Calendar.DAY_OF_MONTH,14);
		startPoint.set(Calendar.MONTH, 6-1);
		startPoint.set(Calendar.YEAR, 2010);
		startPoint.set(Calendar.HOUR_OF_DAY, 0);
		startPoint.set(Calendar.MINUTE, 00);
		startPoint.set(Calendar.SECOND, 00);
		startPoint.set(Calendar.MILLISECOND, 00);
		
		Calendar endPoint=Calendar.getInstance();
		endPoint.set(Calendar.DAY_OF_MONTH,14);
		endPoint.set(Calendar.MONTH, 12-1);
		endPoint.set(Calendar.YEAR, 2010);
		endPoint.set(Calendar.HOUR_OF_DAY, 0);
		endPoint.set(Calendar.MINUTE, 00);
		endPoint.set(Calendar.SECOND, 00);
		endPoint.set(Calendar.MILLISECOND, 00);
		
		new RightAlgorithm_ISK(30+31/60f, 
							   50+26/60f, 
							   calendar, 
							   2,
								  
							   30+31/60f,
							   50+26/60f,
							   startPoint, 
							   endPoint, 
							   new CalendarStep(0,0,3,0,0,0),
							   2,
							   new ConsoleOutput());
		System.out.println("-end-");
	}
	
	/** алгоритм "Поиска денег" производит поиск максимального Аспекта в указанной точке (кроме Луны), затем по найденной паре планет начинает по алгоритму шаг строить на указанный период график
	 * @param longitude - долгота даты "успеха" 
	 * @param latitude - широта даты "успеха"
	 * @param calendar - временная точка даты "успеха", в которой будет производиться поиск
	 * @param gmtCalendar - GMT даты "успеха"
	 * 
	 * @param longitudePoint долгота в настоящем времени 
	 * @param latitudePoint широта в настоящем времени
	 * @param startPoint - дата начала  
	 * @param endPoint - дата окончания 
	 * @param gmt - GMT в настоящем времени   
	 * @param output - объект-обработчик выходных данных 
	 * */
	public RightAlgorithm_ISK(double longitude, 
						  	  double latitude, 
						  	  Calendar calendar,
						  	  int gmtCalendar,
						  		 
						  	  double longitudePoint,
						  	  double latitudePoint,
						  	  Calendar startPoint, 
						  	  Calendar endPoint,
						  	  CalendarStep step, 
						  	  int gmt,
						  	  IRithmOutput output){
		// получить максимальный аспект по указанной дате 
		PlanetName[] planetInAspect=findAspectWithStep(longitude, 
													   latitude, 
													   calendar, 
													   gmtCalendar, 
													   1);
		new RightAlgorithm_RISHA(longitudePoint, 
								 latitudePoint, 
								 startPoint.getTime(), 
								 endPoint.getTime(), 
								 step,
								 planetInAspect[0], 
								 planetInAspect[1],
								 gmt, 
								 output
								 );
	}
	
	/** произвести поиск планет, которые составляют максимальный КПД в указанной дате, 
	 * двигаться на указанное смещение, если не найден ни один КПД  
	 * @param longitude - долгота
	 * @param latitude - широта
	 * @param calendar - календарь 
	 * @param gmt - GMT
	 * @param hour - часы 
	 * @return
	 */
	private PlanetName[] findAspectWithStep(double longitude, double latitude, Calendar calendar, int gmt, int hour){
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
		PlanetName[] returnValue=null;
		while( (returnValue=getMaxAspectWithoutMoon(horoscop))==null ){
			calendar.add(Calendar.HOUR_OF_DAY, hour);
			horoscop.setTime(calendar.get(Calendar.DAY_OF_MONTH), 
					 calendar.get(Calendar.MONTH) + 1, 
					 calendar.get(Calendar.YEAR),
					 calendar.get(Calendar.HOUR_OF_DAY), 
					 calendar.get(Calendar.MINUTE), 
					 calendar.get(Calendar.SECOND), 
					 gmt);
			horoscop.calcValues();
		}
		return returnValue;
	}
	
	/** получить максимальный аспект не включая Луну или вернуть null */
	private PlanetName[] getMaxAspectWithoutMoon(TextHoroscop horoscop){
		PlanetName[] returnValue=null;
		PlanetName moon=PlanetName.Moon;
		float weightBest=0;
		float weightCurrent=0;
		int[] calculation=null;
		for(int x=0;x<10;x++){
			if(x==moon.getKod())continue;
			for(int y=0;y<10;y++){
				if(x==y)break;
				if(y==moon.getKod())continue;
				// System.out.println("FindValue: "+PlanetName.getPlanet(x)+"   "+PlanetName.getPlanet(y));
				calculation=Weight.getAngleWeight(UtilsAngle.getAngleBetweenAngles(UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[x]), 
	 					 											   			   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[y])
	 					 											   			   )
	 					 			 			  );
				if(calculation!=null){
					weightCurrent=Weight.array[calculation[0]][calculation[1]];
					if(weightCurrent>weightBest){
						weightBest=weightCurrent;
						returnValue=new PlanetName[]{PlanetName.getPlanet(x), 
													 PlanetName.getPlanet(y)
													 };
						// System.out.println("ReturnValue: "+PlanetName.getPlanet(x)+"   "+PlanetName.getPlanet(y)+"  Weight:"+weightBest);
					}else{
						// finded weight not biggest
					}
				}else{
					// calculation is null
				}
			}
		}
		return returnValue;
	}
}
