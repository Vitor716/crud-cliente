package com.cliente.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cliente.dtos.ErroDto;
import com.cliente.exception.ErroDeNegocioException;
import com.cliente.exception.TabelaDeErros;
import com.cliente.model.Erro;

@RestControllerAdvice
public class ErrorController {
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ErroDeNegocioException.class)
	@ResponseBody
	public ResponseEntity<ErroDto> handle(ErroDeNegocioException e) {
		ErroDto erroDto = new ErroDto();
		erroDto.setErro(e.getErro());

		return ResponseEntity.status(e.getHttpStatus()).body(erroDto);
	}

	// @Valid -> Classes
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public ResponseEntity<ErroDto> handle(BindException exception) {
		List<Erro> validacoes = new ArrayList<>();

		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			Erro erro = new Erro(error.getField(), mensagem);
			validacoes.add(erro);
		}
		TabelaDeErros tabela = TabelaDeErros.ERRO_DE_VALIDACAO;
		ErroDto erroDto = new ErroDto();
		erroDto.setErro(tabela.getErro());
		erroDto.setValidacoes(validacoes);

		return ResponseEntity.status(tabela.getHttpStatus()).body(erroDto);
	}

	// @Validated -> Parametros dos Metodos
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<ErroDto> handle(ConstraintViolationException e) {
		List<Erro> validacoes = new ArrayList<>();

		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
			Erro erro = new Erro(path, violation.getMessage());
			validacoes.add(erro);
		}
		TabelaDeErros tabela = TabelaDeErros.ERRO_DE_VALIDACAO;
		ErroDto erroDto = new ErroDto();
		erroDto.setErro(tabela.getErro());
		erroDto.setValidacoes(validacoes);

		return ResponseEntity.status(tabela.getHttpStatus()).body(erroDto);
	}
}
