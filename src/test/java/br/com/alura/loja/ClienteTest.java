package br.com.alura.loja;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

public class ClienteTest {
	private HttpServer server;

	// Rodar antes de todos os testes
	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
	}

	// Para de rodar depois dos testes
	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		Client client = ClientBuilder.newClient(); // Cria cliente
		WebTarget target = client.target("http://localhost:8080"); // Acesso ao
																	// localhost
		String conteudo = target.path("/carrinhos").request().get(String.class);
		System.out.println(conteudo);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

		server.stop();// Para o servidor
	}
}
