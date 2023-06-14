package com.algaworks;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.desconto.CalculadoraFaixaDesconto;

public class Pedido {

	private List<ItemPedido> itens = new ArrayList<>();
	private CalculadoraFaixaDesconto calculadoraFaixaDesconto;

	public Pedido(CalculadoraFaixaDesconto calculadoraFaixaDesconto) {
		this.calculadoraFaixaDesconto = calculadoraFaixaDesconto;
	}

	public void adicionarItem(ItemPedido itemPedido) {
		ValidarQuantidadeItens(itemPedido);
		itens.add(itemPedido);
	}

	private void ValidarQuantidadeItens(ItemPedido itemPedido) {
		if (itemPedido.getQuantidade() < 0) {
			throw new QuantidadeItensInvalidaException();
		}
	}

	public ResumoPedido resumo() {
		double valorTotal = itens.stream().mapToDouble(
				item -> item.getValorUnitario() * item.getQuantidade()).sum();

		double desconto = calculadoraFaixaDesconto.desconto(valorTotal);
		return new ResumoPedido(valorTotal, desconto);
	}

}
