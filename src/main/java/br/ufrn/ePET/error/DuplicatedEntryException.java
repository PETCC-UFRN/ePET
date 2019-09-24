package br.ufrn.ePET.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DuplicatedEntryException extends RuntimeException{
	
	public DuplicatedEntryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
