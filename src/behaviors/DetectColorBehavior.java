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
	private static List<Integer> colors;
	private BluetoothConnector connector;
	private int colorId;
	private boolean suppressed = false;
	
	public DetectColorBehavior(Robot r, boolean master, BluetoothConnector connector) {
		this.robot = r;
		this.colors = new ArrayList<>();
		
		this.connector = connector;
	}
	
	@Override
	public boolean takeControl() {
		this.colorId = robot.getColorId();
		return (colorId == Color.BLUE || colorId == Color.YELLOW || colorId == Color.RED) && !colors.contains(colorId);
	}

	@Override
	public void action() {
		//System.out.println("Found color " + colorId);
		connector.writeMessage(String.valueOf(colorId));
		System.out.println("Message send");
		if (! colors.contains(colorId) ){
			colors.add(colorId);
			getColors();
		}else if (colors.size() == 3){
			connector.writeMessage("-2");
		}
	}

	@Override
	public void suppress() {
		suppressed = true;	
	}
	
	public static void addColor(int i){
		if(! colors.contains(i) && colors.size() < 3){
			colors.add(i);
		}else{
			System.out.println("Not gonna add");
		}
	}
	
	public static void getColors(){
		for(int i = 0 ; i < colors.size(); i++){
			System.out.println(Integer.toString(colors.get(i)));
		}
	}

}
