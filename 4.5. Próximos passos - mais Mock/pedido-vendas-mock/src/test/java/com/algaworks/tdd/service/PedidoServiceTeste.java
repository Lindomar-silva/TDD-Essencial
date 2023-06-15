package com.algaworks.tdd.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.algaworks.tdd.email.NotificadorEmail;
import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.model.PedidoStatus;
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
		List<AcaoLancamentoPedido> acoes = Arrays.asList(repository, email, sms);
		
		pedidoService = new PedidoService(repository, acoes);
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
		Mockito.verify(repository).executar(pedido);
	}

	@Test
	public void deveNotificarPorEmail() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(email).executar(pedido);
	}
	
	@Test
	public void deveNotificarPorSms() throws Exception {
		pedidoService.lancar(pedido);
		Mockito.verify(sms).executar(pedido);
	}
	
	@Test
	public void devePagarPedidoPendente() throws Exception {
		Long pedicoCod = 135L;
		
		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatus(PedidoStatus.PENDENTE);
		Mockito.when(repository.buscarPeloCodigo(pedicoCod)).thenReturn(pedidoPendente);
		
		Pedido pedidoPago = pedidoService.pagar(pedicoCod);
		assertEquals(pedidoPago.getStatus(), PedidoStatus.PAGO);
	}
	
	@Test(expected = StatusPedidoInvalidoException.class)
	public void deveNegarPagamento() throws Exception {
		Long pedicoCod = 135L;

		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatus(PedidoStatus.PAGO);
		Mockito.when(repository.buscarPeloCodigo(pedicoCod)).thenReturn(pedidoPendente);

		pedidoService.pagar(pedicoCod);
	}
}
