package behaviors;

import java.util.ArrayList;
import java.util.List;

import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;
import main.Colors;
import main.Robot;

/*
 * In this behavior we we first connect to the slave or wait for the connection from the master (depending on whether we are the master or the slave)
 * We take control if the sensored color is either blue, yellow mor red and if we have not encountered this color before. 
 */
public class DetectColorBehavior implements Behavior{

	private Robot robot;
	private BluetoothConnector connector;
	private int colorId;
	private boolean suppressed = false;
	private Colors c;
	
	public DetectColorBehavior(Robot r, boolean master, BluetoothConnector connector, Colors c) {
		this.robot = r;
		this.c = c;
		this.connector = connector;
	}
	
	@Override
	public boolean takeControl() {
		this.colorId = robot.getColorId();
		return (colorId == Color.BLUE || colorId == Color.YELLOW || colorId == Color.RED) && !c.colorExists(colorId);
	}

	@Override
	/**
	 * We send the colorId of the detected color to the slave and add it to our own list. 
	 * If we have found all colors we send that we are done and stop driving. 
	 */
	public void action() {
		String message = "";
		switch(colorId){
		case Color.BLUE:
			message = "B";
			break;
		case Color.YELLOW:
			message = "Y";
			break;
		case Color.RED:
			message = "R";
			break;
		}
		
		c.addColor(message);
		
		connector.writeMessage(message);
		System.out.println("Message send");
		
		if(c.isDone()){
			connector.writeMessage("complete");
			System.out.println("Ik ben klaar");
		}
	}

	@Override
	public void suppress() {
		suppressed = true;	
	}


}
