package asgn2Tests;

import static org.junit.Assert.*;

import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.PizzaException;
import asgn2Pizzas.MargheritaPizza;
import asgn2Pizzas.MeatLoversPizza;
import asgn2Pizzas.Pizza;
import asgn2Pizzas.PizzaTopping;
import asgn2Pizzas.VegetarianPizza;


/**
 * A class that that tests the asgn2Pizzas.MargheritaPizza, asgn2Pizzas.VegetarianPizza, asgn2Pizzas.MeatLoversPizza classes. 
 * Note that an instance of asgn2Pizzas.MeatLoversPizza should be used to test the functionality of the 
 * asgn2Pizzas.Pizza abstract class. 
 * 
 * @author n9884050 Glenn Arne Ebol Christensen
 *
 */
public class PizzaTests {
	MeatLoversPizza pizza;
	MeatLoversPizza pizza2;
	MeatLoversPizza meatLoversPizza;
	MargheritaPizza margheritaPizza; 
	VegetarianPizza vegetarianPizza;
	
	private static final int NEGATIVE_TWO_PIZZAS = -2;
	private static final int ZERO_PIZZAS = 0;
	private static final int ONE_PIZZA = 1;
	private static final int TWO_PIZZAS = 2;
	private static final int THREE_PIZZAS = 3;
	private static final int FOUR_PIZZAS = 4;
	private static final int FIVE_PIZZAS = 5;
	private static final int SIX_PIZZAS = 6;
	private static final int ONE_MORE_MAX = 11;
	
	private static final String EMPTY_STRING = "";
	
	private static final LocalTime SIX_PM = LocalTime.of(18, 0);
	private static final LocalTime SEVEN_PM = LocalTime.of(19, 0);
	private static final LocalTime NINE_PM = LocalTime.of(21, 0);
	private static final LocalTime FIVE_PAST_NINE_PM = LocalTime.of(21, 5);
	private static final LocalTime TEN_PM = LocalTime.of(22, 0);
	private static final LocalTime ELLEVEN_PM = LocalTime.of(23, 0);
	private static final LocalTime ELLEVEN_THIRTY_PM = LocalTime.of(23, 30);
	private static final LocalTime TWELVE_AM = LocalTime.of(23, 59);
	
	double expectedPrice;
	double expectedCost;
	double expectedValue;
	// Used when using asserEquals for double variables. So we dont get @SuppressWarnings("deprecation") warning
	double epsilon;
	
	// Used to get epsilon of given values. Makes it more efficient making test for cost
	private double getEpsilonOfValues(double value, double value2){
		return Math.abs(value - value);
	}
	
	@Before
	public void SetupTest() throws PizzaException{
		meatLoversPizza = new MeatLoversPizza(ONE_PIZZA, NINE_PM, TEN_PM);
		margheritaPizza = new MargheritaPizza(TWO_PIZZAS, NINE_PM, TEN_PM);
		vegetarianPizza = new VegetarianPizza(THREE_PIZZAS, NINE_PM, TEN_PM);
	}
	
	@Test (expected = PizzaException.class)
	public void OrderMoreThanTenPizzas() throws PizzaException{
		pizza = new MeatLoversPizza(ONE_MORE_MAX, NINE_PM, TEN_PM);
	}
	
	@Test (expected = PizzaException.class)
	public void OrderZeroPizzas() throws PizzaException{
		pizza = new MeatLoversPizza(ZERO_PIZZAS, NINE_PM, TEN_PM);
	}
	
	@Test (expected = PizzaException.class)
	public void OrderNegativePizzas() throws PizzaException{
		pizza = new MeatLoversPizza(NEGATIVE_TWO_PIZZAS, NINE_PM, TEN_PM);
	}
	
	@Test (expected = PizzaException.class)
	public void OrderBeforeOpening() throws PizzaException{
		pizza = new MeatLoversPizza(ONE_PIZZA, SIX_PM, SEVEN_PM);
	}
	
