package com.cliente.dtos;

import java.math.BigDecimal;

import com.cliente.model.TipoCliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClienteSaidaDto {
	private Integer id;
	private String nome;
	private String requisitos;
	private TipoCliente tipoCliente;
	private BigDecimal valorContrato;
	private EnderecoSaidaDto endereco;
}
