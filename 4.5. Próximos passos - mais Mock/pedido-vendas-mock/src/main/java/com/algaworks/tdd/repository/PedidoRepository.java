package com.algaworks.tdd.repository;

import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.service.AcaoLancamentoPedido;

public class PedidoRepository implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Salvando no banco de dados...");
	}

	public Pedido buscarPeloCodigo(Long pedidoCod) {
		// Deveria ir no banco de dados buscar pelo código
		return new Pedido();
	}
}
