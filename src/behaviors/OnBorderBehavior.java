package behaviors;


import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

public class OnBorderBehavior implements Behavior {
	
	/**
	 * The master robot
	 */
	private final MasterRobot masterRobot;
	/**
	 * Indicates if the behavior got suppressed
	 */
	private boolean suppressed = false;
	/**
	 * The lower border threshold that indicates that we saw a white color.
	 */
	public static float whiteBorderValue = 0.545f;
	
	public OnBorderBehavior(MasterRobot robot){
		this.masterRobot = robot;
	}

	@Override
	public boolean takeControl() {
		System.out.println("L:" + masterRobot.getLeftLightSensorSample());
		System.out.println("R:" + masterRobot.getRightLightSensorSample());
		return false;
	}

	@Override
	public void action() {
		// Set suppressed to false
		suppressed = false;
		// drive backwards
		// Rotate CCW (Counter Clock Wise)
		masterRobot.getRightMotor().rotate(180, true);
		masterRobot.getLeftMotor().rotate(-180, true);
		while (masterRobot.getLeftMotor().isMoving() && !suppressed){
			// Wait till turn is complete or suppressed is called
			Thread.yield();
		}
		// Clean up
		masterRobot.stopRightMotor();
		masterRobot.stopLeftMotor();
	}

	@Override
	public void suppress() {
		suppressed = true; 
	}

}
