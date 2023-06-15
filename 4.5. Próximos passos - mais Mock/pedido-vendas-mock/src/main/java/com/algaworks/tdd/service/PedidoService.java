package com.algaworks.tdd.service;

import java.util.List;

import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.model.PedidoStatus;
import com.algaworks.tdd.repository.PedidoRepository;

public class PedidoService {

	private List<AcaoLancamentoPedido> acoes;
	private PedidoRepository repository;

	public PedidoService(PedidoRepository repository, List<AcaoLancamentoPedido> acoes) {
		this.acoes = acoes;
		this.repository = repository;
	}

	public double lancar(Pedido pedido) {
		double imposto = pedido.getValor() * 0.1;
		acoes.forEach(acao -> acao.executar(pedido));

		return imposto;
	}

	public Pedido pagar(Long pedicoCod) {
		Pedido pedido = repository.buscarPeloCodigo(pedicoCod);

		if (!pedido.getStatus().equals(PedidoStatus.PENDENTE)) {
			throw new StatusPedidoInvalidoException();
		}

		pedido.setStatus(PedidoStatus.PAGO);
		return pedido;
	}

}
