package com.cliente.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RelatorioTest {
	@Test
	void cobertura() {
		TipoCliente tipo = TipoCliente.PESSOA_FISICA;
		Long quantidade = 10L;
		Relatorio relatorio = new Relatorio(tipo, quantidade);

		Assertions.assertEquals(tipo, relatorio.getTipo());
		Assertions.assertEquals(quantidade, relatorio.getQuantidade());
	}
}
