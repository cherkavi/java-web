package algorithms.out;

import java.util.Date;

/** вывод/сохранение данных алгоритма Rithm */
public interface IRithmOutput {
	/** вывод/сохранение данных алгоритма Rithm  
	 * @param date - дата
	 * @param angle1 - угол в радианах 
	 * @param angle2 - угол в радианах
	 */
	public void execute(Date date, double angle1, double angle2);
}
