package reporter;

import reporter.query.IQuery;

/** ���� ������� �� �������, ������� ����� ���������� ����������� ������� */
public class ReportQueueElement {
	private String uniqueId;
	private PatternReportList pattern;
	private IQuery query;
	private String title;
	
	/** ���� ������� �� �������, ������� ����� ���������� ����������� ������� 
	 * @param uniqueId - ���������� ������������� ������ ��� ����������� � ���������
	 * @param pattern - ������, �� �������� ����� ������ ������ ����� ({@link PatternReportList})
	 * @param title - ��������� ��� ������ 
	 * @param query - ��������� �������
	 */
	public ReportQueueElement(String uniqueId, PatternReportList pattern, String title, IQuery query){
		this.uniqueId=uniqueId;
		this.pattern=pattern;
		this.title=title;
		this.query=query;
	}
	
	/** �������� ���������� ������������� */
	public String getUniqueId(){
		return this.uniqueId;
	}
	
	/** �������� ������ ������ ������ ������ ����� ����������� ��������� */
	public PatternReportList getPattern(){
		return this.pattern;
	}
	
	/** �������� ������, ������� ������� SQL �������� � ���� ������  */
	public IQuery getQuery(){
		return this.query;
	}
	
	/** �������� ��������� ��� ������ */
	public String getTitle(){
		return this.title;
	}
}
