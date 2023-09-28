package com.cliente.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cliente.dtos.ClienteEntradaDto;
import com.cliente.exception.ErroDeNegocioException;
import com.cliente.exception.TabelaDeErros;
import com.cliente.repository.ClienteRepository;

@Component
public class ClienteValidator {
	@Autowired
	private ClienteRepository clienteRepository;

	public void criar(ClienteEntradaDto clienteEntradaDto) {
		if (clienteRepository.existsByNome(clienteEntradaDto.getNome())) {
			throw new ErroDeNegocioException(TabelaDeErros.NOME_JA_EM_USO);
		}
	}

	public void editar(Integer id, ClienteEntradaDto clienteEntradaDto) {
		if (clienteRepository.existsByNomeAndIdNot(clienteEntradaDto.getNome(), id)) {
			throw new ErroDeNegocioException(TabelaDeErros.NOME_JA_EM_USO);
		}
	}

	public void excluir(Integer id) {
		if (!clienteRepository.existsById(id)) {
			throw new ErroDeNegocioException(TabelaDeErros.CLIENTE_NAO_ENCONTRADO);
		}
	}

}
