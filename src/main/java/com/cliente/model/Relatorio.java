package com.cliente.model;

import lombok.Getter;

@Getter
public class Relatorio {

	private TipoCliente tipo;
	private Long quantidade;

	public Relatorio(TipoCliente tipo, Long quantidade) {
		this.tipo = tipo;
		this.quantidade = quantidade;
	}
}
