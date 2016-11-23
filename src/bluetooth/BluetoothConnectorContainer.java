package bluetooth;

/**
 * Container for the bluetooth connector
 * In order to prevent NullPointer
 */

public class BluetoothConnectorContainer {

	private BluetoothConnector controller;
	private boolean isMaster;
	
	public BluetoothConnectorContainer(boolean master){
		this.isMaster = master;
		if(isMaster){
			//FIXME: Pass the Rover with which you would like to connect to, currently hardcoded. 
			controller = new BluetoothConnector("Rover5");
		}else{
			controller = new BluetoothConnector();
		}
	}
	
	public BluetoothConnector getInstance(){
		return controller;
	}
	
}
