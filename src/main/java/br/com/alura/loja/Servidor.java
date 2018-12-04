package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	public static void main(String[] args) throws IOException {
		org.glassfish.grizzly.http.server.HttpServer server = inicializaServidor(); // M�todo
																					// est�tico
		System.out.println("Servidor rodando"); // exibe a msg
		System.in.read(); // espera dar enter
		server.stop(); // para o servidor
	}

	static org.glassfish.grizzly.http.server.HttpServer inicializaServidor() {
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		URI uri = URI.create("http://localhost:8080/");
		org.glassfish.grizzly.http.server.HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		return server;
	}
}
