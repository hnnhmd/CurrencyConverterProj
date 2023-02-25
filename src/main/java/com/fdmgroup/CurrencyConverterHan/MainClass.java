package com.fdmgroup.CurrencyConverterHan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainClass {

	public static void main(String[] args) throws StreamReadException, DatabindException, IOException, NonExistUserException, NonExistCurrencyException, BrokeBoiException {
		// TODO Auto-generated method stub
		
		UserList ul = new UserList();
		//reading from the JSON
		ul.setUserListFromJSON();
		
		//converting - catching custom erros we created in case there are no matching users, no matching currency to convert, or not enough money to convert.
		try {
			TransactionProcessor.executeTransaction(ul);
		} catch( NonExistUserException | NonExistCurrencyException | BrokeBoiException e ) {
			System.out.println("Exception has been caught, look at log/console for more info");
		}
		
		//writing it to the JSON file post conversion
		TransactionProcessor.updateUserFile(ul);


	}

}
