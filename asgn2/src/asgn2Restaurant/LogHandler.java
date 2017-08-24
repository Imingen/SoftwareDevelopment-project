package asgn2Restaurant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import asgn2Customers.Customer;
import asgn2Customers.CustomerFactory;
import asgn2Exceptions.CustomerException;
import asgn2Exceptions.LogHandlerException;
import asgn2Exceptions.PizzaException;
import asgn2Pizzas.Pizza;
import asgn2Pizzas.PizzaFactory;

/**
 *
 * A class that contains methods that use the information in the log file to return Pizza 
 * and Customer object - either as an individual Pizza/Customer object or as an
 * ArrayList of Pizza/Customer objects.
 * 
 * @author  n9884076 Marius Steller Imingen <br> n9884050 Glenn Arne Ebol Christensen
 *
 */
public class LogHandler {
	
	private static final int NUMBER_OF_ELEMENTS_ON_ONE_LINE = 9;
	private static final String FILE_SPLIT_SYMBOL =  ",";
	/**
	 * Returns an ArrayList of Customer objects from the information contained in the log file ordered as they appear in the log file.
	 * @param filename The file name of the log file
	 * @return an ArrayList of Customer objects from the information contained in the log file ordered as they appear in the log file. 
	 * @throws CustomerException If customercode is invalid or if any of the customer constraints are invalid. 
	 * @throws LogHandlerException If the file is empty, the file doesnt exist or when there is a general IOexception being caught
	 * 
	 */
	public static ArrayList<Customer> populateCustomerDataset(String filename) throws CustomerException, LogHandlerException{
		ArrayList<Customer> customers = new ArrayList<>();
		BufferedReader br = null;
		try {
			File file = new File(filename);
			//Exception from createCustomer() is not throw when a line is empty here, thus extra check too see if the file is empty 
			if(file.length() == 0){
				throw new LogHandlerException("Empty file");
			}
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while(line != null){
				Customer c = createCustomer(line);
				customers.add(c);
				line = br.readLine();
			}
		} 
		catch (FileNotFoundException e) {
			throw new LogHandlerException("File not found: " + e.getMessage());
		} 
		catch (IOException e) {
			//Generic IOexception, print message to get more info
			throw new LogHandlerException("IOexception - contact IT - support" + e.getMessage());
		}
		catch (NullPointerException e) {
			throw new LogHandlerException("File doesnt exist (NullPointerException)");
		}
		catch(CustomerException e){
			throw e;
		}
		//always close the file
		finally {
			try {
				br.close();
			} 
			catch (IOException e2) {
				//Generic IOexception, print message to get more info
				throw new LogHandlerException("IOexception - contact IT - support" + e2.getMessage());
			}
			catch (NullPointerException e2){
				throw new LogHandlerException("File doesnt exist " + e2.getMessage());
			}
		}
		return customers;	
	}		

