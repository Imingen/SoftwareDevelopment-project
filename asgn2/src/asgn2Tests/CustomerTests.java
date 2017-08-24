package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Customers.Customer;
import asgn2Customers.DriverDeliveryCustomer;
import asgn2Customers.DroneDeliveryCustomer;
import asgn2Customers.PickUpCustomer;
import asgn2Exceptions.CustomerException;


/**
 * A class that tests the that tests the asgn2Customers.PickUpCustomer, asgn2Customers.DriverDeliveryCustomer,
 * asgn2Customers.DroneDeliveryCustomer classes. Note that an instance of asgn2Customers.DriverDeliveryCustomer 
 * should be used to test the functionality of the  asgn2Customers.Customer abstract class. 
 * 
 * @author n9884076 Marius Steller Imingen
 * 
 *
 */
public class CustomerTests {
	private static final String DRIVERNAME = "Dwayne Johnson";
	private static final String DRONENAME = "John Cena";
	private static final String PICKUPNAME = "Mjaasabjunn";
	
	private static final String DRIVERPHONENUMBER = "0123782932";
	private static final String DRONEPHONENUMBER = "0321753991";
	private static final String PICKUPPHONENUMBER = "0008356177";
	
	private static final String NAME = "Hina pau havven";
	private static final String BLANKNAME = "  ";
	private static final String TOOLONGNAME = "Professor Albus Percival Wulfric Brian Dumbledore";
	private static final String PHONENUMBER = "0123456789";
	private static final String INVALIDPHONENUMBER = "1234567899";
	private static final String TOOSHORTPHONENUMBER = "1234";
	private static final String PHONENUMBER_TOO_LONG = "1234567891011";
	private static final int X = 4;
	private static final int Y = 3;
	private static final int NEG_X = -4;
	private static final int NEG_Y = -3;
	private static final int LONGX = 17;
	private static final int LONGY = 13;
	private static final int LONGNEGX = -14;
	private static final int LONGNEGY = -15;
	private static final int ZERO_X = 0;
	private static final int ZERO_Y = 0;
	
	private static final int DRONEX = 7;
	private static final int DRONEY = 5;
	private static final int DRIVERX = -2;
	private static final int DRIVERY = -4;
	private static final int PICKUPX = 0;
	private static final int PICKUPY = 0;
	
	private static final double DRONEDELIVERYDISTANCE = Math.sqrt((Math.pow(DRONEX, 2)) + Math.pow(DRONEY, 2));
	private static final double DRIVERDELIVERYDISTACE = 6;
	private static final double PICKUPDELIVERYDISTANCE = 0.0;	
	
	private static final String DRIVERTYPE = "Driver Delivery";
	private static final String DRONETYPE = "Drone Delivery";
	private static final String PICKUPTYPE = "Pick Up Customer";
	
	DriverDeliveryCustomer driver;
	DroneDeliveryCustomer drone;
	PickUpCustomer pickup;
	DriverDeliveryCustomer d1;
	DroneDeliveryCustomer drone1;
	PickUpCustomer pickup1;

