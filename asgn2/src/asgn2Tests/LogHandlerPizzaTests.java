package asgn2Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.LogHandlerException;
import asgn2Exceptions.PizzaException;
import asgn2Pizzas.MargheritaPizza;
import asgn2Pizzas.Pizza;
import asgn2Restaurant.LogHandler;

/** A class that tests the methods relating to the creation of Pizza objects in the asgn2Restaurant.LogHander class.
* 
* @author n9884050 Glenn Arne Ebol Christensen
* 
*/
public class LogHandlerPizzaTests {
	// TO DO	
	Pizza margherita;
	Pizza meatLovers;
	Pizza vegetarian;
	Pizza pizza;
	ArrayList<Pizza> pizzas;
	
	int expectedValue;
	
	private static final int FIVE_PIZZAS = 5;
	
	private static final String MARGHERITAPIZZA_TYPE = "Margherita";
	private static final String MEATLOVERSPIZZA_TYPE = "Meat Lovers";
	private static final String VEGETARIANPIZZA_TYPE = "Vegetarian";
	
	private static final String FILEPATH = ".//logs/20170102.txt";
	private static final String FILEPATH_INVALID = ".//logs/notAFile.txt";
	private static final String FILEPATH_EMPTYFILE = ".//logs/empty.txt";
	private static final String FILEPATH_INVALID_FILE_EXTENSION = "";
	private static final String LOGFILE_LINE = "21:17:00,21:27:00,Emma Brown,0602547760,DVC,-1,0,PZV,5";
	private static final String LOGFILE_LINE_VEGETARIAN = "19:00:00,19:20:00,Casey Jones,0123456789,DVC,5,5,PZV,2";
	private static final String LOGFILE_LINE_MARGHERITA = "20:00:00,21:00:00,Johannes Gustavsen,0987654321,DVC,5,5,PZM,4";
	private static final String LOGFILE_LINE_MEATLOVERS = "21:00:00,21:15:00,Ragnar Lodbrok,0513212343,DVC,5,5,PZL,7";
	private static final String LOGFILE_MISSING_QUANTITY = "20:00:00,21:00:00,Glenn Gustavsen,0987654321,DVC,5,5,PZM";
	private static final String LOGFILE_QUANTITY_AS_DOUBLE = "21:00:00,21:15:00,Ragnar Lodbrok,0513212343,DVC,5,5,PZL,4.21";
	private static final String LOGFILE_TIME_FORMAT_INVALID = "213:90:99,21:15:00,Ragnar Lodbrok,0513212343,DVC,5,5,PZL,4.21";
	private static final String LOGFILE_LINE_SEPRATED_WITH_SEMICOLON = "20:00:00;21:00:00;Glenn Gustavsen;0987654321;DVC;5;5;PZM";
	
	@Before
	public void SetupTest() throws PizzaException, LogHandlerException{
		margherita = LogHandler.createPizza(LOGFILE_LINE_MARGHERITA);
		meatLovers = LogHandler.createPizza(LOGFILE_LINE_MEATLOVERS);
		vegetarian = LogHandler.createPizza(LOGFILE_LINE_VEGETARIAN);
	}
	
	@Test
	public void TestAmountPizzaCreatedIsCorrect() throws PizzaException, LogHandlerException{
		pizzas = LogHandler.populatePizzaDataset(FILEPATH);
		expectedValue = 10;
		assertEquals(expectedValue, pizzas.size());
	}
	
	@Test (expected = LogHandlerException.class)
	public void CheckIfExpcetionOccursWhenMissingValue() throws PizzaException, LogHandlerException{
		pizzas = LogHandler.populatePizzaDataset(LOGFILE_MISSING_QUANTITY);
	}
	
	@Test (expected = LogHandlerException.class)
	public void UsingFilePathThatDontExist() throws PizzaException, LogHandlerException{
		pizzas = LogHandler.populatePizzaDataset(FILEPATH_INVALID);
	}
	
	@Test (expected = LogHandlerException.class)
	public void LogFileWithQuantityValueAsDouble() throws PizzaException, LogHandlerException{
		pizza = LogHandler.createPizza(LOGFILE_QUANTITY_AS_DOUBLE);
	}
	
	@Test (expected = LogHandlerException.class)
	public void WrongTimeFormatInLogFile() throws PizzaException, LogHandlerException{
		pizza = LogHandler.createPizza(LOGFILE_TIME_FORMAT_INVALID);
	}
	
	@Test (expected = LogHandlerException.class)
	public void TryWithAEmptyFile() throws PizzaException, LogHandlerException{
		pizzas = LogHandler.populatePizzaDataset(FILEPATH_EMPTYFILE);
	}
	
	@Test (expected = LogHandlerException.class)
	public void TryWithFileSeperatedWithSemiColons() throws PizzaException, LogHandlerException{
		pizzas = LogHandler.populatePizzaDataset(LOGFILE_LINE_SEPRATED_WITH_SEMICOLON);
	}
	
	@Test (expected = LogHandlerException.class)
	public void TestWithFilePathWrongExtension() throws PizzaException, LogHandlerException{
		pizzas = LogHandler.populatePizzaDataset(FILEPATH_INVALID_FILE_EXTENSION);
	}
	
	@Test
	public void TestIfInstanceOfPizza(){
		assertTrue(meatLovers instanceof Pizza);
	}
	
	@Test
	public void TestIfPizzaIsCorrectSubClass(){
		assertTrue(margherita instanceof MargheritaPizza);
	}
	
	@Test
	public void CheckIfVegetarianPizzaTypeIsCorrect(){
		assertEquals(VEGETARIANPIZZA_TYPE, vegetarian.getPizzaType());
	}
	
	@Test
	public void CheckIfMeatLoversPizzaTypeIsCorrect(){
		assertEquals(MEATLOVERSPIZZA_TYPE, meatLovers.getPizzaType());
	}
	
	@Test
	public void CheckIfMargheritaPizzaTypeIsCorrect(){
		assertEquals(MARGHERITAPIZZA_TYPE, margherita.getPizzaType());
	}
	
	@Test
	public void CheckQuantityMatches() throws PizzaException, LogHandlerException{
		pizza = LogHandler.createPizza(LOGFILE_LINE);
		assertEquals(FIVE_PIZZAS, pizza.getQuantity());
	}
}
