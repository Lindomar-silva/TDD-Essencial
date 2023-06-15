package com.algaworks.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.model.Passageiro;
import com.algaworks.model.TipoPassageiro;
import com.algaworks.model.Voo;

public class PressoPassagemServiceTest {

	PrecoPassagemService precoPassagemService;

	@Before
	public void setup() {
		precoPassagemService = new PrecoPassagemService();
	}

	@Test
	public void devePermitirCalcularValorPassagemParaPassageiroGold_ComValorAbaixoDoLimite() throws Exception {
		Passageiro passageiro = new Passageiro("Paulo", TipoPassageiro.GOLD);
		Voo voo = new Voo("Londrina", "São Paulo", 100.0);
		assertValorPassagem(passageiro, voo, 90.0);
	}

	@Test
	public void devePermitirCalcularValorPassagemParaPassageiroGold_ComValorAcimaDoLimite() throws Exception {
		Passageiro passageiro = new Passageiro("Paulo", TipoPassageiro.GOLD);
		Voo voo = new Voo("Londrina", "São Paulo", 600.0);
		assertValorPassagem(passageiro, voo, 510.0);
	}

	@Test
	public void devePermitirCalcularValorPassagemParaPassageiroSilver_ComValorAbaixoDoLimite() throws Exception {
		Passageiro passageiro = new Passageiro("Paulo", TipoPassageiro.SILVER);
		Voo voo = new Voo("Londrina", "São Paulo", 100.0);
		assertValorPassagem(passageiro, voo, 94.0);
	}
	
	@Test
	public void devePermitirCalcularValorPassagemParaPassageiroSilver_ComValorAcimaDoLimite() throws Exception {
		Passageiro passageiro = new Passageiro("Paulo", TipoPassageiro.SILVER);
		Voo voo = new Voo("Londrina", "São Paulo", 800.0);
		assertValorPassagem(passageiro, voo, 720.0);
	}

	private void assertValorPassagem(Passageiro passageiro, Voo voo, double esperado) {
		double valor = precoPassagemService.calcular(passageiro, voo);
		assertEquals(esperado, valor, 0.0001);
	}
}
