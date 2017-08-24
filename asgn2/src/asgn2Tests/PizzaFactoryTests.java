package asgn2Tests;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.PizzaException;
import asgn2Pizzas.MargheritaPizza;
import asgn2Pizzas.MeatLoversPizza;
import asgn2Pizzas.Pizza;
import asgn2Pizzas.PizzaFactory;
import asgn2Pizzas.VegetarianPizza;

/** 
 * A class that tests the asgn2Pizzas.PizzaFactory class.
 * 
 * @author n9884050 Glenn Arne Ebol Christensen 
 * 
 */
public class PizzaFactoryTests {
	
	Pizza margherita;
	Pizza meatLovers;
	Pizza vegetarian;
	
	Pizza pizza;
	
	private static final int ONE_PIZZA = 1;
	private static final int TWO_PIZZAS = 2;
	private static final int THREE_PIZZAS = 3;
	private static final int FIVE_PIZZAS = 5;
	
	private static final String MARGHERITA_PIZZA = "PZM";
	private static final String MEATLOVERS_PIZZA = "PZL";
	private static final String VEGETARIAN_PIZZA = "PZV";
	private static final String INVALID_PIZZACODE = "JAS";
	
	private static final LocalTime SEVEN_PM = LocalTime.of(19, 0);
	private static final LocalTime EIGHT_PM = LocalTime.of(20, 0);
	private static final LocalTime NINE_PM = LocalTime.of(21, 0);
	private static final LocalTime TEN_PM = LocalTime.of(22, 0);
	
	@Before
	public void SetupTest() throws PizzaException{
		margherita = PizzaFactory.getPizza(MARGHERITA_PIZZA, ONE_PIZZA, EIGHT_PM, NINE_PM);
		meatLovers = PizzaFactory.getPizza(MEATLOVERS_PIZZA, TWO_PIZZAS, SEVEN_PM, EIGHT_PM);
		vegetarian = PizzaFactory.getPizza(VEGETARIAN_PIZZA, THREE_PIZZAS, NINE_PM, TEN_PM);
	}
	
	@Test
	public void CheckIfMargheritaInstanceOfCorrectClass(){
		assertTrue(margherita instanceof MargheritaPizza);
	}
	
	@Test
	public void CheckIfMeatLoversInstanceOfCorrectClass(){
		assertTrue(meatLovers instanceof MeatLoversPizza);
	}
	
	@Test
	public void CheckIfVegeTarianInstanceOfCorrectClass(){
		assertTrue(vegetarian instanceof VegetarianPizza);
	}
	
	@Test
	public void CheckMargheritaInstanceOfPizza(){
		assertTrue(margherita instanceof Pizza);
	}
	
	@Test
	public void CheckMeatLoversInstanceOfPizza(){
		assertTrue(meatLovers instanceof Pizza);
	}
	
	@Test
	public void CheckVegetarianInstanceOfPizza(){
		assertTrue(vegetarian instanceof Pizza);
	}
	
	@Test (expected = PizzaException.class)
	public void CheckCanCreateObjectWithInvalidCode() throws PizzaException{
		pizza = PizzaFactory.getPizza(INVALID_PIZZACODE, FIVE_PIZZAS, SEVEN_PM, EIGHT_PM);
	}
}
