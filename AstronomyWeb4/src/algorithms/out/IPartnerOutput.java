package algorithms.out;

public interface IPartnerOutput {
	/**   
	 * @param angle1 - ������  
	 * @param aspect Angle 
	 * @param kpd - ��� �������   
	 * @param risha - �������� ���� (�����-����)
	 */
	public void execute(double angle1, double aspectAngle, double kpd, int risha);
	
	/**   
	 * @param angle1 - ������  
	 */
	public void execute(double angle1);
}