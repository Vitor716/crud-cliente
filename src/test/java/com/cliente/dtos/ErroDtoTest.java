package com.cliente.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErroDtoTest {
	@Test
	void cobertura() {
		ErroDto erro = new ErroDto();
		erro.setErro(null);
		erro.setValidacoes(null);
		Assertions.assertNotNull(erro.toString());
	}
}
