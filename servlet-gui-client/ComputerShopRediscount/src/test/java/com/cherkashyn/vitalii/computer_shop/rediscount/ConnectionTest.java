package com.cherkashyn.vitalii.computer_shop.rediscount;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * Unit test for simple App.
 */
public class ConnectionTest {

	@Test
	public void testConnection() throws SQLException, ClassNotFoundException{
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		Connection connection=java.sql.DriverManager.getConnection("jdbc:firebirdsql://localhost:3050/V:/Computer_shop/Program/Delphi7/Server/DataBase/server_data.GDB?sql_dialect=3", "SYSDBA", "masterkey");
		System.out.println(connection);
		
		DriverManagerDataSource ds=new org.springframework.jdbc.datasource.DriverManagerDataSource();
		ds.setDriverClassName("org.firebirdsql.jdbc.FBDriver");
		ds.setUrl("jdbc:firebirdsql://localhost:3050/V:/Computer_shop/Program/Delphi7/Server/DataBase/server_data.GDB?sql_dialect=3");
		ds.setUsername("sysdba");
		ds.setPassword("masterkey");
		System.out.println(ds.getConnection());
	}
}
