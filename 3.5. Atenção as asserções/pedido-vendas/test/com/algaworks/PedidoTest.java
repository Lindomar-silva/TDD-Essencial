package com.algaworks;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PedidoTest {

	private PedidoBuilder pedido;

	@Before
	public void setup() {
		pedido = new PedidoBuilder();
	}

	private void assertResumoDoPedido(double valorTotal, double desconto) {
		ResumoPedido resumoPedido = pedido.construir().resumo();

//		assertEquals(valorTotal, resumoPedido.getValorTotal(), 0.0001);
//		assertEquals(desconto, resumoPedido.getDesconto(), 0.0001);
		assertEquals(new ResumoPedido(valorTotal, desconto), resumoPedido);
	}

	@Test
	public void deveCalcularValorTotalEDescontoParaPedidoVazio() throws Exception {
		assertResumoDoPedido(0.0, 0.0);
	}

	@Test
	public void deveCalacularOResumoParaUmItemSemDesconto() throws Exception {
//		pedido.adicionarItem(new ItemPedido("Sabonete", 5.0, 5));
		pedido.comItem(5.0, 5);
		assertResumoDoPedido(25.0, 0.0);
	}

	@Test
	public void deveCalcularResumoParaDoisItensSemDesconto() throws Exception {
//		pedido.adicionarItem(new ItemPedido("Sabonete", 3.0, 3));
//		pedido.adicionarItem(new ItemPedido("Pasta de Dente", 7.0, 3));
		pedido
			.comItem(3.0, 3)
			.comItem(7.0, 3);
		
		assertResumoDoPedido(30.0, 0);
	}

	@Test
	public void deveAplicardescontoNaPrimeiraFaixa() throws Exception {
//		pedido.adicionarItem(new ItemPedido("Crema", 20.0, 20));
		pedido.comItem(20.0, 20);
		assertResumoDoPedido(400.0, 16.0);
	}

	@Test
	public void deveAplicardescontoNaSegundaFaixa() throws Exception {
//		pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
//		pedido.adicionarItem(new ItemPedido("Óleo", 15.0, 30));
		pedido
			.comItem(15.0, 30)
			.comItem(15.0, 30);
		
		assertResumoDoPedido(900.0, 54.0);
	}

	@Test
	public void deveAplicardescontoNaTerceiraFaixa() throws Exception {
//		pedido.adicionarItem(new ItemPedido("Creme", 15.0, 30));
//		pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
//		pedido.adicionarItem(new ItemPedido("Óleo", 10.0, 30));
		pedido
			.comItem(15.0, 30)
			.comItem(15.0, 30)
			.comItem(10.0, 30);
		
		assertResumoDoPedido(1200.0, 96.0);
	}
}
