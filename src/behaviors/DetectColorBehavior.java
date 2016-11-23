package behaviors;

import bluetooth.BluetoothConnector;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;
import main.MasterRobot;

/*
 * In this behavior we we first connect to the slave or wait for the connection from the master (depending on whether we are the master or the slave)
 * We take control if the sensored color is either blue, yellow mor red and if we have not encountered this color before. 
 */
public class DetectColorBehavior implements Behavior{

	private MasterRobot robot;
	private BluetoothConnector connector;
	private float[] rgb;
	private boolean suppressed = false;
	
	public DetectColorBehavior(MasterRobot r, boolean master, BluetoothConnector connector) {
		this.robot = r;
		this.connector = connector;
	}
	
	@Override
	public boolean takeControl() {
		this.rgb = robot.getColorRGB();
		//fromRgbToColor(this.rgb);
		return true;
		//return (colorId == Color.BLUE || colorId == Color.YELLOW || colorId == Color.RED) && !c.colorExists(colorId);
	}

	@Override
	/**
	 * We send the colorId of the detected color to the slave and add it to our own list. 
	 * If we have found all colors we send that we are done and stop driving. 
	 */
	public void action() {
		String message = "";
	}

	@Override
	public void suppress() {
		suppressed = true;	
	}


}
