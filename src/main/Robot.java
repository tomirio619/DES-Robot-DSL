package main;

import behaviors.DriveForwardBehavior;
import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import bluetooth.BluetoothSensorDataReceiver;
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

	/**
	 * The master robot
	 */
	private final MasterRobot masterRobot;
	/**
	 * The slave robot
	 */
	private final SlaveRobot slaveRobot;
	
	/**
	 * Constructor
	 */
	public Robot(){
		masterRobot = new MasterRobot();
		slaveRobot = new SlaveRobot();
		Run();
	}
	
	/**
	 * Run method of the robot
	 */
	public void Run(){
		// Set the name of the master robot
		String masterRobotName;
		boolean isMasterRobot = false;
		// Check whether this is a master robot
		if(BrickFinder.getLocal().getName().equals("Rover5")){
			masterRobotName = "Rover5";
			isMasterRobot = true;
		}
		else{
			masterRobotName = "Rover7";
			isMasterRobot = true;
		} 

		// Setup the Bluetooth connection
		BluetoothConnector connector = new BluetoothConnectorContainer(isMasterRobot, masterRobotName).getInstance();

		if (isMasterRobot){
			// This is the master robot
			System.out.print("I'm the master");
			// Start the thread for receiving the sensor values
			new Thread(new BluetoothSensorDataReceiver(connector)).start();
			// Define the list of behaviors
			Behavior[] behaviors = { new DriveForwardBehavior(new MasterRobot(), connector), 
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
			Behavior[] behaviors = {new BluetoothSensorDataStreamer(slaveRobot)};
			Arbitrator arbitrator = new Arbitrator(behaviors);
			arbitrator.go();
		}

	}
	
}
