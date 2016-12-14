package behaviors;

import java.util.LinkedList;

import helpers.RGBColorWrapper;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

/*
 * In this behavior we we first connect to the slave or wait for the connection from the master (depending on whether we are the master or the slave)
 * We take control if the measured color is either blue, yellow more red and if we have not encountered this color before. 
 */
public class OnColorBehavior implements Behavior {

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
	float[] mostRecentMeasurement = new float[3];
	/**
	 * The most recent color ID based on the most recent measurement
	 */
	int mostRecentColorID = -1;

	/**
	 * Colors found so far
	 */
	LinkedList<Integer> foundColors = new LinkedList<Integer>();

	public OnColorBehavior(MasterRobot r) {
		this.masterRobot = r;

	}

	@Override
	public boolean takeControl() {
		mostRecentMeasurement = masterRobot.getColorSensorSample();
		mostRecentColorID = RGBColorWrapper.determineColor(mostRecentMeasurement);
		if (mostRecentColorID == Color.GREEN || mostRecentColorID == Color.RED || mostRecentColorID == Color.GREEN){
			return foundColors.contains(mostRecentColorID);
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
		// Add the color to our found colors
		foundColors.add(mostRecentColorID);
		masterRobot.putArmMotorDown();
		masterRobot.putArmMotorUp();
		// Clean up
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
