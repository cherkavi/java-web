package reporter;

import reporter.query.IQuery;

/** один элемент из очереди, который нужно обработать генератором отчетов */
public class ReportQueueElement {
	private String uniqueId;
	private PatternReportList pattern;
	private IQuery query;
	private String title;
	
	/** один элемент из очереди, который нужно обработать генератором отчетов 
	 * @param uniqueId - уникальный идентификатор отчета для возвращения в слушатель
	 * @param pattern - шаблон, по которому будет создан данный отчет ({@link PatternReportList})
	 * @param title - заголовок для отчета 
	 * @param query - генератор запроса
	 */
	public ReportQueueElement(String uniqueId, PatternReportList pattern, String title, IQuery query){
		this.uniqueId=uniqueId;
		this.pattern=pattern;
		this.title=title;
		this.query=query;
	}
	
	/** получить уникальный идентификатор */
	public String getUniqueId(){
		return this.uniqueId;
	}
	
	/** получить шаблон какого именно отчета будет происходить генерация */
	public PatternReportList getPattern(){
		return this.pattern;
	}
	
	/** получить объект, который владеет SQL запросом к базе данных  */
	public IQuery getQuery(){
		return this.query;
	}
	
	/** получить заголовок для отчета */
	public String getTitle(){
		return this.title;
	}
}
