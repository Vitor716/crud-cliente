package com.cliente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cliente.model.Cliente;
import com.cliente.model.Relatorio;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	boolean existsByNome(String nome);

	boolean existsByNomeAndIdNot(String nome, Integer id);

	@Query("SELECT new com.client.model.Relatorio(c.tipoCliente, COUNT(c)) FROM cliente c GROUP BY c.tipoCliente")
	List<Relatorio> contarPorTipoCliente();
}
