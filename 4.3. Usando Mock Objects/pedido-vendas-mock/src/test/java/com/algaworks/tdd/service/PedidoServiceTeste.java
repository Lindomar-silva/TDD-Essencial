package com.algaworks.tdd.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.algaworks.tdd.email.NotificadorEmail;
import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.model.builder.PedidoBuilder;
import com.algaworks.tdd.repository.PedidoRepository;
import com.algaworks.tdd.sms.NotificadorSms;

public class PedidoServiceTeste {

	private PedidoService pedidoService; 
	
	@Mock
	private PedidoRepository repository;
	
	@Mock
	private NotificadorEmail email;
	
	@Mock
	private NotificadorSms sms;
	
	private Pedido pedido;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		pedidoService = new PedidoService(repository, email, sms);
		pedido = new PedidoBuilder()
				.comValor(100.0)
				.para("João", "joao@teste.com", "16-565-5656")
				.construir();
	}
	
	@Test
	public void deveCalcularOImposto() throws Exception {
		double imposto = pedidoService.lancar(pedido);
		assertEquals(10.0, imposto, 0.0001);
	}

	@Test
	public void deveSalvarPedidoNoBancoDeDados() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(repository).guardar(pedido);
	}

	@Test
	public void deveNotificarPorEmail() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(email).enviar(pedido);
	}
	
	@Test
	public void deveNotificarPorSms() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(sms).notificar(pedido);
	}
}
