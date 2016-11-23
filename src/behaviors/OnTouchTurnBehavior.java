package behaviors;

import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnTouchTurnBehavior implements Behavior{

	private MasterRobot robot;
	private boolean suppressed = false;
	private boolean collisionLeft = false;
	private boolean collisionRight = false;
	
	public OnTouchTurnBehavior (MasterRobot r) {
		this.robot = r;
	}
	
	@Override
	public boolean takeControl() {
		return true;
		//Event 
	}

	@Override
	public void action() {
		// Set suppressed to false
		suppressed = false;
		
		// Move backwards
		robot.getRightMotor().rotate(-180, true);
		robot.getLeftMotor().rotate(-180, true);
		
		if (collisionLeft){
			// Turn right
			System.out.println("Left C");
			robot.getRightMotor().rotate(-180, true);
			robot.getLeftMotor().rotate(180, true);
		}else{
			// Turn left
			System.out.println("Right C");
			robot.getRightMotor().rotate(180, true);
			robot.getLeftMotor().rotate(-180, true);
		}
		// Note that we always end up spinning the left motor in the end
		while(robot.getLeftMotor().isMoving() && !suppressed ){
			Thread.yield();	
		}
		// Clean up
		collisionLeft = false;
		collisionRight = false;
		robot.getLeftMotor().stop();
		robot.getRightMotor().stop();
	}

	@Override
	public void suppress() {
		suppressed = true; 
	}

}
