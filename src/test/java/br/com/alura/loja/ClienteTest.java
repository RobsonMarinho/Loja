package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class ClienteTest {
	private HttpServer server;
	private Client client;
	private WebTarget target;

	// Rodar antes de todos os testes
	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
		this.client = ClientBuilder.newClient();	//Cria o cliente
		this.target = client.target("http://localhost:8080");
	}

	// Para de rodar depois dos testes
	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrasOCarrinhoEsperado() {
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void testaQueSuportaNovosCarrinhos() {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314, "Microfone", 37, 1));
		carrinho.setRua("Rua Vergueiro 3185");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201,  response.getStatus());
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		Assert.assertTrue(conteudo.contains("Microfone"));
	}
}
