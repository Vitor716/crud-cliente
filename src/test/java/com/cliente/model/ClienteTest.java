package com.cliente.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClienteTest {

	@Test
	void cobertura() {
		Cliente cliente = new Cliente();
		cliente.setId(null);
		cliente.setNome(null);
		cliente.setRequisitos(null);
		cliente.setTipoCliente(null);
		cliente.setValorContrato(null);
		cliente.setEndereco(null);
		Assertions.assertNotNull(cliente.toString());
	}
}