	@Test (expected = PizzaException.class)
	public void OrderAfterClosed() throws PizzaException{
		pizza = new MeatLoversPizza(ONE_PIZZA, ELLEVEN_THIRTY_PM, TWELVE_AM);
	}
	
	@Test
	public void OrderLastPossibleTime() throws PizzaException{
		pizza = new MeatLoversPizza(ONE_PIZZA, ELLEVEN_PM, TWELVE_AM);
	}
	
	@Test
	public void CheckIfMargheritaIsInstaceOfCorrectClass(){
		assertTrue(margheritaPizza instanceof MargheritaPizza);
	}
	
	@Test
	public void CheckIfMeatLoversIsInstaceOfCorrectClass(){
		assertTrue(meatLoversPizza instanceof MeatLoversPizza);
	}
	
	@Test
	public void CheckIfVegetarianIsInstaceOfCorrectClass(){
		assertTrue(vegetarianPizza instanceof VegetarianPizza);
	}
	
	@Test
	public void CheckIfMeatLoversIsInstaceOfPizza(){
		assertTrue(meatLoversPizza instanceof Pizza);
	}
	
	@Test
	public void CheckIfMargheritaIsInstaceOfPizza(){
		assertTrue(margheritaPizza instanceof Pizza);
	}
	
	@Test
	public void CheckIfVegetarianInstaceOfPizza(){
		assertTrue(vegetarianPizza instanceof Pizza);
	}
	
	@Test (expected = PizzaException.class)
	public void DeliverTimeBeforePizzaCanBeMade() throws PizzaException{
		pizza = new MeatLoversPizza(SIX_PIZZAS, NINE_PM, FIVE_PAST_NINE_PM);
	}
	
	@Test
	public void CheckCalculateCostForMargherita(){
		margheritaPizza.calculateCostPerPizza();
		
		expectedCost = 3;
		epsilon = getEpsilonOfValues(expectedCost, margheritaPizza.getCostPerPizza());
		assertEquals(expectedCost, margheritaPizza.getCostPerPizza(), epsilon);
	}
	
	@Test
	public void CheckCalculateCostForMeatLovers(){
		meatLoversPizza.calculateCostPerPizza();
		
		expectedCost = 10;
		epsilon = getEpsilonOfValues(expectedCost, meatLoversPizza.getCostPerPizza());
		assertEquals(expectedCost, meatLoversPizza.getCostPerPizza(), epsilon);
	}
	
	@Test
	public void CheckCalculateCostForVegetarian(){
		vegetarianPizza.calculateCostPerPizza();
		
		expectedCost = 11;
		epsilon = getEpsilonOfValues(expectedCost, vegetarianPizza.getCostPerPizza());
		assertEquals(expectedCost, vegetarianPizza.getCostPerPizza(), epsilon);
	}
	
	
	@Test
	public void CheckVegetarianCostIsCorrect(){
		expectedCost = 5.5;
		epsilon = getEpsilonOfValues(expectedPrice, vegetarianPizza.getCostPerPizza());
		assertEquals(expectedCost, vegetarianPizza.getCostPerPizza(), epsilon);
	}
	
	@Test
	public void CheckMargheritaCostIsCorrect(){
		expectedCost = 1.5;
		epsilon = getEpsilonOfValues(expectedPrice, margheritaPizza.getCostPerPizza());
		assertEquals(expectedCost, margheritaPizza.getCostPerPizza(), epsilon);
	}
	
	@Test
	public void CheckMeatLoversCostIsCorrect(){
		expectedCost = 5;
		epsilon = getEpsilonOfValues(expectedPrice, meatLoversPizza.getCostPerPizza());
		assertEquals(expectedCost, meatLoversPizza.getCostPerPizza(), epsilon);
	}

	
	@Test
	public void CheckMeatLoversPriceIsCorrect(){
		expectedPrice = 12;
		epsilon = getEpsilonOfValues(expectedPrice, meatLoversPizza.getPricePerPizza());
		assertEquals(expectedPrice, meatLoversPizza.getPricePerPizza(), epsilon);
	}
	
