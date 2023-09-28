package com.cliente.dtos;

import java.math.BigDecimal;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cliente.model.TipoCliente;

class ClienteEntradaDtoTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void cobertura() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setNome(null);
		entradaDto.setRequisitos(null);
		entradaDto.setTipoCliente(null);
		entradaDto.setValorContrato(null);
		entradaDto.setIdEndereco(null);

		Assertions.assertNotNull(entradaDto.toString());
	}

	@Test
	void validarSucesso() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setNome("a");
		entradaDto.setTipoCliente(TipoCliente.PESSOA_FISICA);
		entradaDto.setValorContrato(new BigDecimal("1.00"));

		Assertions.assertTrue(validator.validate(entradaDto).isEmpty());
	}

	@Test
	void validarErroObrigatórios() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setNome(null);
		entradaDto.setTipoCliente(null);
		entradaDto.setValorContrato(null);

		Assertions.assertEquals(3, validator.validate(entradaDto).size());
	}

	@Test
	void validarNotBlank() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setNome("");

		Assertions.assertFalse(validator.validate(entradaDto).isEmpty());
	}

	@Test
	void validarDecimanMin() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setValorContrato(new BigDecimal("1.00"));

		Assertions.assertFalse(validator.validate(entradaDto).isEmpty());
	}

	@Test
	void validarDecimalMax() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setValorContrato(new BigDecimal("1000000.01"));

		Assertions.assertFalse(validator.validate(entradaDto).isEmpty());
	}

	@Test
	void validarDigits() {
		ClienteEntradaDto entradaDto = new ClienteEntradaDto();
		entradaDto.setValorContrato(new BigDecimal("10000000.001"));

		Assertions.assertFalse(validator.validate(entradaDto).isEmpty());
	}
}
