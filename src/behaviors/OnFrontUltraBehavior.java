package behaviors;

import java.util.Random;

import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnFrontUltraBehavior implements Behavior{
	
	/**
	 * If the ultra sonic back sensor has a value greater than this threshold,
	 * we have to drive forward. Otherwise, we will fall of the egde.
	 */
	float frontUltraSafeThreshold = 0.3f;
	/**
	 * The master robot
	 */
	private MasterRobot masterRobot;
	/**
	 * Indicates if the behavior got suppressed
	 */
	private boolean suppressed = false;
	
	Random random;
	
	public OnFrontUltraBehavior(MasterRobot r) {
		this.masterRobot = r;
		random = new Random();
	}

	@Override
	public boolean takeControl() {
		float frontUltraSample = masterRobot.getFrontUltraSensorSample();
		System.out.print(frontUltraSample);
		return false;
		//return frontUltraSample <= frontUltraSafeThreshold;
	}

	@Override
	public void action() {
		// set suppressed to false
		suppressed = false;
		// Get a random value
		int rnd = random.nextInt(2);
		// Rotate based on the random number
		switch(rnd){
		case 1:
			masterRobot.getRightMotor().rotate(-180, true);
			masterRobot.getLeftMotor().rotate(180, true);
			break;
		default:
			// 0
			masterRobot.getRightMotor().rotate(180, true);
			masterRobot.getLeftMotor().rotate(-180, true);
		}
				
		while (masterRobot.getLeftMotor().isMoving() && !suppressed){
			// Wait for motor to complete rotation
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
