package com.milkteashop.kingtea.exception;

import org.springframework.security.authentication.BadCredentialsException;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BadCredentialsException {
	
	private static final long serialVersionUID = 1L;
	private final String path;
	
	public UnauthorizedException(String message, String path) {
		super(message);
		this.path = path;
	}
	
	public UnauthorizedException(String message, String path, Throwable cause) {
		super(message, cause);
		this.path = path;
	}
}
