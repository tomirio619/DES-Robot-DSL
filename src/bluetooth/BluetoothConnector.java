package bluetooth;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import behaviors.ReadBluetoothMessageBehavior;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

public class BluetoothConnector {
	
	private final String[] robots = {"Rover1", "Rover2", "Rover3", "Rover4"};
	private PrintWriter writer = null;
	private final int TIMEOUT = 60000;
	private final NXTConnection connection;
	private byte[] buffer = new byte[32];
	private int j = -1;
	
	/*
	 * Master constructor
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
	 */
	public BluetoothConnector(){
		BTConnector connector = new BTConnector();
		connection = connector.waitForConnection(TIMEOUT, NXTConnection.RAW);
		writer = new PrintWriter(connection.openOutputStream());
	}
	
	public void writeMessage(String message){
		writer.println(message);
		writer.flush();
	}
	
	public void checkForMessage(){
		DataInputStream reader = connection.openDataInputStream();
		new Thread(new Runnable(){

			@Override
			public void run() {
				byte b;
				int i = 0;
				
				try{
					j = reader.readInt();
					System.out.println("Klaar " + j);
					ReadBluetoothMessageBehavior.setMessageReady(j != -1);
				}catch (IOException ex){
					System.out.println("EXCP\n" + ex.getMessage());
					ReadBluetoothMessageBehavior.setMessageReady(false);
				}
			}
		}).start();
	}
	
	public int getMessage(){
		int copy = j;
		j = -1;
		return copy;
	}
	
}
