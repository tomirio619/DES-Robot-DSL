package behaviors;

import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnTouchBehavior implements Behavior{

	/**
	 * The master robot
	 */
	private MasterRobot masterRobot;
	/**
	 * Indicates if the behavior got suppressed
	 */
	private boolean suppressed = false;
	/**
	 * Whether a left collision happened
	 */
	private boolean collisionLeft = false;
	/**
	 * Whether a right collision happened 
	 */
	private boolean collisionRight = false;
	
	public OnTouchBehavior (MasterRobot r) {
		this.masterRobot = r;
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		// Set suppressed to false
		suppressed = false;
		
		// Move backwards
		masterRobot.getRightMotor().rotate(-180, true);
		masterRobot.getLeftMotor().rotate(-180, true);
		
		if (collisionLeft){
			// Turn right
			// Specify behavior on collision left here
			masterRobot.getRightMotor().rotate(-180, true);
			masterRobot.getLeftMotor().rotate(180, true);
		}else{
			// Specify behavior on collision right here
			masterRobot.getRightMotor().rotate(180, true);
			masterRobot.getLeftMotor().rotate(-180, true);
		}
		// Note that we always end up spinning the left motor in the end
		while(masterRobot.getLeftMotor().isMoving() && !suppressed ){
			Thread.yield();	
		}
		// Clean up
		collisionLeft = false;
		collisionRight = false;
		masterRobot.getLeftMotor().stop();
		masterRobot.getRightMotor().stop();
	}

	@Override
	public void suppress() {
		suppressed = true; 
	}

}
