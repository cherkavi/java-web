package reporter.done_listener;

/** слушатель, которому передается управление в случае выполнения отчета */
public interface IReportDoneListener {
	/**  отчет успешно выполнен 
	 * @param reportId - уникальный идентификатор отчета 
	 * @param fullPathToFile - полный путь к сформированному файлу 
	 */
	public void reportDone(String reportId, String fullPathToFile);
	
	/**  отчет не выполнен  
	 * @param reportId - уникальный идентификатор отчета 
	 * @param fullPathToFile - (nullable) полный путь к файлу, который можно отобразить 
	 */
	public void reportError(String reportId, String fullPathToEmptyFile);
}
