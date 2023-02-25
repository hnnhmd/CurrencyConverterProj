package com.fdmgroup.CurrencyConverterHan;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A POJO class to store the attributes of currencies from the currency JSON file.
 * @author Han
 */
public class Currency {
	
	/**
	 * Code of the currency. This is used as the key in the HashMap for our calculation on conversion.
	 */
	private String code;
	
	/**
	 * Alpha Code of the currency.
	 */
	private String alphaCode;
	
	/**
	 * Numeric Code of the currency.
	 */
	private String numericCode;
	
	/**
	 * Name of the currency.
	 */
	private String name;
	
	/**
	 * Rate of the currency. This is used in our calculation on conversion. It is relative to USD.
	 */
	private Double rate;
	
	/**
	 * Full date of the currency.
	 */
	private String date;
	
	/**
	 * Inverse Rate of the currency. This is used in our calculation on conversion. It is relative to USD. 
	 */
	private Double inverseRate;
	
	/**
	 * Getter for the Code.
	 * @return Code of the currency.
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Setter for the Code.
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Getter for the Alpha Code.
	 * @return Alpha Code of the currency.
	 */
	public String getAlphaCode() {
		return this.alphaCode;
	}
	
	/**
	 * Setter for the Alpha Code.
	 * @param alphaCode
	 */
	public void setAlphaCode(String alphaCode) {
		this.alphaCode = alphaCode;
	}
	
	/**
	 * Getter for the Numeric Code.
	 * @return Numeric Code of the currency.
	 */
	public String getNumericCode() {
		return this.numericCode;
	}
	
	/**
	 * Setter for the Numeric Code.
	 * @param numericCode
	 */
	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}
	
	/**
	 * Getter for the Name.
	 * @return Name of the currency.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the Name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the Rate.
	 * @return Rate of the currency.
	 */
	public Double getRate() {
		return this.rate;
	}
	
	/**
	 * Setter for the Rate.
	 * @param rate
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	/**
	 * Getter for the Date.
	 * @return Full date of the currency.
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * Setter for the Date.
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Getter for the Inverse Rate.
	 * @return Inverse Rate of the currency.
	 */
	public Double getInverseRate() {
		return this.inverseRate;
	}
	
	/**
	 * Setter for the Inverse Rate.
	 * @param inverseRate
	 */
	public void setInverseRate(Double inverseRate) {
		this.inverseRate = inverseRate;
	}
}
