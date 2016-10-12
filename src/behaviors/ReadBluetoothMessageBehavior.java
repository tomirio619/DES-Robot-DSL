package behaviors;

import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import lejos.robotics.subsumption.Behavior;
import main.Robot;

public class ReadBluetoothMessageBehavior implements Behavior{

	private BluetoothConnector connector;
	private Robot robot;
	
	public ReadBluetoothMessageBehavior(Robot r, boolean master) {
		this.robot = r;
		connector = new BluetoothConnectorContainer(master).getInstance();
	}
	
	@Override
	public boolean takeControl() {
		return connector.isThereAMessage();
	}

	@Override
	public void action() {
		String received = connector.getMessage();
		System.out.println(received);
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

}
