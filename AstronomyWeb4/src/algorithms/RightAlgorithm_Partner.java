package algorithms;

import algorithms.out.IPartnerOutput;
import algorithms.out.time_angle_angle.ConsoleOutput;
import algorithms.utils.Weight;

/** <b>�������� �������</b> � ���������� ���� ������������ ��������� ��� � �� ������ �������� ����������� ������� ��������  */
public class RightAlgorithm_Partner {

	public static void main(String[] args){
		new RightAlgorithm_Partner(204+50/60f, 
								   8, 
								   new ConsoleOutput());
	}
	
	/** <b>�������� �������</b> � ���������� ���� ������������ ��������� ��� � �� ������ �������� ����������� ������� ��������   
	 * @param startAngle - ��������� ���� 
	 * @param stepCount - ���-�� ���, ������� ����� ��������� ������ ���� 
	 * @param output - �����, ���� ����� �������� 
	 */
	public RightAlgorithm_Partner(double startAngle, 
								   
								  int stepCount, 
								  IPartnerOutput output){
		double step=37+50/60f;
		double currentAngle=startAngle;
		for(int counter=0;counter<stepCount;counter++){
			int[] result=Weight.getAngleWeight(currentAngle);
			if(result!=null){
				output.execute(currentAngle, 
							   Weight.array[result[0]][0], 
							   Weight.array[result[0]][result[1]], 
							   Weight.risha[result[0]]);
			}else{
				output.execute(currentAngle);
			}
			
			currentAngle+=step;
			if(currentAngle>360)currentAngle-=360;
		}
	}
}
