package asgn2GUIs;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.table.DefaultTableModel;

import asgn2Exceptions.CustomerException;
import asgn2Exceptions.LogHandlerException;
import asgn2Exceptions.PizzaException;
import asgn2Restaurant.PizzaRestaurant;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


/**
 * This class is the graphical user interface for the rest of the system. 
 * Currently it is a ‘dummy’ class which extends JFrame and implements Runnable and ActionLister. 
 * It should contain an instance of an asgn2Restaurant.PizzaRestaurant object which you can use to 
 * interact with the rest of the system. You may choose to implement this class as you like, including changing 
 * its class signature – as long as it  maintains its core responsibility of acting as a GUI for the rest of the system. 
 * You can also use this class and asgn2Wizards.PizzaWizard to test your system as a whole
 * 
 * 
 * @author n9884076 Marius Steller Imingen <br> n9884050 Glenn Arne Ebol Christensen
 *
 */
public class PizzaGUI extends javax.swing.JFrame implements Runnable, ActionListener {
	
	
	PizzaRestaurant restaurant;
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;
	
	private JPanel pnlBackground;
	private JPanel pnlButtonSouth;
	private JPanel pnlButton;
	private JPanel pnlCustomers;
	private JPanel pnlOrders;
	
	private JButton btnChooseFile;
	private JButton btnLoadCustomers;
	private JButton btnLoadOrders;
	private JButton btnReset;
	private JButton btnCalculateDistance;
	private JButton btnCalculateProfit;
	
	private JTable customersTable;
	private JTable ordersTable;
	
	DefaultTableModel customerModel;
	DefaultTableModel orderModel;
	
	private JLabel functionalityInfo;
	
	private JTextField calcDist;
	private JTextField calcOrder;
	
	/**
	 * Creates a new Pizza GUI with the specified title 
	 * 
	 * @param title - The title for the supertype JFrame
	 */
	public PizzaGUI(String title) {
		super(title);
	}
	
	@Override
	public void run() {
		restaurant = new PizzaRestaurant();
		
		createGUI();
	}
	
