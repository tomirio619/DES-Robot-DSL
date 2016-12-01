package bluetooth;

import lejos.robotics.subsumption.Behavior;
import main.Direction;
import main.SlaveRobot;


public class BluetoothSensorDataStreamer implements Behavior {
	
	/*
	 * The bluetooth connector
	 */
	BluetoothConnector connector;
	/*
	 * The slave robot
	 */
	private final SlaveRobot slaveRobot;
	/*
	 * Indicates whether the behavior got suppressed
	 */
	boolean suppressed;
	
	/**
	 * Constructor
	 * @param slaveRobot	The slave robot.
	 */
	public BluetoothSensorDataStreamer(SlaveRobot slaveRobot, BluetoothConnector connector){
		this.slaveRobot = slaveRobot;
		this.connector = connector;
		suppressed = false;
	}

	/**
	 * Take control method, always return true as this is the only behavior of the slave.
	 */
	@Override
	public boolean takeControl() {
		return true;
	}

	/**
	 * The action method, which specified what the slave will do
	 */
	@Override
	public void action() {
		while (!suppressed){
			/*
			 * Time to wait before retransmitting the data
			 */
			long refreshRate = 100;
			/**
			 * Retrieve all of the sensor values and submit them
			 */
			int touchLeft = slaveRobot.getTouchValue(Direction.LEFT);
			int touchRight = slaveRobot.getTouchValue(Direction.RIGHT);
			float frontUltra = slaveRobot.getFrontUltraValue();
			int gyro = slaveRobot.getGyroValue();
			connector.writeMessage(touchLeft, touchRight, frontUltra, gyro);
			/**
			 * Sleep some time before resending the values.
			 */
			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
}
