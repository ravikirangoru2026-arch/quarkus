package com.rk.quarkus.exception;

import io.quarkus.logging.Log;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String msg) {
		super(msg);
		Log.error(msg);
	}
}
