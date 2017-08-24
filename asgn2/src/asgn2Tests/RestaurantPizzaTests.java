package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.CustomerException;
import asgn2Exceptions.LogHandlerException;
import asgn2Exceptions.PizzaException;
import asgn2Pizzas.Pizza;
import asgn2Restaurant.PizzaRestaurant;

/**
 * A class that tests the methods relating to the handling of Pizza objects in the asgn2Restaurant.PizzaRestaurant class as well as
 * processLog and resetDetails.
 * 
 * @author n9884050 Glenn Arne Ebol Christensen
 *
 */
public class RestaurantPizzaTests {
	PizzaRestaurant pizzas;
	PizzaRestaurant pizzaRestaurant;
	Pizza pizzaObject;
	
	private int expectedValue;
	private double expectedDoubleValue;
	private double epsilon;
	private String expectedString;
	
	private static final String FILEPATH = ".//logs/20170102.txt";
	private static final String FILEPATH2 = ".//logs/20170101.txt";
	private static final String FILEPATH_INVALID = ".//logs/notAFileHehe.txt";
	private static final String FILEPATH_INVALID_PIZZACODE = ".//logs/wrong_pizzacodes.txt";
	
	// Used to get epsilon of given values. Makes it more efficient making test for cost
	private double getEpsilonOfValues(double value, double value2){
		return Math.abs(value - value);
	}
	
	@Before
	public void SetupTest() throws CustomerException, PizzaException, LogHandlerException{
		pizzas = new PizzaRestaurant();
		pizzas.processLog(FILEPATH);
	}
	
	@Test
	public void TestProcessLogWithValidFilePath() throws CustomerException, PizzaException, LogHandlerException{
		assertTrue(pizzas.processLog(FILEPATH));
	}
	
	@Test (expected = LogHandlerException.class)
	public void TestProcessLogThatDoesntWork() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH_INVALID);
	}
	
	@Test (expected = PizzaException.class)
	public void testProcessLogThrowsPizzaException() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH_INVALID_PIZZACODE);
	}
	
	
	@Test (expected = PizzaException.class)
	public void TryGetIndexThatDontExist() throws PizzaException{
		pizzas.getPizzaByIndex(11);
	}
	
	@Test
	public void GetPizzaCheckIfItsPizzaObject() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH);
		assertTrue(pizzas.getPizzaByIndex(1) instanceof Pizza);
	}
	
	@Test
	public void GetPizzaByIndexCheckIfCorrectPizza() throws PizzaException, CustomerException, LogHandlerException{
		pizzaRestaurant = new PizzaRestaurant();
		pizzaRestaurant.processLog(FILEPATH);
		pizzaObject = pizzaRestaurant.getPizzaByIndex(0);
		expectedString = "Vegetarian";
		assertEquals(expectedString, pizzaObject.getPizzaType());
	}
	
	@Test
	public void CheckNumPizzaOrdersMatches() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH2);
		expectedValue = 3;
		assertEquals(expectedValue, pizzas.getNumPizzaOrders());
	}
	
	
	@Test
	public void CheckIfTotalProfitMatchesExpectedValue() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH2);
		expectedDoubleValue = 36.5;
		epsilon = getEpsilonOfValues(expectedDoubleValue, pizzas.getTotalProfit());
		assertEquals(expectedDoubleValue, pizzas.getTotalProfit(), epsilon);
	}
	
	@Test (expected = PizzaException.class)
	public void CheckIfMethodResetsTheDetails() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH);
		pizzas.resetDetails();
		pizzas.getPizzaByIndex(0);
	}
	
	@Test
	public void testResetDetailsWithoutException() throws CustomerException, PizzaException, LogHandlerException{
		pizzas.processLog(FILEPATH);
		int before = pizzas.getNumPizzaOrders();
		pizzas.resetDetails();
		int after = pizzas.getNumPizzaOrders();
		assertNotEquals(before, after);
	}
}
