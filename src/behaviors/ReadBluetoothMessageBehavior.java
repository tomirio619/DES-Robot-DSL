package behaviors;

import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import lejos.robotics.subsumption.Behavior;
import main.Colors;
import main.Robot;

/**
 * In this behavior we wait for a connection from the master or maybe create a connection to the slave depending on whether we are the master
 * or the slave. 
 * Next we start a thread which checks whether a message was received from the other robot. 
 * 
 *
 */
public class ReadBluetoothMessageBehavior implements Behavior{

	private BluetoothConnector connector;
	private Robot robot;
	private boolean messageReady = false; 
	private Colors c;
	
	public ReadBluetoothMessageBehavior(Robot r, boolean master, BluetoothConnector connector, Colors c) {
		this.robot = r;
		this.connector = connector;
		this.c = c;
		connector.checkForMessage(this);
	}
	
	@Override
	public boolean takeControl() {
		return messageReady;
	}

	@Override
	public void action() {
		String received = connector.getMessage().trim();
		System.out.println("Received: " + received + " " + received.length());
		c.addColor(received);
		c.printColors();
		
		messageReady = false;
		if(received.equals("complete")){
			System.out.println("Completed");
			robot.stopLeftMotor();
			robot.stopRightMotor();
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
	}
	
	public void setMessageReady(boolean b){
		messageReady = b;
	}

}
