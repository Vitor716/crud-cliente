package com.cliente.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnderecoSaidaDtoTest {
	@Test
	void cobertura() {
		EnderecoSaidaDto endereco = new EnderecoSaidaDto();
		endereco.setId(null);
		endereco.setNome(null);
		Assertions.assertNotNull(endereco.toString());
	}
}
