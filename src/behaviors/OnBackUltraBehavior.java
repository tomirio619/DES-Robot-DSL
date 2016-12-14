package behaviors;

import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnBackUltraBehavior implements Behavior{
	
	/**
	 * If the ultra sonic back sensor has a value greater than this threshold,
	 * we have to drive forward. Otherwise, we will fall of the egde.
	 */
	float backUltraSafeThreshold = 0.05f;
	/**
	 * The master robot
	 */
	private MasterRobot masterRobot;
	/**
	 * Indicates if the behavior got suppressed
	 */
	private boolean suppressed = false;
	
	public OnBackUltraBehavior(MasterRobot r) {
		this.masterRobot = r;
	}

	@Override
	public boolean takeControl() {
		float backUltraSample = masterRobot.getBackUltraSensorSample();
		return backUltraSample > backUltraSafeThreshold;
	}

	@Override
	public void action() {
		// set suppressed to false
		suppressed = false;
		// Drive forward
		masterRobot.getRightMotor().rotate(180, true);
		masterRobot.getLeftMotor().rotate(180, true);
		
		while (masterRobot.getLeftMotor().isMoving() && !suppressed){
			// Wait till turn is complete or suppressed is called
			Thread.yield();
		}
		// Clean up
		masterRobot.stopLeftMotor();
		masterRobot.stopRightMotor();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
