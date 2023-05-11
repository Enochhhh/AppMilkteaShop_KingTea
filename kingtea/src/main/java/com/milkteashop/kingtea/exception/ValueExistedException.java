package com.milkteashop.kingtea.exception;

import lombok.Getter;

@Getter
public class ValueExistedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String path;
	
	public ValueExistedException(String message, String path) {
		super(message);
		this.path = path;
	}
	
	public ValueExistedException(String message, String path, Throwable e) {
		super(message, e);
		this.path = path;
	}
}
