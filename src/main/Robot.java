package main;

import behaviors.DriveForwardBehavior;
import behaviors.ReadBluetoothMessageBehavior;
import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
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

	

	private MasterRobot masterRobot;
	private SlaveRobot slaveRobot;
	
	public Robot(){
		
		masterRobot = new MasterRobot();
		slaveRobot = new SlaveRobot();

		Run();
	}
	
	public void Run(){
		boolean driver = BrickFinder.getLocal().getName().equals("Rover5");
		BluetoothConnector connector = new BluetoothConnectorContainer(driver).getInstance();
		
		Behavior[] behaviors = { new DriveForwardBehavior(masterRobot, connector), 
				//new CheckDistanceBehavior(this), 
				//new OnTouchTurnBehavior(this), 
				//new DetectColorBehavior(this, driver, connector, c), 
				new ReadBluetoothMessageBehavior(masterRobot, connector), 
				//new AvoidBlackBorder(this)
				};
		Arbitrator arbitrator = new Arbitrator(behaviors);
		arbitrator.go();
	}
	
	
	
	
	
	
	
	
	
}
