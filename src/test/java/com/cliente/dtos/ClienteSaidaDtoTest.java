package com.cliente.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClienteSaidaDtoTest {
	@Test
	void cobertura() {
		ClienteSaidaDto cliente = new ClienteSaidaDto();
		cliente.setId(null);
		cliente.setNome(null);
		cliente.setRequisitos(null);
		cliente.setTipoCliente(null);
		cliente.setValorContrato(null);
		cliente.setEndereco(null);
		Assertions.assertNotNull(cliente.toString());
	}
}
