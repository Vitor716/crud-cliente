package com.cliente.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200, unique = true, nullable = false)
	private String nome;

	@Column(length = 500)
	private String requisitos;

	@Column(length = 30, nullable = false, name = "tipo_cliente")
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	@Column(nullable = false, name = "valor_contrato")
	private BigDecimal valorContrato;

	@OneToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
}
