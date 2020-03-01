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

/** поиск пар аспектов по указанной дате  */
public class RightAlgorithm_FindAspects {
	
	/** наложить следующий фильтр на найденные аспекты:<br>
	 *   
	 * @param list - список найденных аспектов
	 * @return отфильтрованный список
	 * */
	public ArrayList<DoublePlanet> filterAspects(ArrayList<DoublePlanet> list){
		ArrayList<DoublePlanet> returnValue=new ArrayList<DoublePlanet>();
		//  все аспекты по возрастанию КПД и брать первые 10, или, если количество менее 10, все найденные
		for(int counter=0;counter<list.size();counter++){
			// отображается не более 10 ритмов
			if(counter==10)break;
			returnValue.add(list.get(counter));
		}
		return returnValue;
	}
	
	/** получить все пары аспектов по указанной дате */
	public ArrayList<DoublePlanet> getAspects(double longitude, 
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
			if(x==1)continue; // Moon exclude;
			for(int y=0;y<10;y++){
				if(y==1)continue; // Moon exclude;
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
		
		// удалить аспект меркурий-солнце (2,0)
		int tempIndex=returnValue.indexOf(new DoublePlanet(PlanetName.Mercury, PlanetName.Sun));
		if(tempIndex>=0){
			returnValue.remove(tempIndex);
		}
		// удалить аспект венера-солнце (3,0)
		tempIndex=returnValue.indexOf(new DoublePlanet(PlanetName.Venus, PlanetName.Sun));
		if(tempIndex>=0){
			returnValue.remove(tempIndex);
		}
		
		return returnValue;
	}
	
}
