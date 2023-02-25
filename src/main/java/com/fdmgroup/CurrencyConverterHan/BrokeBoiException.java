package com.fdmgroup.CurrencyConverterHan;

/**
 * Custom Exception thrown when User has not enough money for transaction.
 * @author Han
 */
public class BrokeBoiException extends Exception{
	public BrokeBoiException(String message) {
		super(message);
	}
}
