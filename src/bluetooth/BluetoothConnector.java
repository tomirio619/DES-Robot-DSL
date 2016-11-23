package bluetooth;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import behaviors.ReadBluetoothMessageBehavior;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

/**
 * In this class we handle the bluetooth connection
 * 
 */
public class BluetoothConnector {
	
	private final HashMap<String, String> pairedRobots = new HashMap<String, String>();
	private PrintWriter writer = null;
	private final int TIMEOUT = 60000;
	private final NXTConnection connection;
	private String message;
	
	/*
	 * Master constructor
	 * connect to a specific robot
	 */
	public BluetoothConnector(String robotName){
		initializePairedRobots();
		BTConnector connector = new BTConnector();
		String robotPartner = pairedRobots.get(robotName);
		System.out.println("Trying to connect to " + robotPartner) ;
		connection = connector.connect(robotPartner, NXTConnection.RAW);
		if(connection == null){
			System.out.println("Could not connect to " + robotPartner);
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.openInputStream()));
		new Thread(new Runnable(){
			@Override
			public void run() {
				int i = 0;
				try{
					message = reader.readLine();
					beh.setMessageReady(message.length() > 0);
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
		return message;
	}
	
	private void initializePairedRobots(){
		pairedRobots.put("Rover5", "Rover6");
		pairedRobots.put("Rover6", "Rover5");
		pairedRobots.put("Rover3", "Rover4");
		pairedRobots.put("Rover4", "Rover3");
	}
	
	
}
