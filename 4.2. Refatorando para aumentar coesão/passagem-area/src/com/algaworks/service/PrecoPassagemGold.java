package com.algaworks.service;

import com.algaworks.model.Voo;

public class PrecoPassagemGold implements CalculadoraPrecoPassagem {

	@Override
	public double calcular(Voo voo) {
		if (voo.getPreco() > 500.0) {
			return CalcularValorAcimaDoLimite(voo);
		}

		return CalcularValorAbaixoDoLimite(voo);
	}

	private double CalcularValorAbaixoDoLimite(Voo voo) {
		return voo.getPreco() * 0.9;
	}

	private double CalcularValorAcimaDoLimite(Voo voo) {
		return voo.getPreco() * 0.85;
	}

}
