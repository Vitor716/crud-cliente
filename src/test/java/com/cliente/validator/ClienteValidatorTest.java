package com.cliente.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cliente.dtos.ClienteEntradaDto;
import com.cliente.exception.ErroDeNegocioException;
import com.cliente.exception.TabelaDeErros;
import com.cliente.repository.ClienteRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClienteValidator.class)
class ClienteValidatorTest {
	@Autowired
	private ClienteValidator clienteValidator;

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private ClienteRepository clienteRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCriar() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteRepository.existsByNome(clienteEntradaDto.getNome())).thenReturn(false);

		Assertions.assertDoesNotThrow(() -> clienteValidator.criar(clienteEntradaDto));
	}

	@Test
	void testCriarComNomeJaEmUso() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteRepository.existsByNome(clienteEntradaDto.getNome())).thenReturn(true);

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteValidator.criar(clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.NOME_JA_EM_USO.getErro(), e.getErro());

	}

	@Test
	void testEditar() {
		Integer id = 1;
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteRepository.existsByNomeAndIdNot(clienteEntradaDto.getNome(), id)).thenReturn(false);

		Assertions.assertDoesNotThrow(() -> clienteValidator.editar(id, clienteEntradaDto));
	}

	@Test
	void testEditarComNomeJaEmUso() {
		Integer id = 1;
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteRepository.existsByNomeAndIdNot(clienteEntradaDto.getNome(), id)).thenReturn(true);

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteValidator.editar(id, clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.NOME_JA_EM_USO.getErro(), e.getErro());

	}

	@Test
	void testExcluir() {
		Integer id = 1;
		Mockito.when(clienteRepository.existsById(id)).thenReturn(true);

		Assertions.assertDoesNotThrow(() -> clienteValidator.excluir(id));
	}

	@Test
	void testExcluirClienteNaoEncontrado() {
		Integer id = 1;
		Mockito.when(clienteRepository.existsById(id)).thenReturn(false);

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteValidator.excluir(id));

		Assertions.assertEquals(TabelaDeErros.CLIENTE_NAO_ENCONTRADO.getErro(), e.getErro());

	}

}
