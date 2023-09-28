package com.cliente.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnderecoTest {

	@Test
	void cobertura() {
		Endereco endereco = new Endereco();
		endereco.setId(null);
		endereco.setNome(null);
		Assertions.assertNotNull(endereco.toString());
	}
}
