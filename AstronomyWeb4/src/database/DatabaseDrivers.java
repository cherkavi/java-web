package database;

/** ��� ��������� � ���� ������  */
public enum DatabaseDrivers {
	Firebird(1, 
			 "org.firebirdsql.jdbc.FBDriver", 
			 "org.hibernate.dialect.FirebirdDialect",
			 3050,
			 "jdbc:firebirdsql:[//<HOST>[:<PORT>]/]<DB>" 
			 ), 
	
	MySQL(2, 
		  "org.gjt.mm.mysql.Driver",
		  "org.hibernate.dialect.MySQLDialect",
		  3306,
		  "jdbc:mysql://<HOST>:<PORT>/<DB>" 
		  ),
		  
	Oracle(3, 
		   "oracle.jdbc.driver.OracleDriver",
		   "org.hibernate.dialect.OracleDialect",
		   1521,
		   "jdbc:oracle:thin:@<HOST>:<PORT>:<SID>" 
		   );
	
	private int index=0;
	/** ������ ��� ��������  */
	private String driverName;
	/** ��� ���������� URL  */
	private String urlPattern;
	/** default ����  */
	private int defaultPort;
	/** Hibernate ������� */
	private String hibernateDialect;
	
	/**
	 * �������-������������� ���������� � ����� ������ 
	 * @param index - ������ 
	 * @param driverName - ������ ��� �������� ( ������ ���������� ��� DriverManager )
	 * @param hibernateDialect - ������� Hibernate
	 * @param urlPattern - ������ ������� URL ��� ������� �������� 
	 */
	DatabaseDrivers(int index, String driverName, String hibernateDialect, int defaultPort, String urlPattern){
		this.index=index;
		this.driverName=driverName;
		this.hibernateDialect=hibernateDialect;
		this.defaultPort=defaultPort;
		this.urlPattern=urlPattern;
	}
	
	/** �������� ������ ��� �������� ��� ��������  */
	public String getDriverName(){
		return this.driverName;
	}
	
	/** �������� ������ ( ������ ) */
	public String getUrlPattern(){
		return this.urlPattern;
	}
	
	/** ��������  URL ���������� 
	 * @param host - ���� 
	 * @param port - ���� 
	 * @param databaseName - ��� ���� ������ 
	 * */ 
	public String getUrl(String host, int port, String databaseName){
		switch(index){
			case 1:{
				// Firebird: ""
				return "jdbc:firebirdsql://"+host+":"+port+"/"+databaseName; 
			}
			case 2:{
				// MySQL: "jdbc:mysql://<HOST>:<PORT>/<DB>"
				return "jdbc:mysql://"+host+":"+port+"/"+databaseName;
			}
			case 3:{
				// Oracle: "jdbc:oracle:thin:@<HOST>:<PORT>:<SID>"
				return "jdbc:oracle:thin:@"+host+":"+port+":"+databaseName;
			}
			default: return "";
		}
	}
	
	/** ��������  URL ���������� 
	 * @param host - ���� 
	 * @param databaseName - ��� ���� ������ 
	 * */ 
	public String getUrl(String host, String databaseName){
		return getUrl(host, this.getDefaultPort(), databaseName);
	}
	
	/** �������� ����������� ���� ���������� */
	public int getDefaultPort(){
		return this.defaultPort;
	}

	/** �������� ������� Hibernate */
	public String getHibernateDialect() {
		return this.hibernateDialect;
	}
}

