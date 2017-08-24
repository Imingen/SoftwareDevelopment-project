package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.CustomerException;
import asgn2Exceptions.LogHandlerException;
import asgn2Exceptions.PizzaException;
import asgn2Restaurant.PizzaRestaurant;

/**
 * A class that that tests the methods relating to the handling of Customer objects in the asgn2Restaurant.PizzaRestaurant
 * class as well as processLog and resetDetails.
 * 
 * @author n9884076 Marius Steller Imingen
 */
public class RestaurantCustomerTests {
	
	PizzaRestaurant pr;
	
	private static final String PATH_WRONG_CUSTOMER_CODES = ".//logs/wrong_customercodes.txt";
	private static final String PATH_EMPTY_FILE = ".//logs/empty.txt";
	private static final String PATH = ".//logs/20170102.txt";
	private static final int NUM_ELEM_IN_PATH_FILE = 10 ;
	private static final double PATH_DELIVERY_DISTANCE = 41.409653895730557124491525579851;
	
	private static final String DRONE_DELIVERY = "Drone Delivery";
	
	private static final String NAME_ON_INDEX_3 = "Bella Chen";
	private static final String NUMBER_ON_INDEX_8 = "0698979160";
	private static final double DISTANCE_ON_INDEX_4 = 4.4721359549995793928183473374626 ;
	private static final int INDEX_TOO_HIGH = 17;
	
	@Before
	public void setupTests() throws CustomerException, PizzaException, LogHandlerException{
		pr = new PizzaRestaurant();
	}
	
	@Test
	public void testSuccessfullProcessLog() throws CustomerException, PizzaException, LogHandlerException{
		assertTrue(pr.processLog(PATH));
	}
	
	@Test (expected = LogHandlerException.class)
	public void testprocessLogsThrowsLogHandlerException() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH_EMPTY_FILE);
	}
	
	@Test (expected = CustomerException.class)
	public void testprocessLogsThrowsCustomerException() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH_WRONG_CUSTOMER_CODES);
	}
	
	@Test
	public void testGetCustomerByIndexDistanceIsCorrect() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(DISTANCE_ON_INDEX_4, pr.getCustomerByIndex(4).getDeliveryDistance(), 0);
	}
	
	@Test
	public void testGetCustomerByIndexCorrectNumber() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(NUMBER_ON_INDEX_8, pr.getCustomerByIndex(8).getMobileNumber());
	}
	
	@Test (expected = CustomerException.class)
	public void testGetCustomerByIndexThrowsCustomerException() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		pr.getCustomerByIndex(INDEX_TOO_HIGH);
	}
	
	@Test
	public void testGetCustomerByIndexCorrectName() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(NAME_ON_INDEX_3, pr.getCustomerByIndex(3).getName());
	}
	
	@Test
	public void testGetCustomrByIndex() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(DRONE_DELIVERY, pr.getCustomerByIndex(1).getCustomerType());
	}
	
	@Test
	public void testGetNumCustomerOrdersIsCorrect() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(NUM_ELEM_IN_PATH_FILE, pr.getNumCustomerOrders());
	}
	
	@Test
	public void testNumCustomerOrdersEqualTooPizzaOrders() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(pr.getNumCustomerOrders(), pr.getNumPizzaOrders());
	}
	
	@Test
	public void testGetTotalDeliveryDistance() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		assertEquals(PATH_DELIVERY_DISTANCE, pr.getTotalDeliveryDistance(), 0);
	}
	
	@Test
	public void testResetDetails() throws CustomerException, PizzaException, LogHandlerException{
		pr.processLog(PATH);
		int before = pr.getNumCustomerOrders();
		pr.resetDetails();
		int after = pr.getNumCustomerOrders();
		assertNotEquals(before, after);
	}
	
	
}
