package com.cherkashyn.vitalii.web.rest;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AbstractSparkApplicationTest {
	private static Server webServer;
	protected static int PORT=9999;
	
	protected static SparkTestUtil spartTestUtil;
	
	@BeforeClass
	public static void init() throws Exception{
	     // sparkTestUtil = new SparkTestUtil(PORT);
	       webServer = new Server();
	       ServerConnector connector = new ServerConnector(webServer);
	       connector.setPort(PORT);
	       webServer.setConnectors(new Connector[] {connector});
	       WebAppContext bb = new WebAppContext();
	       bb.setServer(webServer);
	       bb.setContextPath("/");
	       bb.setWar("src/test/webapp/");
	       
	       webServer.setHandler(bb);
	       webServer.start();
	       spartTestUtil=new SparkTestUtil(PORT);
	}

	
	@AfterClass
	public static void destroy() throws Exception{
		webServer.stop();
	}
}
