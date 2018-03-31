package com.cherkashyn.vitalii.akka.web;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.scaladsl.Source;

public class App {

	public static void main( String[] args ){
		System.out.println("-- begin --");
		ActorSystem system = ActorSystem.create();
		Materializer materializer = ActorMaterializer.create(system);

		akka.stream.javadsl.Source<IncomingConnection, CompletionStage<akka.http.javadsl.ServerBinding>> serverSource =
		  Http.get(system).bind(ConnectHttp.toHost("localhost", 8080), materializer);

		CompletionStage<ServerBinding> serverBindingFuture =
		  serverSource.to(Sink.foreach(connection -> {
		      System.out.println("Accepted new connection from " + connection.remoteAddress());
		    }
		  )).run(materializer);
		System.out.println("--- end ---");
	}
	
}
