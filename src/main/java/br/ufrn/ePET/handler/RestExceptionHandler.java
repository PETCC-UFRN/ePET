package br.ufrn.ePET.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.ufrn.ePET.error.ErrorDetails;
import br.ufrn.ePET.error.ResourceNotFoundDetails;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.error.ValidationErrorDetail;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException rnfe){
		ResourceNotFoundDetails rnfd =  ResourceNotFoundDetails.Builder
				.newBuilder()
				.setTimestamp(new Date().getTime())
				.setStatus(HttpStatus.NOT_FOUND.value())
				.setTitulo("Recuso não encontrado")
				.setDetalhes(rnfe.getMessage())
				.setMensagem(rnfe.getClass().getName())
				.build();
				
		return new ResponseEntity<>(rnfd, HttpStatus.NOT_FOUND);
	}
	
	//@ExceptionHandler(MethodArgumentNotValidException.class)
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request){
		List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
		String campo = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String campoMenssagem = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationErrorDetail ved =  ValidationErrorDetail.Builder
				.newBuilder()
				.setTimestamp(new Date().getTime())
				.setStatus(HttpStatus.BAD_REQUEST.value())
				.setTitulo("Argumentos inválidos")
				.setDetalhes(manve.getMessage())
				.setMensagem(manve.getClass().getName())
				.setCampo(campo)
				.setCampoMenssagem(campoMenssagem)
				.build();
				
		return new ResponseEntity<>(ved, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails ed =  ResourceNotFoundDetails.Builder
				.newBuilder()
				.setTimestamp(new Date().getTime())
				.setStatus(status.value())
				.setTitulo("Erro interno")
				.setDetalhes(ex.getMessage())
				.setMensagem(ex.getClass().getName())
				.build();
				
		return new ResponseEntity<>(ed, HttpStatus.NOT_FOUND);
	}

}