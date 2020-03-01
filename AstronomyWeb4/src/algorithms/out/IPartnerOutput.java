package algorithms.out;

public interface IPartnerOutput {
	/**   
	 * @param angle1 - градус  
	 * @param aspect Angle 
	 * @param kpd - КПД аспекта   
	 * @param risha - значение РИША (ритма-шага)
	 */
	public void execute(double angle1, double aspectAngle, double kpd, int risha);
	
	/**   
	 * @param angle1 - градус  
	 */
	public void execute(double angle1);
}