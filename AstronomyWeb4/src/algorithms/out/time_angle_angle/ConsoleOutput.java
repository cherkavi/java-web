package algorithms.out.time_angle_angle;

import java.text.SimpleDateFormat;
import java.util.Date;

import algorithms.out.IPartnerOutput;
import algorithms.out.IRithmOutput;
import algorithms.utils.UtilsAngle;
import algorithms.utils.Weight;

/** вывод на консоль значений  */
public class ConsoleOutput implements IRithmOutput, IPartnerOutput{
	
	/** вывод на консоль значений  
	 * @param date - дата
	 * @param angle1 - градус первой планеты 
	 * @param angle2 - градус второй планеты 
	 */
	@Override
	public void execute(Date date, 
						double angle1, 
						double angle2){
		int[] aspectBetween=Weight.getAspectBetween(angle1, angle2);
		if(aspectBetween!=null){
			System.out.println(sdf.format(date)+";   "+formatValues(getDouble(angle1))+" ;; "+formatValues(getDouble(angle2))+" ; ; "+UtilsAngle.getAngleBetweenAngles(angle1, angle2)+" ; "+Weight.array[aspectBetween[0]][aspectBetween[1]]+" ; "+ Weight.aspectWeight[aspectBetween[0]]);
		}else{
			System.out.println(sdf.format(date)+";   "+formatValues(getDouble(angle1))+" ;; "+formatValues(getDouble(angle2))+" ; ; "+UtilsAngle.getAngleBetweenAngles(angle1, angle2)+"  ");
		}
	}
	
	/** вывод на консоль значений  
	 * @param date - дата
	 * @param angle1 - градус первой планеты 
	 * @param angle2 - градус второй планеты
	 * @param aspect Angle 
	 * @param kpd - КПД аспекта   
	 * @param risha - значение РИША (ритма-шага)
	 */
	public void execute(Date date, double angle1, double angle2, double aspectAngle, double kpd, int risha){
		System.out.println(sdf.format(date)+";   "+formatValues(getDouble(angle1))+" ;; "+formatValues(getDouble(angle2))+" ; ; "+UtilsAngle.getAngleBetweenAngles(angle1, angle2)+";    Aspect:;"+aspectAngle+";    КПД: ;"+kpd+";   РИША:;"+risha);
	}

	/** вывод на консоль значений  
	 * @param angle1 - градус  
	 * @param aspect Angle 
	 * @param kpd - КПД аспекта   
	 * @param risha - значение РИША (ритма-шага)
	 */
	@Override
	public void execute(double angle1, double aspectAngle, double kpd, int risha){
		System.out.println(formatValues(getDouble(angle1))+";    Aspect:;"+aspectAngle+";    КПД: ;"+kpd+";   РИША:;"+risha);
	}

	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:SS");
	
	
	private String formatValues(int[] values){
		return values[0]+" ; "+values[1]+" ; "+values[2]+" ; ";
	}
	
	private int[] getDouble(double d){
	      int h,m,s;
	      // final double d = CalcUtil.DFromR(r);
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
	   System.out.println(buf.toString());*/
	}

	/** вывод на печать одного угла */
	@Override
	public void execute(double angle) {
		System.out.println(formatValues(getDouble(angle))+";");
	}
	
}
