package com.milkteashop.kingtea.exception;

import lombok.Getter;

@Getter
public class NotFoundValueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final String path;
	
	public NotFoundValueException(String message, String path) {
		super(message);
		this.path = path;
	}
	
	public NotFoundValueException(String message, String path, Throwable cause) {
		super(message, cause);
		this.path = path;
	}
}
