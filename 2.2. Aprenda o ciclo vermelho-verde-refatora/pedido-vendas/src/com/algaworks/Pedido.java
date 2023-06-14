package com.algaworks;

public class Pedido {

	private double valorTotal;
	private double desconto;

	public void adicionarItem(ItemPedido itemPedido) {
		valorTotal = itemPedido.getValorUnitario() * itemPedido.getQuantidade();
	}

	public double valorTotal() {
		return valorTotal;
	}

	public double desconto() {
		return desconto;
	}

}
