package reporter;

/** ������ ������ �������� ��� ���������� ������� */
public enum PatternReportList {
	reportRithmHour("AspectHour.jrxml"),
	reportRithmDay("AspectDay.jrxml"),
	reportRithmWeek("AspectWeek.jrxml"),
	reportRithmMonth("AspectMonth.jrxml")
	;
	
	/** ��� ����� ������  */
	private String patternReportFileName;
	
	private PatternReportList(String patternReportFileName){
		this.patternReportFileName=patternReportFileName;
	}
	
	/** ��� �����-������  */
	public String getPatternReportFileName(){
		return this.patternReportFileName;
	}
}
