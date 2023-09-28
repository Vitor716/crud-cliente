package com.cliente.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cliente.dtos.ClienteEntradaDto;
import com.cliente.dtos.ClienteSaidaDto;
import com.cliente.exception.ErroDeNegocioException;
import com.cliente.exception.TabelaDeErros;
import com.cliente.model.Cliente;
import com.cliente.model.Endereco;
import com.cliente.model.Relatorio;
import com.cliente.repository.ClienteRepository;
import com.cliente.repository.EnderecoRespository;
import com.cliente.validator.ClienteValidator;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ClienteService.class)
class ClienteServiceTest {

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private EnderecoRespository enderecoRepository;

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private ClienteRepository clienteRepository;

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private ModelMapper mapper;

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private ClienteValidator validator;

	@Autowired
	private ClienteService clienteService;

	@Test
	void criarSucesso() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Endereco endereco = Mockito.mock(Endereco.class, Answers.RETURNS_MOCKS);
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);

		Mockito.when(enderecoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(endereco));
		Mockito.when(mapper.map(clienteEntradaDto, Cliente.class)).thenReturn(cliente);
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

		Assertions.assertDoesNotThrow(() -> clienteService.criar(clienteEntradaDto));

		Mockito.verify(clienteRepository, Mockito.times(1)).save(Mockito.any(Cliente.class));
	}

	@Test
	void criarSucessoSemEndereco() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteEntradaDto.getIdEndereco()).thenReturn(null);
		Mockito.when(mapper.map(clienteEntradaDto, Cliente.class)).thenReturn(cliente);
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

		Assertions.assertDoesNotThrow(() -> clienteService.criar(clienteEntradaDto));
	}

	@Test
	void erroCriarComEnderecoInexistente() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.criar(clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.CLIENTE_NAO_ENCONTRADO.getErro(), e.getErro());

		Mockito.verify(clienteRepository, Mockito.times(0)).save(Mockito.any(Cliente.class));
	}

	@Test
	void criarComErroGenerico() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(mapper.map(clienteEntradaDto, Cliente.class)).thenThrow(new RuntimeException("Erro genérico"));

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.criar(clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.ERRO_DE_SISTEMA.getErro(), e.getErro());
	}

	@Test
	void editarSucesso() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Endereco endereco = Mockito.mock(Endereco.class, Answers.RETURNS_MOCKS);
		Mockito.when(enderecoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(endereco));

		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(cliente));

		Assertions.assertDoesNotThrow(() -> clienteService.editar(cliente.getId(), clienteEntradaDto));
	}

	@Test
	void editarSucessoSemEndereco() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(clienteEntradaDto.getIdEndereco()).thenReturn(null);
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(cliente));

		Assertions.assertDoesNotThrow(() -> clienteService.editar(cliente.getId(), clienteEntradaDto));
	}

	@Test
	void erroEditarComEnderecoInexistente() {

		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Mockito.when(enderecoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(cliente));

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.editar(cliente.getId(), clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.CLIENTE_NAO_ENCONTRADO.getErro(), e.getErro());
	}

	@Test
	void erroEditarComClienteInexistente() {

		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.editar(cliente.getId(), clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.CLIENTE_NAO_ENCONTRADO.getErro(), e.getErro());
	}

	@Test
	void editarComErroGenerico() {
		ClienteEntradaDto clienteEntradaDto = Mockito.mock(ClienteEntradaDto.class, Answers.RETURNS_MOCKS);

		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(cliente));

		Endereco endereco = Mockito.mock(Endereco.class, Answers.RETURNS_MOCKS);
		Mockito.when(enderecoRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(endereco));

		doThrow(new RuntimeException("Erro genérico")).when(mapper).map(clienteEntradaDto, cliente);

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.editar(cliente.getId(), clienteEntradaDto));

		Assertions.assertEquals(TabelaDeErros.ERRO_DE_SISTEMA.getErro(), e.getErro());

	}

	@Test
	void excluirSucesso() {

		doNothing().when(validator).excluir(Mockito.any(Integer.class));

		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);

		Integer clienteId = cliente.getId();

		clienteService.excluir(clienteId);

		verify(validator).excluir(clienteId);
		verify(clienteRepository).deleteById(clienteId);
	}

	@Test
	void excluirComErroDeNegocio() {
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Integer clienteId = cliente.getId();

		doThrow(new EmptyResultDataAccessException(1)).when(clienteRepository).deleteById(clienteId);

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.excluir(cliente.getId()));

		Assertions.assertEquals(TabelaDeErros.ERRO_DE_SISTEMA.getErro(), e.getErro());

	}

	@Test
	void pegarUmExistenteSucesso() {
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(cliente));

		ClienteSaidaDto clienteSaidaDto = Mockito.mock(ClienteSaidaDto.class, Answers.RETURNS_MOCKS);
		Mockito.when(mapper.map(cliente, ClienteSaidaDto.class)).thenReturn(clienteSaidaDto);

		Assertions.assertDoesNotThrow(() -> clienteService.pegarUm(cliente.getId()));
	}

	@Test
	void erroPegarUmInexistente() {
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

		ClienteSaidaDto clienteSaidaDto = Mockito.mock(ClienteSaidaDto.class, Answers.RETURNS_MOCKS);
		Mockito.when(mapper.map(cliente, ClienteSaidaDto.class)).thenReturn(clienteSaidaDto);

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.pegarUm(cliente.getId()));

		Assertions.assertEquals(TabelaDeErros.CLIENTE_NAO_ENCONTRADO.getErro(), e.getErro());
	}

	@Test
	void pegarUmComErroDeNegocio() {
		Cliente cliente = Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS);
		Mockito.when(clienteRepository.findById(Mockito.any(Integer.class)))
				.thenThrow(new RuntimeException("Erro genérico"));

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.pegarUm((cliente.getId())));

		Assertions.assertEquals(TabelaDeErros.ERRO_DE_SISTEMA.getErro(), e.getErro());
	}

	@Test
	void listarSucesso() {
		List<Cliente> clientes = Arrays.asList(Mockito.mock(Cliente.class, Answers.RETURNS_MOCKS));
		Mockito.when(clienteRepository.findAll()).thenReturn(clientes);

		List<ClienteSaidaDto> clienteSaidaDto = Arrays
				.asList(Mockito.mock(ClienteSaidaDto.class, Answers.RETURNS_MOCKS));
		Mockito.when(mapper.map(clientes, new TypeToken<List<ClienteSaidaDto>>() {
		}.getType())).thenReturn(clienteSaidaDto);

		Assertions.assertDoesNotThrow(() -> clienteService.listar());
	}

	@Test
	void listarComErroDeNegocio() {
		Mockito.when(clienteRepository.findAll()).thenThrow(new RuntimeException("Erro genérico"));

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.listar());

		Assertions.assertEquals(TabelaDeErros.ERRO_DE_SISTEMA.getErro(), e.getErro());
	}

	@Test
	void contagemTipoClientesSucesso() {
		List<Relatorio> relatorios = Arrays.asList(Mockito.mock(Relatorio.class, Answers.RETURNS_MOCKS));
		Mockito.when(clienteRepository.contarPorTipoCliente()).thenReturn(relatorios);

		Assertions.assertNotNull(clienteService.contagemTipo());
	}

	@Test
	void contagemTipoClientesComErroDeNegocio() {
		Mockito.when(clienteRepository.contarPorTipoCliente()).thenThrow(new RuntimeException("Erro genérico"));

		ErroDeNegocioException e = Assertions.assertThrows(ErroDeNegocioException.class,
				() -> clienteService.contagemTipo());

		Assertions.assertEquals(TabelaDeErros.ERRO_DE_SISTEMA.getErro(), e.getErro());

	}

}
