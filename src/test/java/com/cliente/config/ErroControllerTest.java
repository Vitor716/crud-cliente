package com.cliente.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cliente.dtos.ErroDto;
import com.cliente.exception.ErroDeNegocioException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ErrorController.class)
class ErroControllerTest {
	@Autowired
	private ErrorController errorController;

	@MockBean(answer = Answers.RETURNS_MOCKS)
	private MessageSource messageSource;

	@Test
	void testHandleErroDeNegocioException() {
		ErroDeNegocioException e = new ErroDeNegocioException(HttpStatus.BAD_REQUEST, "Erro de Negócio");

		ResponseEntity<ErroDto> responseEntity = errorController.handle(e);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		Assertions.assertEquals("Erro de Negócio", responseEntity.getBody().getErro());
	}

	@Test
	void testHandleBindExceptionGetBindingResult() {
		BindException exception = Mockito.mock(BindException.class, Answers.RETURNS_MOCKS);

		BindingResult bindingResult = Mockito.mock(BindingResult.class, Answers.RETURNS_MOCKS);
		Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);

		List<FieldError> lista = new ArrayList<>();
		Mockito.when(bindingResult.getFieldErrors()).thenReturn(lista);

		FieldError error = Mockito.mock(FieldError.class, Answers.RETURNS_MOCKS);
		lista.add(error);

		ResponseEntity<ErroDto> responseEntity = errorController.handle(exception);
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		Assertions.assertEquals("Erro de Validação", responseEntity.getBody().getErro());
	}

	@Test
    void testHandleConstraintViolationException() {
        abstract class ConstraintViolationMock implements ConstraintViolation<String> {

        }

        
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class,Answers.RETURNS_MOCKS);
        
        //constraintViolationMock - violation
        ConstraintViolationMock violation = Mockito.mock(ConstraintViolationMock.class, Answers.RETURNS_MOCKS);


        //violation - violations
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        Mockito.when(exception.getConstraintViolations()).thenReturn(violations);
        
        
        violations.add(violation);

        PathImpl pathImpl = Mockito.mock(PathImpl.class, Answers.RETURNS_MOCKS);
        Mockito.when(violation.getPropertyPath()).thenReturn(pathImpl);


        ResponseEntity<ErroDto> responseEntity = errorController.handle(exception);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals("Erro de Validação", responseEntity.getBody().getErro());
    }
}
