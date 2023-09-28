package com.cliente.dtos;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnderecoEntradaDtoTest {
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void cobertura() {
		EnderecoEntradaDto entradaDto = new EnderecoEntradaDto();
		entradaDto.setNome(null);

		Assertions.assertNotNull(entradaDto.toString());
	}

	@Test
	void validarSucesso() {
		EnderecoEntradaDto entradaDto = new EnderecoEntradaDto();
		entradaDto.setNome("a");
		entradaDto.setNome("a");

		Assertions.assertTrue(validator.validate(entradaDto).isEmpty());
	}

	@Test
	void validarErroObrigatórios() {
		EnderecoEntradaDto entradaDto = new EnderecoEntradaDto();
		entradaDto.setNome("a");

		Assertions.assertEquals(0, validator.validate(entradaDto).size());
	}

	@Test
	void validarNotBlank() {
		EnderecoEntradaDto entradaDto = new EnderecoEntradaDto();
		entradaDto.setNome("a");
		entradaDto.setNome("");
		Assertions.assertFalse(validator.validate(entradaDto).isEmpty());
	}
}
