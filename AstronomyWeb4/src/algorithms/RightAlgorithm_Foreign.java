package algorithms;

import java.util.ArrayList;
import java.util.Calendar;

import algorithms.utils.DoublePlanet;
import algorithms.utils.PlanetName;
import algorithms.utils.UtilsAngle;
import algorithms.utils.Weight;
import at.kugel.zodiac.TextHoroscop;
import at.kugel.zodiac.house.HouseKoch;
import at.kugel.zodiac.planet.PlanetAA0;

/** просчитывание одинаковых ритмов у двух людей по указанным датам рождения,
 * поиск пар планет которые образуют аспекты у одного и другого человека */
public class RightAlgorithm_Foreign {
	
	public static void main(String[] args){
		Calendar one=Calendar.getInstance();
		one.set(Calendar.DAY_OF_MONTH,01);
		one.set(Calendar.MONTH, 1-1);
		one.set(Calendar.YEAR, 1980);
		one.set(Calendar.HOUR_OF_DAY, 0);
		one.set(Calendar.MINUTE, 00);
		one.set(Calendar.SECOND, 00);
		one.set(Calendar.MILLISECOND, 00);
		
		Calendar two=Calendar.getInstance();
		two.set(Calendar.DAY_OF_MONTH,02);
		two.set(Calendar.MONTH, 2-1);
		two.set(Calendar.YEAR, 1981);
		two.set(Calendar.HOUR_OF_DAY, 0);
		two.set(Calendar.MINUTE, 00);
		two.set(Calendar.SECOND, 00);
		two.set(Calendar.MILLISECOND, 00);

		double longitude=30+31/60f;
		double latitude=50+26/60f;
		int gmt=2;
		new RightAlgorithm_Foreign(longitude, latitude, gmt, one, longitude, latitude, gmt, two);
		
		System.out.println("-end-");
	}

	/** просчитывание одинаковых ритмов у двух людей по указанным датам рождения,
	 * поиск пар планет которые образуют аспекты у одного и другого человека */
	RightAlgorithm_Foreign(double longitudeOne, 
						   double latitudeOne,
						   int gmtOne,
						   Calendar peopleOne,
						   
						   double longitudeTwo,
						   double latitudeTwo,
						   int gmtTwo,
						   Calendar peopleTwo
						   ){
		ArrayList<DoublePlanet> one=this.getAspects(longitudeOne, latitudeOne, gmtOne, peopleOne);
		for(int counter=0;counter<one.size();counter++)System.out.println(one.get(counter));
		
		// System.out.println("======================");
		ArrayList<DoublePlanet> two=this.getAspects(longitudeTwo, latitudeTwo, gmtTwo, peopleTwo);
		for(int counter=0;counter<two.size();counter++)System.out.println(two.get(counter));
		
		// System.out.println("======================");
		ArrayList<DoublePlanet> join=this.join(one,two);
		for(int counter=0;counter<join.size();counter++){
			System.out.println(join.get(counter));
		}
	}
	
	/** получить одинаковые  */
	private ArrayList<DoublePlanet> join(ArrayList<DoublePlanet> one, ArrayList<DoublePlanet> two){
		ArrayList<DoublePlanet> returnValue=new ArrayList<DoublePlanet>();
		if((one!=null)&&(two!=null)){
			for(int oneIndex=0;oneIndex<one.size();oneIndex++){
				for(int twoIndex=0;twoIndex<two.size();twoIndex++){
					if(one.get(oneIndex).equals(two.get(twoIndex))){
						returnValue.add(one.get(oneIndex));
						break; // не может повторятся пара планет 
					}
				}
			}
		}else{
			// one or two is null - return empty Set 
		}
		return returnValue;
	}
	
	/** получить все пары аспектов по указанной дате */
	private ArrayList<DoublePlanet> getAspects(double longitude, 
			   								   double latitude,
			   								   int gmt,
			   								   Calendar calendar){
		ArrayList<DoublePlanet> returnValue=new ArrayList<DoublePlanet>();
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
		
		int[] calculation=null;
		// проверить планеты на предмет выявления аспектов 
		for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				if(x==y)break;
				// System.out.println("FindValue: "+PlanetName.getPlanet(x)+"   "+PlanetName.getPlanet(y));
				calculation=Weight.getAngleWeight(UtilsAngle.getAngleBetweenAngles(UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[x]), 
	 					 											   			   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[y])
	 					 											   			   )
	 					 			 			  );
				if(calculation!=null){
					returnValue.add(new DoublePlanet(PlanetName.getPlanet(x), 
													 PlanetName.getPlanet(y),
													 (float)UtilsAngle.getAngleBetweenAngles(UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[x]), 
	 											   			   UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[y])
	 											   			   ),
													 Weight.array[calculation[0]][calculation[1]]
													 )
									
									);
				}else{
					// calculation is null
				}
			}
		}
		return returnValue;
	}
}
