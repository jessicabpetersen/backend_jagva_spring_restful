package br.com.jessicabpetersen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnableToDeleteException(String string) {
		super(string);
	}

}