	/**
	 * 	Handles all the actions performed when a user presses a button in the GUI.
	 * 
	 *  @param e - The action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == btnChooseFile){
				try {		
					boolean success = getFilePath();
					if(success == true){
						isFileLoaded(true); // Enable buttons when file is loaded
						setFunctionalityText("File Selected");
					}
					else{
						isFileLoaded(false);
					}
					
				} catch (CustomerException | LogHandlerException | PizzaException e1) {
					isFileLoaded(false);
					JOptionPane.showMessageDialog(pnlBackground, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
		}
		else if(src == btnLoadCustomers){
			if(customerModel.getRowCount() > 0){
				customerModel.setRowCount(0);
				displayCustomers();
				setFunctionalityText("Customers Loaded");
			}
			else{
				displayCustomers();
				btnCalculateDistance.setEnabled(true);
				setFunctionalityText("Customers Loaded");
			}
		}
		else if(src == btnLoadOrders){
			if(orderModel.getRowCount() > 0){
				orderModel.setRowCount(0);
				displayOrders();
				setFunctionalityText("Orders Loaded");
			}
			else{
				displayOrders();
				btnCalculateProfit.setEnabled(true);
				setFunctionalityText("Orders Loaded");
			}
		}
		else if(src == btnReset){
			resetDetails();
			setFunctionalityText("All Data Resetted");
		}
		else if(src == btnCalculateProfit){
			calcProfit();
			setFunctionalityText("Total Profit Calculated");
		}
		else if(src == btnCalculateDistance){
			calcDistance();
			setFunctionalityText("Total Distance Calculated");
		}
	}
	
	/**
	 * Gets the absolute file path of the file chosen by the user
	 * Display's a message dialog with a exception message dependent on which type exception was catched.
	 * 
	 * @return true if a file was successfully chosen, false otherwise.
	 * @throws CustomerException - If the log file contains semantic errors leading that violate the customer constraints listed in Section 5.3 of the Assignment Specification or contain an invalid customer code (passed by another class).
	 * @throws LogHandlerException - If there was a problem parsing the line from the log file.
	 * @throws PizzaException - If the log file contains semantic errors leading that violate the pizza constraints listed in Section 5.3 of the Assignment Specification or contain an invalid pizza code (passed by another class).
	 */
	private boolean getFilePath() throws CustomerException, LogHandlerException, PizzaException{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File(".//logs/"));
		int value = fc.showOpenDialog(this);
		// If user press cancel button
		if(value != JFileChooser.APPROVE_OPTION){
			JOptionPane.showMessageDialog(pnlBackground, "No File chosen!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else{
			File f = fc.getSelectedFile();
			String fileName  = f.getAbsolutePath();
			try {
				restaurant.processLog(fileName);
			} catch (CustomerException e) {
				JOptionPane.showMessageDialog(pnlBackground, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (PizzaException e) {
				JOptionPane.showMessageDialog(pnlBackground, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			} catch (LogHandlerException e) {
				JOptionPane.showMessageDialog(pnlBackground, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
			return true;
		}
	}
	
	/**
	 * Display's the total profit on calcOrder JTextField
	 */
	private void calcProfit(){
		String profit = String.format("%.2f", restaurant.getTotalProfit());
		calcOrder.setText(profit);
	}
	
	/**
	 * Display's the total delivery distance on calcDist JTextField
	 */
	private void calcDistance(){
		String distance = String.format("%.2f", restaurant.getTotalDeliveryDistance());
		calcDist.setText(String.format(distance));
	}
	
	/**
	 * Display's all the customer's in the customersTable JTable.
	 * Display's a message dialog if a CustomerException occurs.
	 */
	private void displayCustomers(){
		int amountCustomers = restaurant.getNumCustomerOrders();
		
		for(int i = 0; i < amountCustomers; i++){
			try {
				String name = restaurant.getCustomerByIndex(i).getName();
				String mNumber = restaurant.getCustomerByIndex(i).getMobileNumber();
				String customerType = restaurant.getCustomerByIndex(i).getCustomerType();
				int locationX = restaurant.getCustomerByIndex(i).getLocationX();
				int locationY = restaurant.getCustomerByIndex(i).getLocationY();
				String deliveryDistance = String.format("%.2f", restaurant.getCustomerByIndex(i).getDeliveryDistance());
					
				Object[] data = {name, mNumber, customerType, locationX, locationY, deliveryDistance};
				customerModel.addRow(data);
				
				
			} catch (CustomerException e1) {
				JOptionPane.showMessageDialog(pnlBackground, e1, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Display's all the order's in the ordersTable JTable.
	 * Display's a message dialog if a PizzaException occurs.
	 */
	private void displayOrders(){
		int amountOrders = restaurant.getNumPizzaOrders();
		
		for(int i = 0; i < amountOrders; i++){
			try{
				String type = restaurant.getPizzaByIndex(i).getPizzaType();
				int quantity = restaurant.getPizzaByIndex(i).getQuantity();
				String price = String.format("%.2f", restaurant.getPizzaByIndex(i).getOrderPrice());
				String cost = String.format("%.2f", restaurant.getPizzaByIndex(i).getOrderCost());
				String profit = String.format("%.2f",restaurant.getPizzaByIndex(i).getOrderProfit());
				
				Object[] data = {type, quantity, price, cost, profit};
				
				orderModel.addRow(data);
			}
			catch (PizzaException e){
				JOptionPane.showMessageDialog(pnlBackground, e, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Enable's or disable's specified buttons dependent on the parameter passed in to the method.
	 * 
	 * @param fileLoaded - the boolean that determines if the buttons are disabled or not.
	 */
	private void isFileLoaded(boolean fileLoaded){
		if(fileLoaded == true){
			btnReset.setEnabled(true);
			btnLoadCustomers.setEnabled(true);
			btnLoadOrders.setEnabled(true);
		}
		else{
			btnReset.setEnabled(false);
			btnLoadCustomers.setEnabled(false);
			btnLoadOrders.setEnabled(false);
			btnCalculateDistance.setEnabled(false);
			btnCalculateProfit.setEnabled(false);
		}
	}
	
	/**
	 * Resets the all data displayed and all GUI's components to its initial state.
	 */
	private void resetDetails(){
		restaurant.resetDetails();
		orderModel.setRowCount(0);
		customerModel.setRowCount(0);
		calcOrder.setText("");
		calcDist.setText("");
		btnCalculateDistance.setEnabled(false);
		btnCalculateProfit.setEnabled(false);
		isFileLoaded(false);
	}
	
	/**
	 * Sets new text in the functionalityInfo JLabel which purpose is to report the user
	 * whenever a functionality is complete.
	 * 
	 * @param text - The text going to be display in the JLabel.
	 */
	private void setFunctionalityText(String text){
		functionalityInfo.setText(text);
	}

	/**
	 * Specifies the GUI's size, default close operation, layout and
	 * adds all component's to the GUI
	 */
	private void createGUI(){
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// Create Panels
		pnlBackground = createPanel(Color.pink); 
		pnlButton = createPanel(Color.lightGray);
		pnlCustomers = createPanel(Color.pink);
		pnlOrders = createPanel(Color.pink);
		pnlButtonSouth = createPanel(Color.pink);
		
		// Create Buttons
		btnChooseFile = createButton("Choose File");
		btnLoadCustomers = createButton("Load Customers");
		btnLoadOrders = createButton("Load Orders");
		btnReset = createButton("Reset tables");
		btnCalculateDistance = createButton("Calculate Delivery Distance");
		btnCalculateProfit = createButton("Calculate Total Profit");
		
		// TextFields
		calcDist = new JTextField("", 20);
		calcDist.setEditable(false);
		btnCalculateDistance.setEnabled(false);
		calcOrder = new JTextField("", 20);
		calcOrder.setEditable(false);	
		btnCalculateProfit.setEnabled(false);
		
		// JLabel
		functionalityInfo = new JLabel();
		pnlBackground.add(functionalityInfo);
		
		createTables();
		
		pnlButtonSouth.add(calcDist);
		
		this.getContentPane().add(pnlBackground, BorderLayout.CENTER); // Add to frame
		this.getContentPane().add(pnlButton, BorderLayout.NORTH);
		this.getContentPane().add(pnlCustomers, BorderLayout.WEST);
		this.getContentPane().add(pnlOrders, BorderLayout.EAST);
		this.getContentPane().add(pnlButtonSouth, BorderLayout.SOUTH);
		this.setVisible(true); // Set GUI to visible
		
		layoutPanelGridBag();
		
		isFileLoaded(false); // Disable specific buttons if no file is loaded
	}
	
	/**
	 * Creates the tables and add them to panels.
	 */
	private void createTables(){
		customersTable = new JTable(new DefaultTableModel(new Object[]{"Name", "Mobile Number","Type", "X", "Y", "Delivery Distance"}, 0));
		customersTable.getColumnModel().getColumn(1).setPreferredWidth(80); // Mobile number column
		customersTable.getColumnModel().getColumn(3).setPreferredWidth(5); // X location column
		customersTable.getColumnModel().getColumn(4).setPreferredWidth(5); // Y location column
		customersTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Delivery distance column
		
		ordersTable = new JTable(new DefaultTableModel(new Object[]{"Pizza Type", "Quantity", "Order Price", "Order Cost", "Order Profit"}, 0));
		
		
		// Customer table header
		pnlCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.pink, Color.pink),
																"Customers",
																TitledBorder.CENTER,
																TitledBorder.TOP));
		// Orders table header
		pnlOrders.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.pink, Color.pink),
																"Orders",
																TitledBorder.CENTER,
																TitledBorder.TOP));
		
		customerModel = (DefaultTableModel) customersTable.getModel();
		orderModel = (DefaultTableModel) ordersTable.getModel();
		pnlCustomers.add(new JScrollPane(customersTable));
		pnlOrders.add(new JScrollPane(ordersTable));
	}
	
	/**
	 * Creates a new panel with background color specified in the parameter
	 * 
	 * @param c - The background of the panel
	 * @return the JPanel component
	 */
	private JPanel createPanel(Color c){
		JPanel jp = new JPanel();
		jp.setBackground(c); // Set the color
		
		return jp;
	}
	
	/**
	 * Creates a new button with text specified in the parameter
	 * 
	 * @param text
	 * @return the JButton component
	 */
	private JButton createButton(String text){
		JButton jb = new JButton();
		jb.setText(text);
		jb.addActionListener(this); // Adds actionlistener to button, so they can track when they are clicked
		
		return jb;
	}
	
	/**
	 * Adds GridBagLayot to two panels, and adds different components to the panels,
	 * with specified contraint's and position on the panel.
	 */
	private void layoutPanelGridBag(){
		GridBagLayout layout = new GridBagLayout();
		pnlButton.setLayout(layout);
		pnlBackground.setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		
		addToPanel(pnlButton, btnChooseFile,c,0,0,2,1);
		addToPanel(pnlButton, btnLoadCustomers,c,0,2,2,1);
		addToPanel(pnlButton, btnLoadOrders,c,2,2,2,1);
		addToPanel(pnlButton, btnReset, c, 2, 0, 2, 1);
		addToPanel(pnlButtonSouth, btnCalculateDistance, c, 0, 0, 2, 1);
		addToPanel(pnlButtonSouth, btnCalculateProfit, c, 0, 0, 2, 1);
		pnlButtonSouth.add(calcOrder);
	}
	
	/**
	*
	* A convenience method to add a component to given grid bag
	* layout locations. Code due to Cay Horstmann
	*
	* @param c - the component to add
	* @param constraints - the grid bag constraints to use
	* @param x - the x grid position
	* @param y - the y grid position
	* @param w - the grid width of the component
	* @param h - the grid height of the component
	*/
	private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints,int x, int y, int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		jp.add(c, constraints);
	}



	

	

}
