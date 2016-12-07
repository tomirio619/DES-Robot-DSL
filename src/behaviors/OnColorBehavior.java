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
	 * Colors found so far
	 */
	LinkedList<Integer> foundColors = new LinkedList<Integer>();

	public OnColorBehavior(MasterRobot r) {
		this.masterRobot = r;

	}

	@Override
	public boolean takeControl() {
		// Some conditions on which you want to take control
		mostRecentMeasurement = masterRobot.getFloorColor();
		System.out.println(mostRecentMeasurement[0] + "," + mostRecentMeasurement[1] + "," + mostRecentMeasurement[2] );
		int foundColor = RGBColorWrapper.determineColor(mostRecentMeasurement);
		if (foundColor == Color.GREEN || foundColor == Color.RED || foundColor == Color.GREEN){
			//System.out.println("We found the color with color ID" + foundColor) ;
			return true;
		}
		System.out.println("The found color id was:" + foundColor);
		return false;
	}

	/**
	 * We send the colorId of the detected color to the slave and add it to our
	 * own list. If we have found all colors we send that we are done and stop
	 * driving.
	 */
	@Override
	public void action() {
		while (!suppressed) {
			mostRecentMeasurement = masterRobot.getFloorColor();
			//System.out.println(mostRecentMeasurement[0] + "," + mostRecentMeasurement[1] + "," + mostRecentMeasurement[2]);
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
