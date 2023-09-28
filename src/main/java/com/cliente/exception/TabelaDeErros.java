package com.cliente.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TabelaDeErros {
	CLIENTE_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Cliente não encontrado"),
	NOME_JA_EM_USO(HttpStatus.PRECONDITION_FAILED, "Nome já em uso"),
	ERRO_DE_VALIDACAO(HttpStatus.BAD_REQUEST, "Erro de Validação"),

	ERRO_DE_SISTEMA(HttpStatus.INTERNAL_SERVER_ERROR, "Sistema indisponível");

	private final HttpStatus httpStatus;
	private final String erro;
}
