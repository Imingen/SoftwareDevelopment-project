	package asgn2Pizzas;

import java.time.LocalTime;
import java.util.ArrayList;

import asgn2Exceptions.PizzaException;


/**
 * An abstract class that represents pizzas sold at the Pizza Palace restaurant. 
 * The Pizza class is used as a base class of VegetarianPizza, MargheritaPizza and MeatLoversPizza. 
 * Each of these subclasses have a different set of toppings. A description of the class's fields
 * and their constraints is provided in Section 5.1 of the Assignment Specification. 
 * 
 * @author n9884076 Marius Steller Imingen
 *
 */
public abstract class Pizza  {
	
	
	private int quantity;
	private LocalTime orderTime;
	private LocalTime deliveryTime;
	private String type;
	protected double price;
	private double cost;
	

	private static final LocalTime OPENINGHOURS = LocalTime.parse("19:00:00");
	private static final LocalTime CLOSINGHOURS = LocalTime.parse("23:00:00");
	//Used if the order time is at closinghours
	private static final LocalTime BEFORENEXTDAY = LocalTime.parse("23:59:59");
	private static final int MAXORDERQUANTITY = 10;
	private static final int MINIMUMORDERQUANTITY = 0;
	
	
	/**
	 *  This class represents a pizza produced at the Pizza Palace restaurant.  A detailed description of the class's fields
	 *  and parameters is provided in the Assignment Specification, in particular in Section 5.1. 
	 *  A PizzaException is thrown if the any of the constraints listed in Section 5.1 of the Assignment Specification
	 *  are violated. 
     *
     *  PRE: TRUE
	 *  POST: All field values except cost per pizza are set
	 * 
	 * @param quantity - The number of pizzas ordered 
	 * @param orderTime - The time that the pizza order was made and sent to the kitchen 
	 * @param deliveryTime - The time that the pizza was delivered to the customer
	 * @param type -  A human understandable description of this Pizza type
	 * @param price - The price that the pizza is sold to the customer
	 * @throws PizzaException if supplied parameters are invalid. Quantity
	 * 
	 */
	public Pizza(int quantity, LocalTime orderTime, LocalTime deliveryTime, String type, double price) throws PizzaException{
		if(quantity <= MINIMUMORDERQUANTITY){
			throw new PizzaException("Quantity cannot be 0 (zero)");
		}
		else if(quantity > MAXORDERQUANTITY){
			throw new PizzaException("Quantity exceeded maximum order capacity");
		}
		else if(orderTime.isAfter(CLOSINGHOURS)  || orderTime.isBefore(OPENINGHOURS) || (deliveryTime.isBefore(OPENINGHOURS))){
			throw new PizzaException("Pizza Palace can only take orders between 19:00 and 23:00");
		}
		else if(deliveryTime.isAfter(orderTime.plusHours(1))){
			//If the order time is at 23:00:00, pizzas will get thrown out at 23:59:59 to avoid errors while deliverytime 
			//is after 23, since 23.plushours(1) will result in 00:00 which will be next day
			if(orderTime.equals(CLOSINGHOURS)){
				if(deliveryTime.isAfter(BEFORENEXTDAY)){
					throw new PizzaException("Delivery time exceeded 1 hour from ordertime");
				}
				//Also has to check if deliverytime == ordertime here 
				//because if ordertime and deliverytime both are 23:00 else if below deosnt get triggered. 
				else if(orderTime.equals(deliveryTime)){
					throw new PizzaException("Cannot deliver pizza while its being made");
				}
			}
			else if(orderTime.isBefore(CLOSINGHOURS)){
				throw new PizzaException("Delivery time exceeded 1 hour from ordertime");
			}
		}
		else if(deliveryTime.isBefore(orderTime.plusMinutes(10))){
			throw new PizzaException("Cannot deliver pizza while its being made");
		}
		else{
			this.quantity = quantity;
			this.orderTime = orderTime;
			this.deliveryTime = deliveryTime;
			this.type = type;
			this.price = price;
			calculateCostPerPizza();
		}
	}

	/**
	 * Calculates how much a pizza would could to make calculated from its toppings.
	 *  
     * <P> PRE: TRUE
	 * <P> POST: The cost field is set to sum of the Pizzas's toppings
	 */
	public final void calculateCostPerPizza(){
		if(this.getClass().isAssignableFrom(MargheritaPizza.class)){
			cost += PizzaTopping.TOMATO.getCost(); 
			cost += PizzaTopping.CHEESE.getCost();
		}
		else if(this.getClass().isAssignableFrom(VegetarianPizza.class)){
			cost += PizzaTopping.TOMATO.getCost();	
			cost += PizzaTopping.CHEESE.getCost();
			cost += PizzaTopping.EGGPLANT.getCost();
			cost += PizzaTopping.MUSHROOM.getCost();
			cost += PizzaTopping.CAPSICUM.getCost();
		}
		else if(this.getClass().isAssignableFrom(MeatLoversPizza.class)){
			cost += PizzaTopping.TOMATO.getCost();
			cost += PizzaTopping.CHEESE.getCost();
			cost += PizzaTopping.BACON.getCost();
			cost += PizzaTopping.PEPPERONI.getCost();
			cost += PizzaTopping.SALAMI.getCost();
		}
	}
	
