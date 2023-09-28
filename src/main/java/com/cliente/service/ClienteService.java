package com.cliente.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRespository enderecoRespository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ClienteValidator clienteValidator;

	@Transactional
	public ClienteSaidaDto criar(ClienteEntradaDto clienteEntradaDto) {
		try {
			clienteValidator.criar(clienteEntradaDto);

			Cliente cliente = mapper.map(clienteEntradaDto, Cliente.class);

			if (clienteEntradaDto.getIdEndereco() != null) {
				Optional<Endereco> optionalEndereco = enderecoRespository.findById(clienteEntradaDto.getIdEndereco());

				if (!optionalEndereco.isPresent()) {
					log.error("criar, endereco não encontrado: id={}", clienteEntradaDto.getIdEndereco());
					throw new ErroDeNegocioException(TabelaDeErros.CLIENTE_NAO_ENCONTRADO);
				}

				Endereco endereco = optionalEndereco.get();
				cliente.setEndereco(endereco);
			}

			log.info("criar, mapeamento: clienteEntradaDto={}, entity={}", clienteEntradaDto, cliente);

			Cliente registroClienteBanco = clienteRepository.save(cliente);

			ClienteSaidaDto clienteSaidaDto = mapper.map(registroClienteBanco, ClienteSaidaDto.class);

			return clienteSaidaDto;
		} catch (ErroDeNegocioException e) {
			throw e;
		} catch (Exception e) {
			log.error("criar, erro generico: e=", e);

			throw new ErroDeNegocioException(TabelaDeErros.ERRO_DE_SISTEMA);
		}
	}

	@Transactional
	public void editar(Integer id, ClienteEntradaDto clienteEntradaDto) {
		try {
			clienteValidator.editar(id, clienteEntradaDto);

			Optional<Cliente> optional = clienteRepository.findById(id);

			if (!optional.isPresent()) {
				log.error("editar, endereco não encontrado: id={}", clienteEntradaDto.getIdEndereco());
				throw new ErroDeNegocioException(TabelaDeErros.CLIENTE_NAO_ENCONTRADO);
			}

			Cliente cliente = optional.get();

			if (clienteEntradaDto.getIdEndereco() != null) {
				Optional<Endereco> optionalEndereco = enderecoRespository.findById(clienteEntradaDto.getIdEndereco());

				if (!optionalEndereco.isPresent()) {
					log.error("editar, endereco não encontrado: id={}", clienteEntradaDto.getIdEndereco());
					throw new ErroDeNegocioException(TabelaDeErros.CLIENTE_NAO_ENCONTRADO);
				}

				Endereco endereco = optionalEndereco.get();
				cliente.setEndereco(endereco);
			}

			mapper.map(clienteEntradaDto, cliente);

			log.info("editar, mapeamento: clienteEntradaDto={}, entity={}", clienteEntradaDto, cliente);

			clienteRepository.save(cliente);
		} catch (ErroDeNegocioException e) {
			throw e;
		} catch (Exception e) {
			log.error("editar, erro generico: e=", e);

			throw new ErroDeNegocioException(TabelaDeErros.ERRO_DE_SISTEMA);
		}
	}

	@Transactional
	public void excluir(Integer id) {
		try {
			clienteValidator.excluir(id);

			log.info("excluir, mapeamento: id={}", id);
			clienteRepository.deleteById(id);
		} catch (Exception e) {
			log.error("excluir, erro generico: e=", e);

			throw new ErroDeNegocioException(TabelaDeErros.ERRO_DE_SISTEMA);
		}
	}

	public ClienteSaidaDto pegarUm(Integer id) {
		try {
			Optional<Cliente> optional = clienteRepository.findById(id);

			if (!optional.isPresent()) {
				log.error("pegarUm, cliente não encontrado: id={}", id);
				throw new ErroDeNegocioException(TabelaDeErros.CLIENTE_NAO_ENCONTRADO);
			}

			Cliente registroClienteBanco = optional.get();

			ClienteSaidaDto clienteSaidaDto = mapper.map(registroClienteBanco, ClienteSaidaDto.class);

			log.info("pegarUm, mapeamento: id={}", id);

			return clienteSaidaDto;
		} catch (ErroDeNegocioException e) {
			throw e;
		} catch (Exception e) {
			log.error("pegarUm, erro generico: e=", e);

			throw new ErroDeNegocioException(TabelaDeErros.ERRO_DE_SISTEMA);
		}
	}

	public List<ClienteSaidaDto> listar() {
		try {
			List<Cliente> clientes = clienteRepository.findAll();

			List<ClienteSaidaDto> clienteSaidaDto = mapper.map(clientes, new TypeToken<List<ClienteSaidaDto>>() {
			}.getType());

			log.info("listar, mapeamento: clienteSaidaDto={}", clienteSaidaDto);

			return clienteSaidaDto;
		} catch (Exception e) {
			log.error("listar, erro generico: e=", e);

			throw new ErroDeNegocioException(TabelaDeErros.ERRO_DE_SISTEMA);
		}
	}

	public List<Relatorio> contagemTipo() {
		try {
			List<Relatorio> contagemTipoCliente = clienteRepository.contarPorTipoCliente();

			log.info("listarTipo, mapeamento: quantidadeCliente={}", contagemTipoCliente);

			return contagemTipoCliente;
		} catch (Exception e) {
			log.error("contagemTipo, erro generico: e=", e);

			throw new ErroDeNegocioException(TabelaDeErros.ERRO_DE_SISTEMA);
		}
	}

}
