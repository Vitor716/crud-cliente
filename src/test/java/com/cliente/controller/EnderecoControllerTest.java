package com.cliente.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cliente.dtos.EnderecoEntradaDto;
import com.cliente.service.EnderecoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnderecoController.class)
class EnderecoControllerTest {

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private EnderecoService enderecoService;

	@Autowired
	private EnderecoController controller;

	@Test
	void criarSucesso() {
		EnderecoEntradaDto enderecoEntradaDto = Mockito.mock(EnderecoEntradaDto.class, Answers.RETURNS_MOCKS);

		Assertions.assertDoesNotThrow(() -> controller.criar(enderecoEntradaDto));
	}

	@Test
	void listarSucesso() {
		Assertions.assertDoesNotThrow(() -> controller.listar());
	}
}