	/**
	 * Returns the amount that an individual pizza costs to make.
	 * @return The amount that an individual pizza costs to make.
	 */
	public final double getCostPerPizza(){
		
		return this.cost;
	}

	/**
	 * Returns the amount that an individual pizza is sold to the customer.
	 * @return The amount that an individual pizza is sold to the customer.
	 */
	public final double getPricePerPizza(){
		return this.price;
	}

	/**
	 * Returns the amount that the entire order costs to make, taking into account the type and quantity of pizzas. 
	 * @return The amount that the entire order costs to make, taking into account the type and quantity of pizzas. 
	 */
	public final double getOrderCost(){
		return cost * quantity;
	}
	
	/**
	 * Returns the amount that the entire order is sold to the customer, taking into account the type and quantity of pizzas. 
	 * @return The amount that the entire order is sold to the customer, taking into account the type and quantity of pizzas. 
	 */
	public final double getOrderPrice(){
		return price * quantity;
	}
	
	
	/**
	 * Returns the profit made by the restaurant on the order which is the order price minus the order cost. 
	 * @return  Returns the profit made by the restaurant on the order which is the order price minus the order cost.
	 */
	public final double getOrderProfit(){
		return getOrderPrice() - getOrderCost();
	}
	

	/**
	 * Indicates if the pizza contains the specified pizza topping or not. 
	 * @param topping -  A topping as specified in the enumeration PizzaTopping
	 * @return Returns  true if the instance of Pizza contains the specified topping and false otherwise.
	 */
	public final boolean containsTopping(PizzaTopping topping){
		if(this.getClass().isAssignableFrom(MargheritaPizza.class)){
			if(topping.equals(PizzaTopping.CHEESE) || topping.equals(PizzaTopping.TOMATO)){
				return true;
			}
		}
		else if(this.getClass().isAssignableFrom(VegetarianPizza.class)){
			if(topping.equals(PizzaTopping.TOMATO) || topping.equals(PizzaTopping.CHEESE) || topping.equals(PizzaTopping.EGGPLANT)
					|| topping.equals(PizzaTopping.MUSHROOM) || topping.equals(PizzaTopping.CAPSICUM)){
				return true;
			}
		}
		else if(this.getClass().isAssignableFrom(MeatLoversPizza.class)){
			if(topping.equals(PizzaTopping.TOMATO) || topping.equals(PizzaTopping.CHEESE) || topping.equals(PizzaTopping.BACON)
					|| topping.equals(PizzaTopping.PEPPERONI) || topping.equals(PizzaTopping.SALAMI)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the quantity of pizzas ordered. 
	 * @return the quantity of pizzas ordered. 
	 */
	public final int getQuantity(){
		return this.quantity;
	}

	/**
	 * Returns a human understandable description of the Pizza's type. 
	 * The valid alternatives are listed in Section 5.1 of the Assignment Specification. 
	 * @return A human understandable description of the Pizza's type.
	 */
	public final String getPizzaType(){
		return this.type;
	}


	/**
	 * Compares *this* Pizza object with an instance of an *other* Pizza object and returns true if  
	 * if the two objects are equivalent, that is, if the values exposed by public methods are equal.
	 * You do not need to test this method.
	 *  
	 * @return true if *this* Pizza object and the *other* Pizza object have the same values returned for 	
	 * getCostPerPizza(), getOrderCost(), getOrderPrice(), getOrderProfit(), getPizzaType(), getPricePerPizza() 
	 * and getQuantity().
	 *   
	 */
	@Override
	public boolean equals(Object other){
		Pizza otherPizza = (Pizza) other;
		return ((this.getCostPerPizza()) == (otherPizza.getCostPerPizza()) &&
			(this.getOrderCost()) == (otherPizza.getOrderCost())) &&				
			(this.getOrderPrice()) == (otherPizza.getOrderPrice()) &&
			(this.getOrderProfit()) == (otherPizza.getOrderProfit()) &&
			(this.getPizzaType() == (otherPizza.getPizzaType()) &&
			(this.getPricePerPizza()) == (otherPizza.getPricePerPizza()) &&
			(this.getQuantity()) == (otherPizza.getQuantity()));
	}

	
}
