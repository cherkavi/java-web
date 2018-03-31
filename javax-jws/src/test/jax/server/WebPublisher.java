package test.jax.server;

import javax.xml.ws.Endpoint;

import test.jax.WebImplementation;

public class WebPublisher {
	public static void main(String[] args){
		System.out.println("begin");
		Endpoint.publish("http://localhost:8888/ws", new WebImplementation());
		System.out.println("-end-");
	}
}
