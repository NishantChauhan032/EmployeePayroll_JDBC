package com.capg.jdbc.employeepayroll;

public class DBServiceException extends Exception {

	DBServiceExceptionType exceptionType;

	public DBServiceException(String message, DBServiceExceptionType exceptionType) {
		super(message);
		this.exceptionType = exceptionType;
	}
}

enum DBServiceExceptionType {
	SQL_EXCEPTION, CLASS_NOT_FOUND_EXCEPTION
}
