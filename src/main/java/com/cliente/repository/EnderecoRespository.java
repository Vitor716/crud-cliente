package com.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cliente.model.Endereco;

public interface EnderecoRespository extends JpaRepository<Endereco, Integer> {

}