	@Test
	public void CheckMargheritaPriceIsCorrect(){
		expectedPrice = 8;
		epsilon = getEpsilonOfValues(expectedPrice, margheritaPizza.getPricePerPizza());
		assertEquals(expectedPrice, margheritaPizza.getPricePerPizza(), epsilon);
	}
	
	@Test
	public void CheckVegetarianPizzaIsCorrect(){
		expectedPrice = 10;
		epsilon = getEpsilonOfValues(expectedPrice, vegetarianPizza.getPricePerPizza());
		assertEquals(expectedPrice, vegetarianPizza.getPricePerPizza(), epsilon);
	}
	
	
	@Test
	public void OrderThreePizzaGetExpectedCost() throws PizzaException{
		pizza = new MeatLoversPizza(THREE_PIZZAS, NINE_PM, TEN_PM);
		expectedCost = 15;
		epsilon = getEpsilonOfValues(expectedPrice, pizza.getOrderCost());
		assertEquals(expectedCost, pizza.getOrderCost(), epsilon);
	}
	
	@Test
	public void OrderTwoMargharitaGetExpectedCost() throws PizzaException{
		expectedCost = 3;
		epsilon = getEpsilonOfValues(expectedPrice, margheritaPizza.getOrderCost());
		assertEquals(expectedCost, margheritaPizza.getOrderCost(), epsilon);
		
	}
	
	
	@Test
	public void CheckIfOrderPriceMatches() throws PizzaException{
		pizza = new MeatLoversPizza(FOUR_PIZZAS, NINE_PM, TEN_PM);
		expectedPrice = 48;
		epsilon = getEpsilonOfValues(expectedPrice, pizza.getOrderPrice());
		assertEquals(expectedPrice, pizza.getOrderPrice(), epsilon);
		
	}
	
	@Test
	public void CheckIfOrderProfitMatches(){
		expectedValue = 30 - 16.5; // 13.5
		epsilon = getEpsilonOfValues(expectedPrice, vegetarianPizza.getOrderProfit());
		assertEquals(expectedValue, vegetarianPizza.getOrderProfit(), epsilon);
	}
	
	@Test
	public void CheckMargerithaContainsTomato(){
		assertTrue(margheritaPizza.containsTopping(PizzaTopping.TOMATO));
	}
	
	@Test
	public void CheckMargerithaContainsCheese(){
		assertTrue(margheritaPizza.containsTopping(PizzaTopping.CHEESE));
	}
	
	@Test
	public void CheckMargeritaNotContainsBacon(){
		assertFalse(margheritaPizza.containsTopping(PizzaTopping.BACON));
	}
	
	@Test
	public void CheckMargheritaNotContainsSalami(){
		assertFalse(margheritaPizza.containsTopping(PizzaTopping.SALAMI));
	}
	
	@Test
	public void CheckMargheritNotContainsPepperoni(){
		assertFalse(margheritaPizza.containsTopping(PizzaTopping.PEPPERONI));
	}
	
	@Test
	public void CheckMargheritaNotContainsCapsicum(){
		assertFalse(margheritaPizza.containsTopping(PizzaTopping.CAPSICUM));
	}
	
	@Test
	public void CheckMargheritaNotContainsMushroom(){
		assertFalse(margheritaPizza.containsTopping(PizzaTopping.MUSHROOM));
	}
	
	@Test
	public void CheckMargheritaNotContainsEggplant(){
		assertFalse(margheritaPizza.containsTopping(PizzaTopping.EGGPLANT));
	}
	
	@Test
	public void CheckMeatLoversContainsTomato(){
		assertTrue(meatLoversPizza.containsTopping(PizzaTopping.TOMATO));
	}
	
	@Test
	public void CheckMeatLoversContainsCheese(){
		assertTrue(meatLoversPizza.containsTopping(PizzaTopping.CHEESE));
	}
	
	@Test
	public void CheckMeatLoversContainsBacon(){
		assertTrue(meatLoversPizza.containsTopping(PizzaTopping.BACON));
	}
	
