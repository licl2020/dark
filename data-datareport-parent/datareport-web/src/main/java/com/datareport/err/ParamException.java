package com.datareport.err;

public class ParamException extends RuntimeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamException(String msg) {
		super(msg);
	}

	public ParamException() {
		super();
	}
}
