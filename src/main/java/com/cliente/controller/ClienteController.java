package com.cliente.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.dtos.ClienteEntradaDto;
import com.cliente.dtos.ClienteSaidaDto;
import com.cliente.model.Relatorio;
import com.cliente.service.ClienteService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("clientes")
@Log4j2
@Validated
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ClienteSaidaDto criar(@Valid @RequestBody ClienteEntradaDto clienteEntradaDto) {
		log.info("salvar : {}", clienteEntradaDto);

		return clienteService.criar(clienteEntradaDto);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@PutMapping("id/{id}")
	public void editar(@Positive(message = "O ID não pode ser negativo ou zero") @PathVariable Integer id,
			@Valid @RequestBody ClienteEntradaDto clienteEntradaDto) {

		log.info("editar: {}, {}", id, clienteEntradaDto);

		clienteService.editar(id, clienteEntradaDto);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("id/{id}")
	public void excluir(@Positive(message = "O ID não pode ser negativo ou zero") @PathVariable Integer id) {

		log.info("excluir : {}", id);

		clienteService.excluir(id);
	}

	@GetMapping("id/{id}")
	public ClienteSaidaDto pegarUm(@Positive(message = "O ID não pode ser negativo ou zero") @PathVariable Integer id) {
		log.info("listar : {}", id);

		return clienteService.pegarUm(id);
	}

	@GetMapping
	public List<ClienteSaidaDto> listar() {
		log.info("listar");

		return clienteService.listar();
	}

	@GetMapping("contagemTipo")
	public List<Relatorio> contagemTipo() {
		log.info("listar tipo");

		return clienteService.contagemTipo();
	}

}
