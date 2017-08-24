package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Customers.Customer;
import asgn2Customers.CustomerFactory;
import asgn2Customers.DriverDeliveryCustomer;
import asgn2Customers.DroneDeliveryCustomer;
import asgn2Customers.PickUpCustomer;
import asgn2Exceptions.CustomerException;

/**
 * A class the that tests the asgn2Customers.CustomerFactory class.
 * 
 * @author n9884076 Marius Steller Imingen
 *
 */
public class CustomerFactoryTests {

	private static final String PICKUP_COSTUMER = "PUC";
	private static final String DRONE_DELIVERY = "DNC";
	private static final String DRIVER_DELIVERY = "DVC";
	private static final String INVALID_CODE = "FFF";
	private static final String EMPTY_CODE = " ";
	
	
	private static final String DRIVERNAME = "Dwayne Johnson";
	private static final String DRONENAME = "John Cena";
	private static final String PICKUPNAME = "Mjaasabjunn";
	private static final String NAME = "Reidar Reidarsen";
	
	private static final String DRIVERPHONENUMBER = "0123782932";
	private static final String DRONEPHONENUMBER = "0321753991";
	private static final String PICKUPPHONENUMBER = "0008356177";
	private static final String PHONENUMBER = "0123456789";
	
	private static final int DRONEX = 7;
	private static final int DRONEY = 5;
	private static final int DRIVERX = -2;
	private static final int DRIVERY = -4;
	private static final int PICKUPX = 0;
	private static final int PICKUPY = 0;
	private static final int X = 5;
	private static final int Y = 8;
	
	Customer drone;
	Customer drive;
	Customer pickup;
	Customer tester;
	
	@Before 
	public void setUpTest() throws CustomerException{
		drone = CustomerFactory.getCustomer(DRONE_DELIVERY, DRONENAME, DRONEPHONENUMBER, DRONEX, DRONEY);
		drive = CustomerFactory.getCustomer(DRIVER_DELIVERY, DRIVERNAME, DRIVERPHONENUMBER, DRIVERX, DRIVERY);
		pickup = CustomerFactory.getCustomer(PICKUP_COSTUMER, PICKUPNAME, PICKUPPHONENUMBER, PICKUPX, PICKUPY);
	}
	
	
	
	@Test (expected = CustomerException.class)
	public void testGetCustomerWithInvalidCustomerCode() throws CustomerException{
		tester = CustomerFactory.getCustomer(INVALID_CODE, NAME , PHONENUMBER, X, Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testGetCustomerWithEmptyCustomerCode() throws CustomerException{
		tester = CustomerFactory.getCustomer(EMPTY_CODE, NAME, PHONENUMBER, DRONEX, DRONEY);
	}
	
	@Test (expected = CustomerException.class)
	public void testGetCustomerWithZeroCoordinatesForDrone() throws CustomerException{
		tester = CustomerFactory.getCustomer(DRONE_DELIVERY, DRONENAME, PHONENUMBER, PICKUPX, PICKUPY);
	}
	
	@Test (expected = CustomerException.class)
	public void testGetCustomerWithZeroCoordinatesForDrives() throws CustomerException{
		tester = CustomerFactory.getCustomer(DRIVER_DELIVERY, DRIVERNAME, PHONENUMBER, PICKUPX, PICKUPY);
	}
	
	@Test (expected = CustomerException.class)
	public void testGetCustomerWithNonZeroCoordinatesForPickip() throws CustomerException{
		tester = CustomerFactory.getCustomer(PICKUP_COSTUMER, PICKUPNAME, PHONENUMBER, DRIVERX, DRIVERY);
	}
	
	@Test
	public void testDroneObjectIsInstanceOfCustomer(){
		assertTrue(drone instanceof Customer);
	}
	
	@Test 
	public void testDroneObjectInstanceOfDroneDeliverCustomer(){
		assertTrue(drone instanceof DroneDeliveryCustomer);
	}
	
	@Test
	public void testDriveObjectInstanceOfCustomer(){
		assertTrue(drive instanceof Customer);
	}
	
	@Test
	public void testDriveObjectInstanceOfDriveDeliveryCustomer(){
		assertTrue(drive instanceof DriverDeliveryCustomer);
	}
	
	@Test
	public void testPickupObjectInstanceOfCustomer(){
		assertTrue(pickup instanceof Customer);
	}
	
	@Test
	public void testPickupObjectInstanceOfPickupCustomer(){
		assertTrue(pickup instanceof PickUpCustomer);
	}
	
	
	
	
	
	
}


















