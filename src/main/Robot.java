package main;

import behaviors.DriveForwardBehavior;
import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import bluetooth.SlaveSensorData;
import bluetooth.BluetoothSensorDataStreamer;
import lejos.hardware.BrickFinder;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * We tested the color detection and sending of the colorId and receival via Bluetooth.
 * However we got an NumberFormatException during the storing of the received value to an List
 * Because we got "3 instead of just 3. Maybe we can test this wednesday.
 * @author Abdullah Rasool, Tom Sandmann
 *
 */

public class Robot {

	private boolean isMasterRobot;
	private String masterRobotName;
	
	/**
	 * The master robot
	 */
	private MasterRobot masterRobot = null;
	/**
	 * The slave robot
	 */
	private SlaveRobot slaveRobot = null;
	
	/**
	 * Constructor
	 */
	public Robot(){
		determineRobot();
	}
	
	public void init(){
		if(isMasterRobot)
			startMasterRobot();
		else
			startSlaveRobot();
		
		Run();
	}
	
	private void startMasterRobot(){
		masterRobot = new MasterRobot();
	}
	
	private void startSlaveRobot(){
		slaveRobot = new SlaveRobot();
	}
	
	private void determineRobot(){
		// Set the name of the master robot
		isMasterRobot = false;
		// Check whether this is a master robot
		if(BrickFinder.getLocal().getName().equals("Rover5")){
			isMasterRobot = true;
		}
		else if(BrickFinder.getLocal().getName().equals("Rover7")){
			isMasterRobot = true;
		}
		//The slave needs to know his name, so he can connect to the master
		//The slave starts the connection, because he immediately starts to stream sensor data to the master
		masterRobotName = BrickFinder.getLocal().getName();
	}
	
	/**
	 * Run method of the robot
	 */
	public void Run(){
		BluetoothConnectorContainer container = new BluetoothConnectorContainer(isMasterRobot, masterRobotName);
		if (isMasterRobot){
			// This is the master robot
			System.out.print("I'm the master");
			container.getInstance().checkForMessage();
			// Start the thread for receiving the sensor values
			// Define the list of behaviors
			Behavior[] behaviors = { new DriveForwardBehavior(masterRobot, container.getInstance()), 
					//new CheckDistanceBehavior(this), 
					//new OnTouchTurnBehavior(this), 
					//new DetectColorBehavior(this, driver, connector, c), 
					//new AvoidBlackBorder(this)
					};
			// Create and start the arbitrator
			Arbitrator arbitrator = new Arbitrator(behaviors);
			arbitrator.go();
		}
		else{
			// This is the slave robot
			System.out.print("I'm the slave");
			// Only define one behavior that will stream the sensor values
			Behavior[] behaviors = {new BluetoothSensorDataStreamer(slaveRobot, container.getInstance())};
			Arbitrator arbitrator = new Arbitrator(behaviors);
			arbitrator.go();
		}
	}
	
}
