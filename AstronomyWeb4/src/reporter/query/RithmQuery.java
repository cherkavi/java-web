package reporter.query;

public class RithmQuery implements IQuery{
	private final static long serialVersionUID=1L;
	private int userId;
	
	public RithmQuery(int userId){
		this.userId=userId;
	}

	@Override
	public String getQuery() {
		return "select * from a_calculation_rithm where id_user="+userId;
	}

	@Override
	public String getQuerySize() {
		return "select count(*) from a_calculation_rithm where id_user="+userId;
	}
}
