package com.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Erro {
	private String campo;
	private String mensagem;
}
