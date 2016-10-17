package behaviors;

import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import lejos.robotics.subsumption.Behavior;
import main.Robot;

public class ReadBluetoothMessageBehavior implements Behavior{

	private BluetoothConnector connector;
	private Robot robot;
	private static boolean messageReady = false; 
	
	public ReadBluetoothMessageBehavior(Robot r, boolean master, BluetoothConnector connector) {
		this.robot = r;
		this.connector = connector;
		connector.checkForMessage();
	}
	
	@Override
	public boolean takeControl() {
		return messageReady;
	}

	@Override
	public void action() {
		String received = connector.getMessage();
		System.out.println("Received: " + received);
		DetectColorBehavior.addColor(Integer.valueOf(received));
		DetectColorBehavior.getColors();
		
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
	
	public static void setMessageReady(boolean b){
		messageReady = b;
	}

}