	@Test
	public void CheckMeatLoversContainsPepperoni(){
		assertTrue(meatLoversPizza.containsTopping(PizzaTopping.PEPPERONI));
	}
	
	@Test
	public void CheckMeatLoversContainsSalami(){
		assertTrue(meatLoversPizza.containsTopping(PizzaTopping.SALAMI));
	}
	
	@Test
	public void CheckMeatLoversNotContainsCapsicum(){
		assertFalse(meatLoversPizza.containsTopping(PizzaTopping.CAPSICUM));
	}
	
	@Test
	public void CheckMeatLoversNotContainsMushroom(){
		assertFalse(meatLoversPizza.containsTopping(PizzaTopping.MUSHROOM));
	}
	
	@Test
	public void CheckMeatLoversNotContainsEggplant(){
		assertFalse(meatLoversPizza.containsTopping(PizzaTopping.EGGPLANT));
	}
	
	@Test
	public void CheckVegetarianContainsTomato(){
		assertTrue(vegetarianPizza.containsTopping(PizzaTopping.TOMATO));
	}
	
	@Test
	public void CheckVegetarianContainsCheese(){
		assertTrue(vegetarianPizza.containsTopping(PizzaTopping.CHEESE));
	}
	
	@Test
	public void CheckVegetarianContainsEggplant(){
		assertTrue(vegetarianPizza.containsTopping(PizzaTopping.EGGPLANT));
	}
	
	@Test
	public void CheckVegetarianContainsMushroom(){
		assertTrue(vegetarianPizza.containsTopping(PizzaTopping.MUSHROOM));
	}
	
	@Test
	public void CheckVegetarianContainsCapsicum(){
		assertTrue(vegetarianPizza.containsTopping(PizzaTopping.CAPSICUM));
	}
	
	@Test
	public void CheckVegetarianNotContainsBacon(){
		assertFalse(vegetarianPizza.containsTopping(PizzaTopping.BACON));
	}
	
	@Test
	public void CheckVegetarianNotContainsPeperoni(){
		assertFalse(vegetarianPizza.containsTopping(PizzaTopping.PEPPERONI));
	}
	
	@Test
	public void CheckVegetarianNotContainsSalami(){
		assertFalse(vegetarianPizza.containsTopping(PizzaTopping.SALAMI));
	}
	
	@Test
	public void OrderFivePizzaCheckIfQuantityMatches() throws PizzaException{
		pizza = new MeatLoversPizza(FIVE_PIZZAS, NINE_PM, TEN_PM);
		assertEquals(FIVE_PIZZAS, pizza.getQuantity());
	}
	
	@Test
	public void CheckIfMargheritaTypeMatches(){
		assertEquals("Margherita", margheritaPizza.getPizzaType());
	}
	
	@Test
	public void CheckIfMeatLoversTypeMatches(){
		assertEquals("Meat Lovers", meatLoversPizza.getPizzaType());
	}
	
	@Test
	public void CheckIfVegetarianTypeMatches(){
		assertEquals("Vegetarian", vegetarianPizza.getPizzaType());
	}

	
	@Test
	public void CheckThatTypeDoneMatchMargheritaString(){
		assertNotEquals("Margherita", vegetarianPizza.getPizzaType());
	}
	
	@Test
	public void CheckThatTypeDoneMatchMeatLoversString(){
		assertNotEquals("Meat Lovers", margheritaPizza.getPizzaType());
	}
	
	@Test
	public void CheckThatTypeDoneMatchVegetarianString(){
		assertNotEquals("Vegetarian", margheritaPizza.getPizzaType());
	}
	
	@Test
	public void CheckEmptyStringWithVegetarianType(){
		assertNotEquals(EMPTY_STRING, vegetarianPizza.getPizzaType());
	}
	
	@Test
	public void CheckEmptyStringWithMargheritaPizza(){
		assertNotEquals(EMPTY_STRING, margheritaPizza.getPizzaType());
	}
	
	@Test
	public void CheckEmptyStringWithMeatLoversPizza(){
		assertNotEquals(EMPTY_STRING, meatLoversPizza.getPizzaType());
	}
}