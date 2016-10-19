package bluetooth;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import behaviors.ReadBluetoothMessageBehavior;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

/**
 * In this class we handle the bluetooth connection
 * 
 */
public class BluetoothConnector {
	
	private final String[] robots = {"Rover1", "Rover2", "Rover3", "Rover4"};
	private PrintWriter writer = null;
	private final int TIMEOUT = 60000;
	private final NXTConnection connection;
	private byte[] buffer = new byte[32];
	
	/*
	 * Master constructor
	 * connect to a specific robot
	 */
	public BluetoothConnector(int robotNr){
		BTConnector connector = new BTConnector();

		System.out.println("Trying to connect to " + robots[robotNr-1]);
		connection = connector.connect(robots[robotNr-1], NXTConnection.RAW);
		
		if(connection == null){
			System.out.println("Could not connect to " + robots[robotNr-1]);
		}else{
			System.out.println("Connection success");
			writer = new PrintWriter(connection.openDataOutputStream());
		}
	}
	
	public BluetoothConnector getBluetoothConnector(){
		return this;
	}
	
	/*
	 * Slave constructor 
	 * Wait for connection from master
	 */
	public BluetoothConnector(){
		BTConnector connector = new BTConnector();
		connection = connector.waitForConnection(TIMEOUT, NXTConnection.RAW);
		writer = new PrintWriter(connection.openOutputStream());
	}
	
	/*
	 *	Send message on the connection to the other robot 
	 */
	public void writeMessage(String message){
		writer.println(message);
		writer.flush();
	}
	
	/**
	 * Thread checks whether a message is received from the other robot.
	 * If that is the case, we set a flag in the ReadMessageBehavior
	 */
	public void checkForMessage(ReadBluetoothMessageBehavior beh){
		DataInputStream reader = connection.openDataInputStream();
		new Thread(new Runnable(){
			@Override
			public void run() {
				byte b;
				int i = 0;
				
				try{
					while((b = reader.readByte()) != '\n'){
						buffer[i++] = b;
						System.out.println("b: " + b);
					}
					boolean status = buffer.length > 0;
					System.out.println("Klaar " + status);
					beh.setMessageReady(buffer.length > 0);
				}catch (IOException ex){
					System.out.println("EXCP\n" + ex.getMessage());
					beh.setMessageReady(false);
				}
			}
		}).start();
	}
	
	/**
	 * @return received message
	 */
	public String getMessage(){
		String message = new String(buffer);
		buffer = new byte[32];
		return message;
	}
	
}
