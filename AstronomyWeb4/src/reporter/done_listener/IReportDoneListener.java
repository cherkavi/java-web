package reporter.done_listener;

/** ���������, �������� ���������� ���������� � ������ ���������� ������ */
public interface IReportDoneListener {
	/**  ����� ������� �������� 
	 * @param reportId - ���������� ������������� ������ 
	 * @param fullPathToFile - ������ ���� � ��������������� ����� 
	 */
	public void reportDone(String reportId, String fullPathToFile);
	
	/**  ����� �� ��������  
	 * @param reportId - ���������� ������������� ������ 
	 * @param fullPathToFile - (nullable) ������ ���� � �����, ������� ����� ���������� 
	 */
	public void reportError(String reportId, String fullPathToEmptyFile);
}