	/**
	 * Returns an ArrayList of Pizza objects from the information contained in the log file ordered as they appear in the log file. .
	 * @param filename The file name of the log file
	 * @return an ArrayList of Pizza objects from the information contained in the log file ordered as they appear in the log file. .
	 * @throws PizzaException if pizzacode is invalid or any of the constraints in the file are invalid Pizza parametes
	 * @throws LogHandlerException If the file doesnt exist or the file is empty or with general IOexceptions
	 * 
	 */
	public static ArrayList<Pizza> populatePizzaDataset(String filename) throws PizzaException, LogHandlerException{
		ArrayList<Pizza> pizzas = new ArrayList<>();
		BufferedReader br = null;
		try {
			File file = new File(filename);
			if(file.length() == 0){
				throw new LogHandlerException("Empty file");
			}
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while(line != null){
				Pizza p = createPizza(line);
				pizzas.add(p);
				line = br.readLine();
			}
		} 
		catch (FileNotFoundException e) {
			throw new LogHandlerException("File not found: " + e.getMessage());
		} 
		catch (IOException e) {
			//Generic IOexception, print message to get more info
			throw new LogHandlerException("IOexception - contact IT - support" + e.getMessage());
		}
		catch (NullPointerException e) {
			throw new LogHandlerException("File doesnt exist or is empty (NullPointerException)");
		}
		catch(PizzaException e){
			throw e;
		}
		finally {
			try {
				br.close();
			} 
			catch (IOException e2) {
				//Generic IOexception, print message to get more info
				throw new LogHandlerException("IOexception - contact IT - support" + e2.getMessage());
			}
			catch (NullPointerException e2){
				throw new LogHandlerException("File doesnt exist or is empty (NullPointerException)");
			}
		}
	
		return pizzas;
	}		

	
	/**
	 * Creates a Customer object by parsing the  information contained in a single line of the log file. The format of 
	 * each line is outlined in Section 5.3 of the Assignment Specification.  
	 * @param line  A line from the log file
	 * @return A Customer object containing the information from the line in the log file
	 * @throws CustomerException If any of the constraints in the file is not valid Customer parameters.
	 * @throws LogHandlerException If the line is null or empty<br>
	 * If the line is not seperated by comma <br>
	 * If the line does not contain exactly 9 (nine) constraints, e.g 9 fields seperated by comma<br>
	 * If there is a missing constraint that gives ArrayOutOfBoundsException on the lineArry[]<br>
	 * If the location constraint doesnt sucessfully parse to an integer type<br>
	 */
	public static Customer createCustomer(String line) throws CustomerException, LogHandlerException{
		if(line == null){
			throw new LogHandlerException("line == null");
		}
		else if(line.trim().isEmpty()){
			throw new LogHandlerException("line is empty");
		}
		else if(!line.contains(FILE_SPLIT_SYMBOL)){
			throw new LogHandlerException("line is not seperated by commma");
		}
		try{
			String lineArr[] = line.split(FILE_SPLIT_SYMBOL);
			if(lineArr.length != NUMBER_OF_ELEMENTS_ON_ONE_LINE){
				throw new LogHandlerException("Number of fields on a line is wrong");
			}
			String customerName = lineArr[2];
			String mobileNumber = lineArr[3];
			int locationX = Integer.parseInt(lineArr[5]);
			int locationY = Integer.parseInt(lineArr[6]);
			String customerCode = lineArr[4];
				
			return CustomerFactory.getCustomer(customerCode, customerName, mobileNumber, locationX, locationY);
		}
		catch(NumberFormatException e){
			throw new LogHandlerException("Error trying to parse Location constraint from file");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			throw new LogHandlerException();
		}
		catch(CustomerException e){
			throw e;
		}
	}
	
	/**
	 * Creates a Pizza object by parsing the information contained in a single line of the log file. The format of 
	 * each line is outlined in Section 5.3 of the Assignment Specification.  
	 * @param line A line from the log file
	 * @return A Pizza object containing the information from the line in the log file
	 * @throws PizzaException If any of the constraints in the file is not valid Pizza  parameters.
	 * @throws LogHandlerException If the line is null or empty<br>
	 * If the line is not seperated by comma<br>
	 * If the line does not contain exactly 9 (nine) constraints, e.g 9 fields seperated by comma<br>
	 * If there is a missing constraint that gives ArrayOutOfBoundsException on the lineArry[]<br>
	 * If the quantity constraint cant be parsed to an integer<br>
	 * If the ordertime and delivery time constraints doesnt successfully parse to a LocalTime type<br>
	 */
	public static Pizza createPizza(String line) throws PizzaException, LogHandlerException{
		if(line == null){
			throw new LogHandlerException("line == null");
		}
		else if(line.trim().isEmpty()){
			throw new LogHandlerException("line is empty");
		}
		else if(!line.contains(FILE_SPLIT_SYMBOL)){
			throw new LogHandlerException("line is not seperated by comma");
		}
		try{
			String lineArr[] = line.split(FILE_SPLIT_SYMBOL);
			if(lineArr.length != NUMBER_OF_ELEMENTS_ON_ONE_LINE){
				throw new LogHandlerException("Number of fields on one line is wrong");
			}
			String pizzaCode = lineArr[7];
			int quantity = Integer.parseInt(lineArr[8]);
			LocalTime orderTime = LocalTime.parse(lineArr[0]);
			LocalTime deliveryTime = LocalTime.parse(lineArr[1]);
		
			return PizzaFactory.getPizza(pizzaCode, quantity, orderTime, deliveryTime);	
		}
		catch(NumberFormatException e){
			throw new LogHandlerException("Error trying to parse quantity constraint from file");
		}
		catch(DateTimeParseException e){
			throw new LogHandlerException("Error trying to parse order/deliverytime from file");
		}
		catch(PizzaException e){
			throw e;
		}

	}
}
