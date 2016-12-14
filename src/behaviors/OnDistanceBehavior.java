package behaviors;

import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnDistanceBehavior implements Behavior{

	private MasterRobot robot;
	private boolean suppressed = false;
	
	public OnDistanceBehavior(MasterRobot r) {
		this.robot = r;
	}
	
	@Override
	public boolean takeControl() {
		return robot.getFrontUltraSensorSample() <= 0.33;
	}

	@Override
	public void action() {
		// Set suppressed to false
		suppressed = false;
		// Turn right
		robot.getLeftMotor().rotate(180, true);
		robot.getRightMotor().rotate(-180, true);
		
		while(robot.getRightMotor().isMoving() && !suppressed){
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
