package com.fdmgroup.CurrencyConverterHan;

/**
 * Custom Exception thrown when there are no Users matching the requested transaction.
 * @author Han
 */
public class NonExistUserException extends Exception{
	public NonExistUserException(String message) {
		super(message);
	}

}
