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

import com.cliente.dtos.ClienteEntradaDto;
import com.cliente.service.ClienteService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClienteController.class)
class ClienteControllerTest {

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private ClienteService clienteService;

	@Autowired
	private ClienteController controller;

	@Test
	void criarSucesso() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Assertions.assertDoesNotThrow(() -> controller.criar(clienteEntradaDto));
	}

	@Test
	void editarSucesso() {
		Integer id = 1;
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Assertions.assertDoesNotThrow(() -> controller.editar(id, clienteEntradaDto));
	}

	@Test
	void excluirSucesso() {
		Integer id = 1;

		Assertions.assertDoesNotThrow(() -> controller.excluir(id));
	}

	@Test
	void pegarUmSucesso() {
		Integer id = 1;

		Assertions.assertDoesNotThrow(() -> controller.pegarUm(id));
	}

	@Test
	void listarSucesso() {
		Assertions.assertDoesNotThrow(() -> controller.listar());
	}

	@Test
	void contagemTipoSucesso() {
		Assertions.assertDoesNotThrow(() -> controller.contagemTipo());
	}
}
