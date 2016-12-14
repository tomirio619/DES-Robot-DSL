package behaviors;

import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnLightBehavior implements Behavior {

	/**
	 * The master robot
	 */
	private MasterRobot masterRobot;

	/**
	 * Indicates if the robot is suppressed
	 */
	private boolean suppressed = false;

	/**
	 * The most recent color measurement
	 */
	float whiteBorderValue = 0.545f;
	/**
	 * Indicates if the left light source triggered the onAction()
	 */
	boolean leftIsSource = false;
	/**
	 * Indicates if the right light source triggered the onAction()
	 */
	boolean rightIsSource = false;

	public OnLightBehavior(MasterRobot r) {
		this.masterRobot = r;

	}

	@Override
	public boolean takeControl() {
		float leftLightSensorSample = masterRobot.getLeftLightSensorSample();
		float rightLightSensorSample = masterRobot.getRightLightSensorSample();
		if (leftLightSensorSample >= whiteBorderValue){
			leftIsSource = true;
			return true;
		}
		else if (rightLightSensorSample >= whiteBorderValue){
			rightIsSource = true;
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * We send the colorId of the detected color to the slave and add it to our
	 * own list. If we have found all colors we send that we are done and stop
	 * driving.
	 */
	@Override
	public void action() {
		// Reset suppressed
		suppressed = false;
		// Drive backwards
		masterRobot.getRightMotor().rotate(-180, true);
		masterRobot.getLeftMotor().rotate(-180, true);
		// Wait for the motor to stop spinning
		while (masterRobot.getLeftMotor().isMoving() && !suppressed){
			// Wait till turn is complete or suppressed is called
			Thread.yield();
		}
		// Determine if to rotate left or right
		if (leftIsSource){
			// Turn right
			masterRobot.getRightMotor().rotate(-180, true);
			masterRobot.getLeftMotor().rotate(180, true);
		}else{
			// Turn left
			masterRobot.getRightMotor().rotate(180, true);
			masterRobot.getLeftMotor().rotate(-180, true);
		}
		while (masterRobot.getLeftMotor().isMoving() && !suppressed){
			// Wait till turn is complete or suppressed is called
			Thread.yield();
		}
		// Clean up
		masterRobot.getLeftMotor().stop();
		masterRobot.getRightMotor().stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}