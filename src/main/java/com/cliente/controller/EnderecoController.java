package com.cliente.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.dtos.EnderecoEntradaDto;
import com.cliente.dtos.EnderecoSaidaDto;
import com.cliente.service.EnderecoService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("endereco")
@Log4j2
@Validated
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public EnderecoSaidaDto criar(@Valid @RequestBody EnderecoEntradaDto enderecoEntradaDto) {
		log.info("salvar : {}", enderecoEntradaDto);

		return enderecoService.criar(enderecoEntradaDto);
	}

	@GetMapping
	public List<EnderecoSaidaDto> listar() {
		log.info("listar");

		return enderecoService.listar();
	}

}
