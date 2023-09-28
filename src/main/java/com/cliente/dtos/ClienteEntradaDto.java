package com.cliente.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cliente.model.TipoCliente;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClienteEntradaDto {

	@NotBlank(message = "obrigatório")
	@Size(min = 0, max = 200, message = "máximo 200 caracteres")
	private String nome;

	@Size(min = 0, max = 500, message = "máximo 500 caracteres")
	private String requisitos;

	@NotNull(message = "obrigatório")
	private TipoCliente tipoCliente;

	@DecimalMin(value = "0.01", message = "valor maior igual que 0.01")
	@DecimalMax(value = "1000000.00", message = "valor menor ou igual a 1000000.00")
	@Digits(integer = 7, fraction = 2, message = "inválido")
	@NotNull(message = "obrigatório")
	private BigDecimal valorContrato;

	private Integer idEndereco;
}
