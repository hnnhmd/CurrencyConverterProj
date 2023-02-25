package com.fdmgroup.CurrencyConverterHan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * UserList class has ArrayList containing Users and obtains this from the JSON file users.json.
 * @author Han
 */
public class UserList {
	
	/**
	 * The ArrayList of Users from the JSON file users.json.
	 */
	private ArrayList<User> userList = new ArrayList<User>();

	
	/**
	 * Sets the ArrayList of Users from the JSON file users.json when it is called. 
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public void setUserListFromJSON() throws StreamReadException, DatabindException, IOException {
		//Read from JSON file
		ObjectMapper mapper = new ObjectMapper();
		User[] userList = mapper.readValue(new File("./src/main/resources/users.json"), User[].class);
		ArrayList<User> userArrayList = new ArrayList<User>();
		//changes it to ArrayList because its easier to work with.
		for (User i : userList) {
			userArrayList.add(i);
		}
		this.userList = userArrayList;
	}
	
	/**
	 * Getter for the ArrayList of Users.
	 * @return ArrayList of Users. 
	 */
	public ArrayList<User> getUserList() {
		return this.userList;
	}

}
