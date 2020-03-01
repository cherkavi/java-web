package database;

/** имя драйверов к базе данных  */
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
	/** полное имя драйвера  */
	private String driverName;
	/** вид ожидаемого URL  */
	private String urlPattern;
	/** default порт  */
	private int defaultPort;
	/** Hibernate диалект */
	private String hibernateDialect;
	
	/**
	 * Драйвер-идентификатор соединения с базой данных 
	 * @param index - индекс 
	 * @param driverName - полное имя драйвера ( класса соединения для DriverManager )
	 * @param hibernateDialect - диалект Hibernate
	 * @param urlPattern - пример шаблона URL для данного драйвера 
	 */
	DatabaseDrivers(int index, String driverName, String hibernateDialect, int defaultPort, String urlPattern){
		this.index=index;
		this.driverName=driverName;
		this.hibernateDialect=hibernateDialect;
		this.defaultPort=defaultPort;
		this.urlPattern=urlPattern;
	}
	
	/** получить полное имя драйвера для загрузки  */
	public String getDriverName(){
		return this.driverName;
	}
	
	/** получить шаблон ( пример ) */
	public String getUrlPattern(){
		return this.urlPattern;
	}
	
	/** получить  URL соединения 
	 * @param host - хост 
	 * @param port - порт 
	 * @param databaseName - имя базы данных 
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
	
	/** получить  URL соединения 
	 * @param host - хост 
	 * @param databaseName - имя базы данных 
	 * */ 
	public String getUrl(String host, String databaseName){
		return getUrl(host, this.getDefaultPort(), databaseName);
	}
	
	/** получить стандартный порт соединения */
	public int getDefaultPort(){
		return this.defaultPort;
	}

	/** получить диалект Hibernate */
	public String getHibernateDialect() {
		return this.hibernateDialect;
	}
}

