package com.cliente.service;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cliente.dtos.EnderecoEntradaDto;
import com.cliente.dtos.EnderecoSaidaDto;
import com.cliente.model.Endereco;
import com.cliente.repository.EnderecoRespository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EnderecoService.class)
class EnderecoServiceTest {
	@MockBean(answer = Answers.RETURNS_MOCKS)
	private EnderecoRespository enderecoRespository;

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private ModelMapper mapper;

	@Autowired
	private EnderecoService enderecoService;

	@Test
	void criarSucesso() {
		EnderecoEntradaDto enderecoEntradaDto = Mockito.mock(EnderecoEntradaDto.class, Answers.RETURNS_MOCKS);

		Endereco endereco = Mockito.mock(Endereco.class, Answers.RETURNS_MOCKS);
		Mockito.when(mapper.map(enderecoEntradaDto, Endereco.class)).thenReturn(endereco);
		Mockito.when(enderecoRespository.save(endereco)).thenReturn(endereco);

		Assertions.assertDoesNotThrow(() -> enderecoService.criar(enderecoEntradaDto));
	}

	@Test
	void listarSucesso() {
		List<Endereco> enderecos = Arrays.asList(Mockito.mock(Endereco.class, Answers.RETURNS_MOCKS));
		Mockito.when(enderecoRespository.findAll()).thenReturn(enderecos);

		List<EnderecoSaidaDto> enderecoSaidaDtos = Arrays
				.asList(Mockito.mock(EnderecoSaidaDto.class, Answers.RETURNS_MOCKS));
		Mockito.when(mapper.map(enderecos, new TypeToken<List<EnderecoSaidaDto>>() {
		}.getType())).thenReturn(enderecoSaidaDtos);

		Assertions.assertDoesNotThrow(() -> enderecoService.listar());
	}
}
