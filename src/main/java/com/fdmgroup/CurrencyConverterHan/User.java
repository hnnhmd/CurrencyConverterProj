package com.fdmgroup.CurrencyConverterHan;

import java.util.HashMap;

/**
 * The User class obtains attributes from the JSON file users.json.
 * @author Han
 */
public class User {
	
	/**
	 * The Name of the User.
	 */
	private String name;
	
	/**
	 * The Wallet of the User.
	 */
	private HashMap<String, Double> wallet = new HashMap<String, Double>();
	
	/**
	 * Getter for the Name of the User.
	 * @return Name of User.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the Name of the User.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the Waller of the User.
	 * @return Wallet of User.
	 */
	public HashMap<String, Double> getWallet() {
		return this.wallet;
	}
	
	/**
	 * Setter for the Waller of the User.
	 * @param wallet
	 */
	public void setWallet(HashMap<String, Double> wallet) {
		this.wallet = wallet;
	}
}
