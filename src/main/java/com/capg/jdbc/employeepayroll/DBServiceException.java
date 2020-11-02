package com.capg.jdbc.employeepayroll;

public class DBServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	DBServiceExceptionType exceptionType;

	public DBServiceException(String message, DBServiceExceptionType exceptionType) {
		super(message);
		this.exceptionType = exceptionType;
	}
}

enum DBServiceExceptionType {
	SQL_EXCEPTION, CLASS_NOT_FOUND_EXCEPTION
}
