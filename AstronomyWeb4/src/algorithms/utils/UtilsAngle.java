package algorithms.utils;

import at.kugel.zodiac.util.CalcUtil;

/** утилиты для углов */
public class UtilsAngle {
	
   /** Hilfswert 180/Pi. */
   private static final double PI180 = 180.0/Math.PI;
   /** Hilfswert 2*Pi. */
   public static final double PI2 = 2.0*Math.PI;
   /** Hilfswert Pi/2. */
   public static final double PIH = Math.PI/2.0;
	
	public static double getAngleFromRadian(double r){
		return r*PI180;
	}
	
	
	/** угол между двумя планетами, углы задаются как градусы */
	public static double getAngleBetweenAngles(double angle1, double angle2){
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
	
	/** угол между двумя планетами, углы задаются как радианы */
	public static double getAngleBetweenRadian(double planet1, double planet2){
		double angle1=CalcUtil.DFromR(planet1);
		double angle2=CalcUtil.DFromR(planet2);
		return getAngleBetweenAngles(angle1, angle2);
	}
	
}
