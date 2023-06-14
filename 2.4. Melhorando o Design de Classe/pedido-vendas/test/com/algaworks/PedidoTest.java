package com.algaworks;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.desconto.CalculadoraDescontoPrimeiraFaixa;
import com.algaworks.desconto.CalculadoraDescontoSegundaFaixa;
import com.algaworks.desconto.CalculadoraDescontoTerceiraFaixa;
import com.algaworks.desconto.CalculadoraFaixaDesconto;
import com.algaworks.desconto.SemDesconto;

public class PedidoTest {

	private Pedido pedido;

	@Before
	public void setup() {
		CalculadoraFaixaDesconto calculadoraFaixaDesconto = 
				new CalculadoraDescontoTerceiraFaixa(
						new CalculadoraDescontoSegundaFaixa(
								new CalculadoraDescontoPrimeiraFaixa(
										new SemDesconto(null))));
		
		pedido = new Pedido(calculadoraFaixaDesconto);
	}

	private void assertResumoDoPedido(double valorTotal, double desconto) {
		ResumoPedido resumoPedido = pedido.resumo();

		assertEquals(valorTotal, resumoPedido.getValorTotal(), 0.0001);
		assertEquals(desconto, resumoPedido.getDesconto(), 0.0001);
	}

	@Test
	public void devePermitirAdicionarUmItemNoPedido() throws Exception {
		pedido.adicionarItem(new ItemPedido("Sabonete", 3.0, 10));
	}

	@Test
	public void deveCalcularValorTotalEDescontoParaPedidoVazio() throws Exception {
		assertResumoDoPedido(0.0, 0.0);
	}

	@Test
	public void deveCalacularOResumoParaUmItemSemDesconto() throws Exception {
		pedido.adicionarItem(new ItemPedido("Sabonete", 5.0, 5));
		assertResumoDoPedido(25.0, 0.0);
	}

	@Test
	public void deveCalcularResumoParaDoisItensSemDesconto() throws Exception {
		pedido.adicionarItem(new ItemPedido("Sabonete", 3.0, 3));
		pedido.adicionarItem(new ItemPedido("Pasta de Dente", 7.0, 3));
		assertResumoDoPedido(30.0, 0);
	}

	@Test
	public void deveAplicardescontoNaPrimeiraFaixa() throws Exception {
		pedido.adicionarItem(new ItemPedido("Crema", 20.0, 20));
		assertResumoDoPedido(400.0, 16.0);
	}

	@Test
	public void deveAplicardescontoNaSegundaFaixa() throws Exception {
		pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
		pedido.adicionarItem(new ItemPedido("Óleo", 15.0, 30));
		assertResumoDoPedido(900.0, 54.0);
	}

	@Test
	public void deveAplicardescontoNaTerceiraFaixa() throws Exception {
		pedido.adicionarItem(new ItemPedido("Creme", 15.0, 30));
		pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
		pedido.adicionarItem(new ItemPedido("Óleo", 10.0, 30));
		assertResumoDoPedido(1200.0, 96.0);
	}
}
