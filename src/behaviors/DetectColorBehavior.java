package behaviors;

import java.util.ArrayList;
import java.util.List;

import bluetooth.BluetoothConnector;
import bluetooth.BluetoothConnectorContainer;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;
import main.Robot;

public class DetectColorBehavior implements Behavior{

	private Robot robot;
	private List<Integer> colors;
	private BluetoothConnector connector;
	private int colorId;
	private boolean suppressed = false;
	
	public DetectColorBehavior(Robot r, boolean master) {
		this.robot = r;
		this.colors = new ArrayList<>();
		
		connector = new BluetoothConnectorContainer(master).getInstance();
	}
	
	@Override
	public boolean takeControl() {
		this.colorId = robot.getColorId();
		return colorId == Color.BLUE || colorId == Color.YELLOW || colorId == Color.RED;
	}

	@Override
	public void action() {
		System.out.println("Found color " + colorId);
		connector.writeMessage(String.valueOf(colorId));
		if (! colors.contains(colorId) ){
			colors.add(colorId);
		}else if (colors.size() == 3){
			connector.writeMessage("complete");
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
		
	}

}
