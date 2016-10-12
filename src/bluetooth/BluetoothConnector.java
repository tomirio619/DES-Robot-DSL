package bluetooth;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

public class BluetoothConnector {
	
	private final String[] robots = {"Rover1", "Rover2", "Rover3", "Rover4"};
	private PrintWriter writer = null;
	private final int TIMEOUT = 60000;
	private final NXTConnection connection;
	private byte[] buffer = new byte[32];
	
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
		}
		
		writer = new PrintWriter(connection.openOutputStream());
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
	
	public boolean isThereAMessage(){
		DataInputStream reader = connection.openDataInputStream();
		byte b;
		int i = 0;
		try{
			while((b = reader.readByte()) != '\n'){
				buffer[i++] = b;
			}
			if(buffer.length > 0){
				return true;
			}
		}catch (IOException ex){
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public String getMessage(){
		String message = new String(buffer);
		buffer = new byte[32];
		return message;
	}
	
}
