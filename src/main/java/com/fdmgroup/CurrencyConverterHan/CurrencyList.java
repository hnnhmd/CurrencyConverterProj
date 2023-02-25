package com.fdmgroup.CurrencyConverterHan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * CurrencyList class has the attribute ArrayList of Currency obtained from the fx_rates.json file and also contains the HashMap rateList and inverseRateList, both being used in the calculation for conversion.
 * @author Han
 */
public class CurrencyList {
	
	/**
	 * The ArrayList of Currency from the fx_rates.json file.
	 */
	private ArrayList<Currency> currList = new ArrayList<Currency>();
	
	/**
	 * The Rate List, containing the Code of the currency and the Rate. 
	 */
	private HashMap<String,Double> rateList = new HashMap<String,Double>();
	
	/**
	 * The Inverse Rate List, containing the Code of the currency and the Inverse Rate.
	 */
	private HashMap<String,Double> inverseRateList = new HashMap<String,Double>();
	
	/**
	 * Sets the ArrayList of Currency, by mapping the fx_rates.json file. Additionally also sets the Rate List and Inverse Rate List. 
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public void setAllListFromJSON() throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		//Reads the JSON file into a string because it's hard to work with in that format - we want it to look like an array of currencies
		FileReader fr = new FileReader("./src/main/resources/fx_rates.json");
		BufferedReader br = new BufferedReader(fr);
		
		//It's just a single line anyway
		String currListInterim = br.readLine();
		//Outside brackets need to be square for it to look like an array
		currListInterim = currListInterim.replaceFirst("\\{", "\\[");
		currListInterim = currListInterim.substring(0,currListInterim.length()-1) + "]";
		//Using regex to remove each currency code which is each currency's 'name', so that it would just be an array of currencies.
		currListInterim = currListInterim.replaceAll("[\"][a-z][a-z][a-z][\"][\\:]", "");
		
		//Now can read
		Currency[] currListActual = mapper.readValue(currListInterim, Currency[].class);
		
		
		ArrayList<Currency> currArrayList = new ArrayList<Currency>();
		HashMap<String,Double> rateList = new HashMap<String,Double>();
		HashMap<String,Double> inverseRateList = new HashMap<String,Double>();
		
		for (Currency i : currListActual) {
			//List of currencies as ArrayList because easier to work with
			currArrayList.add(i);
			//List of currencies in Hashmap format for rate
			rateList.put(i.getCode().toLowerCase(),i.getRate());
			//List of currencies in Hashmap format for inverse rate
			inverseRateList.put(i.getCode().toLowerCase(),i.getInverseRate());
		}
		this.currList = currArrayList;
		
		//it's relative to usd so it won't have usd in the list, but some of them users may want to use usd.
		rateList.put("usd", 1.0);
		this.rateList = rateList;
		
		//it's relative to usd so it won't have usd in the list, but some of them users may want to use usd.
		inverseRateList.put("usd", 1.0);
		this.inverseRateList = inverseRateList;
	}
	
	/**
	 * Getter for ArrayList of Currency.
	 * @return ArrayList of Currency.
	 */
	public ArrayList<Currency> getCurrListFromJSON() {
		return this.currList;
	}
	
	/**
	 * Getter for Rate List.
	 * @return Rate List.
	 */
	public HashMap<String,Double> getRateListFromJSON() {
		return this.rateList;
	}
	
	/**
	 * Getter for Inverse Rate List.
	 * @return Inverse Rate List.
	 */
	public HashMap<String,Double> getInverseRateListFromJSON() {
		return this.inverseRateList;
	}
	

}
