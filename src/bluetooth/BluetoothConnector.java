package bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

// @see http://stackoverflow.com/questions/8997598/importing-json-into-an-eclipse-project

import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
/**
 * In this class we handle the bluetooth connection
 * 
 */
public class BluetoothConnector {
	
	/**
	 * Hashmap containing the paired robots
	 */
	private final HashMap<String, String> pairedRobots = new HashMap<String, String>();
	/**
	 * The print writer, for writing messages using bluetooth
	 */
	private PrintWriter writer = null;
	/**
	 * The timeout value for bluetooth
	 */
	private final int TIMEOUT = 60000;
	/**
	 * The bluetooth connection
	 */
	private final NXTConnection connection;
	/**
	 * The bluetooth message that was received
	 */
	private String message;
	/**
	 * Indicates whether there is a message received that can be read
	 */
	private boolean messageReady;
	
	/**
	 * Master constructor
	 * connect to a specific robot
	 *
	 * @param masterRobotName The name of the master robot.
	 */
	public BluetoothConnector(String masterRobotName){
		initializePairedRobots();
		// Create the bluetooth connector
		BTConnector connector = new BTConnector();
		// Get the corresponding slave robot
		String correspondingSlave = pairedRobots.get(masterRobotName);
		System.out.println("Trying to connect to " + correspondingSlave) ;
		connection = connector.connect(correspondingSlave, NXTConnection.RAW);
		if(connection == null){
			System.out.println("Could not connect to " + correspondingSlave);
		}else{
			System.out.println("Connection success");
			writer = new PrintWriter(connection.openDataOutputStream());
		}
	}
	
	public BluetoothConnector getBluetoothConnector(){
		return this;
	}
	
	/**
	 * Slave constructor 
	 * Wait for connection from master
	 */
	public BluetoothConnector(){
		BTConnector connector = new BTConnector();
		connection = connector.waitForConnection(TIMEOUT, NXTConnection.RAW);
		writer = new PrintWriter(connection.openOutputStream());
	}
	
	/**
	 * Send a batch of sensor values through bluetooth.
	 * We could make use of JSON structure, but this is not nessacacy. 
	 * Also see http://stackoverflow.com/questions/13340138/how-to-generate-json-string-in-java-using-net-sf-json
	 * if we somehow decided to do so.
	 * @param touchLeft		The value of the touch left sensor
	 * @param touchRight	The value of the touch right sensor
	 * @param frontUltra	The value of the front ultrasonic sensor
	 * @param gyro			The value of the gyro
	 */
	public void writeMessage(int touchLeft, int touchRight, float frontUltra, float gyro){
		// Sending a batch of sensor values to the master. This is done using JSON
		StringBuilder batch = new StringBuilder();
		// Add all of the data to the current batch
		batch.append(touchLeft).append(" ")
		.append(touchRight).append(" ")
		.append(frontUltra).append(" ")
		.append(gyro);
		writer.write(batch.toString());
		// Flush it.
		writer.flush();
	}
	
	
	/**
	 * Thread checks whether a message is received from the other robot.
	 * If that is the case, we set a flag in the ReadMessageBehavior
	 */
	public void checkForMessage(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.openInputStream()));
		new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					// The following call is blocking
					message = reader.readLine();
					messageReady = (message.length() > 0);
					if (messageReady){
						notifyAll();
					}
				}catch (IOException ex){
					System.out.println("EXCP\n" + ex.getMessage());
					messageReady = false;
				}
			}
		}).start();
	}
	
	/**
	 * @return received message
	 */
	public String getMessage(){
		messageReady = false;
		return message;
	}
	
	/**
	 * 
	 * @return <code>True</code> if there is a message available, <code>False</code> otherwise
	 */
	public boolean messageAvailable(){
		return messageReady;
	}
	
	/**
	 * Initialize the pairs of robots that have been coupled
	 */
	private void initializePairedRobots(){
		pairedRobots.put("Rover5", "Rover6");
		pairedRobots.put("Rover6", "Rover5");
		pairedRobots.put("Rover7", "Rover8");
		pairedRobots.put("Rover8", "Rover7");
	}
	
	
}
