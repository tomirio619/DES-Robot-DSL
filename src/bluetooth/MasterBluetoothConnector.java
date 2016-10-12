package bluetooth;

import lejos.hardware.Bluetooth;

public class MasterBluetoothConnector extends BluetoothConnector{

	private BluetoothConnector connector;
	
	public MasterBluetoothConnector(int robotNr){
		connector = new BluetoothConnector(robotNr);
	}
	
	public BluetoothConnector getConnector(){
		return connector;
	}
	
}
