package com.fdmgroup.CurrencyConverterHan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * TransactionProcessor class is a helper class meant to convert the currencies of Users, as well as transcribing the data to a JSON file.
 * @author Han
 */
public class TransactionProcessor {
	
	
	private static final Logger logger = LogManager.getLogger(TransactionProcessor.class);
	
	/**
	 * Converts the currencies in User's wallets to those requested in the transactions file.
	 * @param userListArg The UserList class.
	 * @throws StreamReadException 
	 * @throws DatabindException
	 * @throws IOException
	 * @throws NonExistUserException A custom exception thrown when the UserList provided has the ArrayList of Users that does not contain some that has been provided in the requested transaction.
	 * @throws NonExistCurrencyException A custom exception thrown when the UserList provided has the ArrayList of Users that does not contain the currency requested for conversion. 
	 * @throws BrokeBoiException A custom exception thrown when the UserList provided has the ArrayList of Users that does not have enough money for conversion of currencies. 
	 */
	public static void executeTransaction(UserList userListArg) throws StreamReadException, DatabindException, IOException, NonExistUserException, NonExistCurrencyException, BrokeBoiException {
		
		//we will be using the currency list provided in the JSON file later 
		CurrencyList cl = new CurrencyList();
		cl.setAllListFromJSON();
		ArrayList<Currency> currencyList = cl.getCurrListFromJSON();
		//it is easier to fetch the rate and inverse rate from hashmaps later in the calculation
		HashMap<String,Double> rateList = cl.getRateListFromJSON();
		HashMap<String,Double> inverseRateList = cl.getInverseRateListFromJSON();
		
		//we will also be using the user list provided in the JSON file later 
		ArrayList<User> userList = userListArg.getUserList();
		//we will be using the list of names as part of defensive coding later 
		ArrayList<String> usersNames = new ArrayList<String>();
		for (User i : userList) {
			usersNames.add(i.getName());
		}
		
		FileReader fileReader = new FileReader("./src/main/resources/transactions.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		//Will be using this to throw exceptions later. I do not want to throw and catch exceptions within the loop as that is messy. 
		//The loop will be skipping transactions not meant to be done without any exceptions thrown anyways.
		boolean allUserExist = true; 
		boolean allCurrExist = true;
		boolean notABrokie = true; 
		
		//Each line is 1 transaction and we will be working 1 transaction at a time/loop.
		while (bufferedReader.ready()) {
			String instructions = bufferedReader.readLine();
			
			//The transaction is a string delimited by spaces.
			String[] instructionsSplit = instructions.split(" ");
			
			//Defensive coding - if the transaction provides an unknown name, we will continue.
			if (!usersNames.contains(instructionsSplit[0])) {
				allUserExist = false;
				logger.warn(instructionsSplit[0] + " is not a user!");
				continue;
			}
			
			//For each transaction we will be shuffling through the list of users to find the appropriate user. 
			for ( User i : userList ) {
				if (i.getName().equals(instructionsSplit[0])) {
					
					//Once found, we will look through his wallet, which we have set as a hashmap.
					HashMap<String, Double> walletContents = i.getWallet();
		
					//I will be using this later because I am not supposed to 'put' hashmap key and values while iterating through a hashmap. 
					//It's part of defensive coding right? 
					boolean currNotExist = false;
					Double gainMoneyOutLoop = 0.0;
					
					//Obtaining the currency code in the wallet.
					Set<String> listCurrInWallet = walletContents.keySet();
					
					//If the currency is not found - we will log it
					if (!listCurrInWallet.contains(instructionsSplit[1])) {
						logger.warn(i.getName() + " does not have the currency : " + instructionsSplit[1]);
						allCurrExist = false;
					}
					
					//We will be iterating through the set's key and values. 
					for (Entry<String, Double> set : walletContents.entrySet()) {

						//If the person has a matching currency but does not have enough money - we log it
						if (set.getKey().equals(instructionsSplit[1]) && set.getValue() < Double.parseDouble(instructionsSplit[3]) ) {
							logger.warn(i.getName() + " does not have enough " + instructionsSplit[1] + " money to convert the requested amount");
							notABrokie = false;
						}
						
						//Defensive coding - we will only execute this code if the person has a currency for it and enough money for it.
						if (set.getKey().equals(instructionsSplit[1]) && set.getValue() >= Double.parseDouble(instructionsSplit[3]) ) {

							//take away the money FROM first.
							Double lossMoney = set.getValue() - Double.parseDouble(instructionsSplit[3]);
							logger.trace(instructionsSplit[3] + " of " + instructionsSplit[1] + " currency subtracted from " + i.getName());
							walletContents.put(set.getKey(),lossMoney);
							logger.trace(i.getName() + " is left with " + lossMoney.toString() + " " + instructionsSplit[1]);
							
							//calculate how much is it worth converted
							Double usd = Double.parseDouble(instructionsSplit[3]) * inverseRateList.get(instructionsSplit[1]);
							Double gainMoney = usd * rateList.get(instructionsSplit[2]);
							logger.trace(instructionsSplit[3] + " " + instructionsSplit[1] + " is " + usd.toString() + " usd which is " + gainMoney.toString() + " " + instructionsSplit[2]);
							
							//Defensive coding - only deposit it within this iteration if we can find the currency TO.
							if (listCurrInWallet.contains(instructionsSplit[2])) {
								walletContents.put(instructionsSplit[2], walletContents.get(instructionsSplit[2])+gainMoney);
								logger.trace(gainMoney.toString() + " added for " + i.getName() + " and he now has " + walletContents.get(instructionsSplit[2]).toString() + " " + instructionsSplit[2]);
							} else {
								currNotExist = true;
								gainMoneyOutLoop = gainMoney;
							}
						}
					}
					//Defensive coding - if it is not found in the currency TO, we deposit it outside the loop to avoid Exception. 
					if (currNotExist == true) {
						walletContents.put(instructionsSplit[2], gainMoneyOutLoop);
						logger.info(i.getName() + " did not start with any " + instructionsSplit[2] + " money - adding " + gainMoneyOutLoop.toString() + " " + instructionsSplit[2]);
					}
				}
			}
			
		}
		
		//After all transactions complete, I throw the error. We will be catching it in the main method anyway. 
		if (allUserExist == false) {
			logger.fatal("Throwing error because some users do not exist");
			throw new NonExistUserException("Some individuals is/are not users of the system - the rest of the transactions were honoured, please.");
		}
		
		if (allCurrExist == false) {
			logger.fatal("Throwing error because some users do not possess the currency to convert");
			throw new NonExistCurrencyException("Some individuals do not possess currency requested to be converted - the rest of the transactions were honoured, please.");
		}
		
		if (notABrokie == false) {
			logger.fatal("Throwing error because some users do not possess enough money to convert");
			throw new BrokeBoiException("Some individuals do not have enough money to carry conversion requested - the rest of the transactions were honoured, please");
		}
		
	}
	
	/**
	 * Takes in the UserList after transaction and transcribes this to JSON format in the userspostconvert.json file. 
	 * @param userListArg The UserList class.
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public static void updateUserFile(UserList userListArg) throws StreamWriteException, DatabindException, IOException {
		
		//creates a new file to store users after conversion.
		File file = new File("./src/main/resources/userspostconvert.json");
		
		ObjectMapper mapper = new ObjectMapper();
		//enables it to be written in a nicer format.
		ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
		
		//Calling the getter for the list of users.
		ArrayList<User> userListJSON = userListArg.getUserList();
		
		//writing to the file.
		writer.writeValue(file, userListJSON);
	}
}
