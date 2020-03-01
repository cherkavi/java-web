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

/** אכדמנטעל ״ְֳ */
public class RightAlgorithm_Shag {
	 static String[] NAME_PLANET = {
	      "Sun","Moon","Mercury","Venus","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto"
	   };

	public static void main(String[] args){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2009);
		calendar.set(Calendar.MONTH, 6-1);
		calendar.set(Calendar.DAY_OF_MONTH,6);
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 57);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		Date dateBegin=calendar.getTime();
		
		calendar.set(Calendar.YEAR, 2011);
		Date dateEnd=calendar.getTime();
		new RightAlgorithm_Shag().executeShag(dateBegin, 
											  dateEnd, 
											  7, 
											  34 + 6.0f / 60f, 
											  +44 - 56.0f / 60f, 
											  3,
											  75.95,
											  PlanetName.Saturn,
											  new ConsoleOutput());
	}
	
	/** אכדמנטעל ״ְֳ */
	public void executeShag(Date dateBegin, 
							Date dateEnd, 
							CalendarStep step, 
							double longitude,
							double latitude,
							int gmt, 
							double planetFirstAngle,
							PlanetName planetSecond,
							IRithmOutput output){
	      // get a horoscop instance
	      TextHoroscop horoscop = new TextHoroscop();
	      // set your desired planet position calculation algorithm
	      PlanetAA0 planet=new PlanetAA0();
	      horoscop.setPlanet(planet);

	      // set your desired house system calculation algorithm
	      // may be anything from the at.kugel.zodiac.house package.
	      horoscop.setHouse(new HouseKoch());

	      // set your user data location value
	      horoscop.setLocationDegree(longitude, latitude);
	      
	      Double mainPlanet=planetFirstAngle;
	      Calendar calendar=Calendar.getInstance();
	      calendar.setTime(dateBegin);
	      while(calendar.getTime().before(dateEnd)){
	    	  horoscop.setTime(calendar.get(Calendar.DAY_OF_MONTH),
	    			  		   calendar.get(Calendar.MONTH)+1,
	    			  		   calendar.get(Calendar.YEAR),
	    			  		   calendar.get(Calendar.HOUR_OF_DAY),
	    			  		   calendar.get(Calendar.MINUTE),
	    			  		   calendar.get(Calendar.SECOND),
	    			  		   gmt);
	    	  horoscop.calcValues();
	    	  // printPlanet (calendar.getTime(), mainPlanet,horoscop.getPlanet().getPlanetsR()[6]); 
	    	  output.execute(calendar.getTime(), 
	    			  		 mainPlanet,
	    			  		 UtilsAngle.getAngleFromRadian(horoscop.getPlanet().getPlanetsR()[planetSecond.getKod()])
	    			  		 );
	    	  step.addToCalendar(calendar);
	      }
	}
	
	/** אכדמנטעל ״ְֳ */
	public void executeShag(Date dateBegin, 
							Date dateEnd, 
							int dayNext, 
							double longitude,
							double latitude,
							int gmt, 
							double planetFirstAngle,
							PlanetName planetSecond,
							IRithmOutput output){
		CalendarStep step=new CalendarStep(0,0,dayNext,0,0,0);
		executeShag(dateBegin, dateEnd, step, longitude, latitude, gmt, planetFirstAngle, planetSecond, output);
	}
	
	
	// private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:SS");
	
	/*private void printPlanet(Date date, double planet1, double planet2){
		System.out.println(sdf.format(date)+";   "+formatValues(getDouble(planet1))+" ;; "+formatValues(getDouble(planet2))+" ; ; "+subAngle(planet1, planet2));
	}
	
	private double subAngle(double planet1, double planet2){
		double angle1=CalcUtil.DFromR(planet1);
		double angle2=CalcUtil.DFromR(planet2);
		double tempAngle=0;
		if(angle1>angle2){
			tempAngle=angle1-angle2;
		}else if(angle1<angle2){
			tempAngle=angle2-angle1;
		}else{
			return 0;
		}
		if(tempAngle>180){
			return (360-tempAngle);
		}else{
			return (tempAngle);
		}
	}
	
	private String formatValues(int[] values){
		return values[0]+" ; "+values[1]+" ; "+values[2]+" ; ";
	}
	
	private int[] getDouble(double r){
	      int h,m,s;
	      final double d = CalcUtil.DFromR(r);
	      h = (int)Math.floor(d); // Y
	      final double t = (d-h)*60;
	      m = (int)Math.floor(t); // Z
	      s = (int)Math.floor((t-m)*60);
	      int[] returnValue=new int[3];
	      returnValue[0]=h;
	      returnValue[1]=m;
	      returnValue[2]=s;
	      return returnValue;
	      /*
	      StringBuffer buf=new StringBuffer();  
	      if (h<10) buf.append('0');
	      buf.append(h);
	      buf.append('°');
	      if (m<10) buf.append('0');
	      buf.append(m);
	      buf.append('\'');
	      if (s<10) buf.append('0');
	      buf.append(s);
	      buf.append('\"');
	   System.out.println(buf.toString());
	}*/
	
}
