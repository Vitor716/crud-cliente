package com.cliente.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErroTest {

	@Test
	void testContrutorEGetters() {
		String campo = "testeCampo";
		String mensagem = "testeMensagem";
		Erro erro = new Erro(campo, mensagem);
		Assertions.assertEquals(campo, erro.getCampo());
		Assertions.assertEquals(mensagem, erro.getMensagem());
	}

	@Test
	void testSetters() {
		String campo = "testeCampo";
		String mensagem = "testeMensagem";
		Erro erro = new Erro(null, null);
		erro.setCampo(campo);
		erro.setMensagem(mensagem);
		Assertions.assertEquals(campo, erro.getCampo());
		Assertions.assertEquals(mensagem, erro.getMensagem());
	}
}
