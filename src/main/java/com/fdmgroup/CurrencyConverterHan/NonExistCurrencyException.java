package com.fdmgroup.CurrencyConverterHan;

/**
 * Custom exception to throw when the User does not have any currency requested in the transaction
 * @author Han
 */
public class NonExistCurrencyException extends Exception{
	public NonExistCurrencyException(String message) {
		super(message);
	}
}
