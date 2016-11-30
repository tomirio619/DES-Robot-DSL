package bluetooth;

public class BluetoothSensorDataReceiver implements Runnable {
	
	// Static variables that can be read to access the latest sensor values available
	static int touchLeft = -1;
	static int touchRight = -1;
	static float frontUltra = -1;
	static int gyro = -1; 

	/**
	 * The bluetooth connector.
	 */
	BluetoothConnector connector;
	
	public BluetoothSensorDataReceiver(BluetoothConnector connector){
		this.connector = connector;
	}
	
	@Override
	public void run() {
		while(true){
			if (!connector.messageAvailable()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// There is a message avaiable
			String batch = connector.getMessage();
			// Split the string at whitespaces, see Streamer class for the correct order.
			String [] sensorValues = batch.split("\\s+");
			touchLeft = Integer.parseInt(sensorValues[0]);
			touchRight = Integer.parseInt(sensorValues[1]);
			frontUltra = Float.parseFloat(sensorValues[2]);
			gyro = Integer.parseInt(sensorValues[3]);
		}
		
	}

}
