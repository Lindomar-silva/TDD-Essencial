package com.algaworks.tdd.service;

import com.algaworks.tdd.email.NotificadorEmail;
import com.algaworks.tdd.model.Pedido;
import com.algaworks.tdd.repository.PedidoRepository;
import com.algaworks.tdd.sms.NotificadorSms;

public class PedidoService {

	private PedidoRepository repository;
	private NotificadorEmail email;
	private NotificadorSms sms;

	public PedidoService(PedidoRepository repository, NotificadorEmail email, NotificadorSms sms) {
		this.repository = repository;
		this.email = email;
		this.sms = sms;
	}

	public double lancar(Pedido pedido) {
		double imposto = pedido.getValor() * 0.1;
		repository.guardar(pedido);
		email.enviar(pedido);
		sms.notificar(pedido);

		return imposto;
	}

}
