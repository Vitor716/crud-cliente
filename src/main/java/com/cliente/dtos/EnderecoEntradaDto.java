package com.cliente.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnderecoEntradaDto {

	@NotBlank(message = "obrigatório")
	@Size(min = 0, max = 200, message = "máximo 200 caracteres")
	public String nome;
}
