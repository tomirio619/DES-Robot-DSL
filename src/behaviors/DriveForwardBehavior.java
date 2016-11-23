package behaviors;

import bluetooth.BluetoothConnector;
import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class DriveForwardBehavior implements Behavior{

	private MasterRobot robot;
	private boolean suppressed = false;
	
	//For testing
	private BluetoothConnector connector;
	
	public DriveForwardBehavior(MasterRobot r, BluetoothConnector connector){
		this.robot = r;
		this.connector = connector;
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		// Set suppressed to false
		suppressed = false;
		robot.getLeftMotor().setSpeed(120);
		robot.getRightMotor().setSpeed(120);
		// Make both motors go forward
		robot.getLeftMotor().forward();
		robot.getRightMotor().forward();
		
		connector.writeMessage("1337");
		
		while(!suppressed){
			// Wait till turn is complete or suppressed is called
			Thread.yield();
		}
		// Clean up
		robot.stopLeftMotor();
		robot.stopRightMotor();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
	

}