	@Before
	public void setUpTest() throws CustomerException{
		driver = new DriverDeliveryCustomer(DRIVERNAME, DRIVERPHONENUMBER, DRIVERX, DRIVERY);
		drone = new DroneDeliveryCustomer(DRONENAME, DRONEPHONENUMBER, DRONEX, DRONEY);
		pickup = new PickUpCustomer(PICKUPNAME, PICKUPPHONENUMBER, PICKUPX, PICKUPY);
	}
	
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithBlankName() throws CustomerException{
		d1 = new DriverDeliveryCustomer(BLANKNAME, PHONENUMBER, X, Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithTooLongName() throws CustomerException{
		d1 = new DriverDeliveryCustomer(TOOLONGNAME, PHONENUMBER, X, Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithInvalidPhoneNumber() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, INVALIDPHONENUMBER, X, Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithTooLongPhoneNumber() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, PHONENUMBER_TOO_LONG, X, Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithTooShortPhoneNumber() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, TOOSHORTPHONENUMBER, X, Y);
	}
	

	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithLongNegativeCoordinates() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, PHONENUMBER, LONGNEGX, LONGNEGY);
	}
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithLongXCoordinate() throws CustomerException{
		drone1 = new DroneDeliveryCustomer(NAME, PHONENUMBER, LONGX, Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testCreateCustomerWithLongYCoordinate() throws CustomerException{
		drone1 = new DroneDeliveryCustomer(NAME, PHONENUMBER, X, LONGY);
	}
	
	@Test (expected = CustomerException.class)
	public void testDroneDeliveryAtZeroCoordinates() throws CustomerException{
		drone1 = new DroneDeliveryCustomer(NAME, PHONENUMBER, ZERO_X, ZERO_Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testDriverDelivertAtZeroCoordinates() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, PHONENUMBER, ZERO_X, ZERO_Y);
	}
	
	@Test (expected = CustomerException.class)
	public void testPicupAtCoordinatesThatAreNotZero() throws CustomerException{
		pickup1 = new PickUpCustomer(NAME, PHONENUMBER, DRIVERX, DRIVERY);
	}
	
	@Test
	public void testGetName(){
		assertEquals(DRIVERNAME, driver.getName());
	}
	
	@Test
	public void testGetMobileNumber(){
		assertEquals(PICKUPPHONENUMBER, pickup.getMobileNumber());
	}
	
	@Test 
	public void testGetDriverCustomerType(){
		assertEquals(DRIVERTYPE, driver.getCustomerType());
	}
	
	@Test 
	public void testGetDroneCustomerType(){
		assertEquals(DRONETYPE, drone.getCustomerType());
	}
	
	@Test
	public void testGetPickUpCustomerType(){
		assertEquals(PICKUPTYPE, pickup.getCustomerType());
	}
	
	@Test 
	public void testGetLocationX(){
		assertEquals(PICKUPX, pickup.getLocationX());
	}
	
	@Test 
	public void testGetLocationY(){
		assertEquals(DRONEY, drone.getLocationY());
	}
	
	@Test
	public void testGetNegativeLocationX() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, PHONENUMBER, NEG_X, Y);
		assertEquals(NEG_X, d1.getLocationX());
	}
	
	@Test
	public void testGetNegeativeLocationY() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, PHONENUMBER, X, NEG_Y);
		assertEquals(NEG_Y, d1.getLocationY());
	}
	

	public void testPickUpGetDeliveryDistance(){
		assertEquals(PICKUPDELIVERYDISTANCE, pickup.getDeliveryDistance(), 0);
	}
	
	@Test 
	public void testDroneGetDeliveryDistance(){
		assertEquals(DRONEDELIVERYDISTANCE, drone.getDeliveryDistance(), 0);
	}
	
	@Test 
	public void testDriverGetDeliveryDistance(){
		assertEquals(DRIVERDELIVERYDISTACE, driver.getDeliveryDistance(), 0);
	}
	
	@Test
	public void testGetDroneDeliveryDistanceEqualForNegativeNumbers() throws CustomerException{
		d1 = new DriverDeliveryCustomer(NAME, PHONENUMBER, X, Y);
		DriverDeliveryCustomer d2 = new DriverDeliveryCustomer(NAME, PHONENUMBER, NEG_X, NEG_Y);
		assertEquals(d1.getDeliveryDistance(), d2.getDeliveryDistance(), 0);
	}
	
	@Test 
	public void testDriverIsInstanceofCustomer(){
		assertTrue(driver instanceof Customer);
	}
	
	@Test
	public void testDriverIsInstancesOfDriver(){
		assertTrue(driver instanceof DriverDeliveryCustomer);
	}
	
	@Test 
	public void testDroneIsInstanceOfCustomer(){
		assertTrue(drone instanceof Customer);
	}
	
	@Test
	public void testDroneIsInstanceOfDrone(){
		assertTrue(drone instanceof DroneDeliveryCustomer);
	}
	
	@Test 
	public void testPickUpIsInstanceOfCustomer(){
		assertTrue(pickup instanceof Customer);
	}
	
	@Test
	public void testPickupIsInstanceOfPickup(){
		assertTrue(pickup instanceof PickUpCustomer);
	}
}

























