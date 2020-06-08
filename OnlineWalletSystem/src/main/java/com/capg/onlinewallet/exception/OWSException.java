package com.capg.onlinewallet.exception;

public class OWSException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public OWSException(String messsage) {
		super(messsage);
	}
}
