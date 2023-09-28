package com.cliente.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cliente.dtos.EnderecoEntradaDto;
import com.cliente.dtos.EnderecoSaidaDto;
import com.cliente.model.Endereco;
import com.cliente.repository.EnderecoRespository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EnderecoService {
	@Autowired
	private EnderecoRespository enderecoRespository;

	@Autowired
	private ModelMapper mapper;

	public EnderecoSaidaDto criar(EnderecoEntradaDto enderecoEntradaDto) {
		Endereco endereco = mapper.map(enderecoEntradaDto, Endereco.class);

		log.info("criar, mapeamento: enderecoEntradaDto={}, entity={}", enderecoEntradaDto, endereco);

		Endereco registroEnderecoBanco = enderecoRespository.save(endereco);

		EnderecoSaidaDto enderecoSaidaDto = mapper.map(registroEnderecoBanco, EnderecoSaidaDto.class);

		return enderecoSaidaDto;
	}

	public List<EnderecoSaidaDto> listar() {
		List<Endereco> endereco = enderecoRespository.findAll();

		List<EnderecoSaidaDto> enderecoSaidaDto = mapper.map(endereco, new TypeToken<List<EnderecoSaidaDto>>() {
		}.getType());

		log.info("listar, mapeamento: enderecoSaidaDto={}", enderecoSaidaDto);

		return enderecoSaidaDto;

	}

}
