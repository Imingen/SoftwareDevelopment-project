package asgn2Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import asgn2Customers.Customer;
import asgn2Customers.DroneDeliveryCustomer;
import asgn2Exceptions.CustomerException;
import asgn2Exceptions.LogHandlerException;
import asgn2Restaurant.LogHandler;

/**
 * A class that tests the methods relating to the creation of Customer objects in the asgn2Restaurant.LogHander class.
 *
 * @author n9884076 Marius Steller Imingen
 */
public class LogHandlerCustomerTests {
	
	private static final String LINE = "20:23:00,20:44:00,Riley Brown,0708426008,DNC,-2,0,PZV,2";
	private static final String LINE_MISSING_CONSTRAINT = "20:23:00,20:44:00,0708426008,DNC,-2,0,PZV,2";
	private static final String LINE_X_WRONG_TYPE = "20:23:00,20:44:00,Riley Brown,0708426008,DNC,minus two,0,PZV,2";
	private static final String LINE_Y_WRONG_TYPE = "20:23:00,20:44:00,Riley Brown,0708426008,DNC,-2,zero,PZV,2";
	private static final String LINE_SEPERATED_NOT_COMMA =  "20:23:00:20:44:00:Riley Brown:0708426008:DNC:-2:0:PZV:2";
	private static final String EMPTY_LINE = " ";
	private static final String LINE_IS_NULL = null;
	
	private static final String PATH = ".//logs/20170102.txt ";
	private static final String PATH_NO_FILE = ".//logs/suh.txt";
	private static final String PATH_TO_FILE_EMPTY_FILE =  ".//logs/empty.txt";
	private static final String PATH_TO_FILE_WRONG_EXTENSION = ".//logs/not_txt_format.xlsx";
	
	private static final int PATH_NUMBER_ELEMENTS = 10;
	private static final String PATH_LAST_NAME = "Caden Kumar";

	Customer c;
	ArrayList<Customer> customers;
	ArrayList<Customer> customerTests;

	@Before
	public void setupTests() throws CustomerException, LogHandlerException{
		c = LogHandler.createCustomer(LINE);
		customers = LogHandler.populateCustomerDataset(PATH);		
	}
	
	
	@Test (expected = LogHandlerException.class)
	public void testpopulateCustomerWithNonexistingFile() throws CustomerException, LogHandlerException{
		LogHandler.populateCustomerDataset(PATH_NO_FILE);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testpopulateCostumerWithWrongFileExtenstion() throws CustomerException, LogHandlerException{
		LogHandler.populateCustomerDataset(PATH_TO_FILE_WRONG_EXTENSION);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testpopulateCostumerWithEmptyFile() throws CustomerException, LogHandlerException{
		LogHandler.populateCustomerDataset(PATH_TO_FILE_EMPTY_FILE);
	}
	
	@Test
	public void testpopulateCostumerArrayContainsCorrectAmountOfElements(){
		assertEquals(PATH_NUMBER_ELEMENTS, customers.size());
	}
	
	public void  testpopulateCostumerArrayContainsCorrectName(){
		//this also confirms that the elements get added properly e.g last in file is last in arraylist
		assertEquals(PATH_LAST_NAME, customers.get(customers.size()-1).getName());
	}
	
	@Test (expected = LogHandlerException.class)
	public void testEmptyLine() throws CustomerException, LogHandlerException{
		LogHandler.createCustomer(EMPTY_LINE);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testLineIsNull() throws CustomerException, LogHandlerException{
		LogHandler.createCustomer(LINE_IS_NULL);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testCreateCustomerMissingConstraingInLine() throws CustomerException, LogHandlerException{
		LogHandler.createCustomer(LINE_MISSING_CONSTRAINT);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testCreateCustomerXCoordinateWrongType() throws CustomerException, LogHandlerException{
		LogHandler.createCustomer(LINE_X_WRONG_TYPE);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testCreateCustomerYCoordinateWrongType() throws CustomerException, LogHandlerException{
		LogHandler.createCustomer(LINE_Y_WRONG_TYPE);
	}
	
	@Test (expected = LogHandlerException.class)
	public void testCreateCustomerWithLineNotSeperatedByComma() throws CustomerException, LogHandlerException{
		LogHandler.createCustomer(LINE_SEPERATED_NOT_COMMA);
	}
	
	@Test
	public void testCreateCustomerInstanceofDroneDeliveryCustomer(){
		assertTrue(c instanceof DroneDeliveryCustomer);
	}
	
	@Test
	public void testCreateCustomerInstanceofCustomer(){
		assertTrue(c instanceof Customer);
	}
	
}
